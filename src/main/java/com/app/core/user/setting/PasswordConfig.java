package com.app.core.user.setting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordConfig {

    @Value("${spring.application.password.config.secret}")
    private String SECRET_KEY;
    @Value("${spring.application.password.config.vector}")
    private String INIT_VECTOR;

    @Bean
    public String getSecret() {
        return SECRET_KEY;
    }

    @Bean
    public String getVector() {
        return INIT_VECTOR;
    }
}
