// src/main/java/com/donation/config/LocaleConfig.java

package com.donation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class LocaleConfig {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH); // Set default locale
        return slr;
    }

    // Optionally, add a LocaleChangeInterceptor to allow changing the locale via
    // request parameter
    /*
     * @Bean
     * public LocaleChangeInterceptor localeChangeInterceptor() {
     * LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
     * lci.setParamName("lang");
     * return lci;
     * }
     * 
     * @Override
     * public void addInterceptors(InterceptorRegistry registry) {
     * registry.addInterceptor(localeChangeInterceptor());
     * }
     */
}
