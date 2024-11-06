package com.kt.dpla.support.masking.field;

import jakarta.annotation.PostConstruct;

import com.kt.dpla.support.masking.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kt.dpla.support.masking.service.MaskingService;

public enum MaskingType {

    DEFAULT(), NAME(), EMAIL(), ID(), PHONE_NO(), ACCOUNT_NUMBER(), ADDRESS(), CREDIT_CARD(), IP(), MEMBERSHIP_CARD(), PASSPORT(), PASSWORD(), RESIDENT_NUMBER();

    private MaskingService maskingService;

    public MaskingService getMaskingService() {
        return maskingService;
    }

    @Component
    public static class MaskingServiceInject {
        @Autowired
        protected DefaultMaskingService maskingService;
        @Autowired
        protected NameMaskingService nameMaskingService;
        @Autowired
        protected EmailMaskingService emailMaskingService;
        @Autowired
        protected IdMaskingService idMaskingService;
        @Autowired
        protected PhoneNoMaskingService phoneNoMaskingService;
        @Autowired
        protected AccountNumberMaskingService accountNumberMaskingService;
        @Autowired
        protected AddressMaskingService addressMaskingService;
        @Autowired
        protected CreditCardMaskingService creditCardMaskingService;
        @Autowired
        protected IpMaskingService ipMaskingService;
        @Autowired
        protected MembershipCardMaskingService membershipCardMaskingService;
        @Autowired
        protected PassportMaskingService passportMaskingService;
        @Autowired
        protected PasswordMaskingService passwordMaskingService;
        @Autowired
        protected ResidentNumberMaskingService residentNumberMaskingService;

        @PostConstruct
        public void postConstruct() {
            DEFAULT.maskingService = maskingService;
            NAME.maskingService = nameMaskingService;
            EMAIL.maskingService = emailMaskingService;
            ID.maskingService = idMaskingService;
            PHONE_NO.maskingService = phoneNoMaskingService;
            ACCOUNT_NUMBER.maskingService = accountNumberMaskingService;
            ADDRESS.maskingService = addressMaskingService;
            CREDIT_CARD.maskingService = creditCardMaskingService;
            IP.maskingService = ipMaskingService;
            MEMBERSHIP_CARD.maskingService = membershipCardMaskingService;
            PASSPORT.maskingService = passportMaskingService;
            PASSWORD.maskingService = passwordMaskingService;
            RESIDENT_NUMBER.maskingService = residentNumberMaskingService;
        }
    }
}
