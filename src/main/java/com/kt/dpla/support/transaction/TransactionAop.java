package com.kt.dpla.support.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import lombok.RequiredArgsConstructor;

@Aspect
@RequiredArgsConstructor
@Configuration
public class TransactionAop {

    private final DataSourceTransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {
        TransactionInterceptor txAdvice = new TransactionInterceptor();

        List<RollbackRuleAttribute> rollbackRules = new ArrayList<>();
        rollbackRules.add(new RollbackRuleAttribute(Exception.class));

        DefaultTransactionAttribute readOnlyAttribute = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
        readOnlyAttribute.setReadOnly(true);
        readOnlyAttribute.setTimeout(60);

        RuleBasedTransactionAttribute writeAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
        writeAttribute.setTimeout(60);

        String readOnlyDescription = readOnlyAttribute.toString();
        String writeDescription = writeAttribute.toString();

        Properties txAttribute = new Properties();
        txAttribute.setProperty("*select", readOnlyDescription);
        txAttribute.setProperty("*get", readOnlyDescription);
        txAttribute.setProperty("*list", readOnlyDescription);
        txAttribute.setProperty("*", writeDescription);

        txAdvice.setTransactionAttributes(txAttribute);
        txAdvice.setTransactionManager(transactionManager);

        return txAdvice;
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.kt..service..*(..))");

        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}