package com.marifsulaksono.ewallet.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {
        // sementara hardcode, nanti diganti user login dari JWT
        return () -> Optional.of("system");
    }
}
