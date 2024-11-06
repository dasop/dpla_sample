package com.kt.mddp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication(scanBasePackages = { "com.kt.mddp", "com.kt.dpla.support" }, exclude = {})
public class KTApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(KTApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(KTApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
        /* @formatter:off */
        log.info("\n========================================================="
               + "\n                                                         "
               + "\n    Application  MDD_-API                                "
               + "\n                                                         "
               + "\n=========================================================");
        /* @formatter:on */
    }

}
