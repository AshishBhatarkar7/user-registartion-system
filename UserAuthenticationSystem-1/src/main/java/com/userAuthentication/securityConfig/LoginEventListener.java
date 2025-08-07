package com.userAuthentication.securityConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LoginEventListener {

    private static final Logger logger = LoggerFactory.getLogger(LoginEventListener.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @EventListener
    public void handleSuccess(AuthenticationSuccessEvent event) {
        String timestamp = LocalDateTime.now().format(formatter);
        String username = event.getAuthentication().getName();
        logger.info("✅ SUCCESSFUL LOGIN: {} at {}", username, timestamp);
    }

    @EventListener
    public void handleFailure(AbstractAuthenticationFailureEvent event) {
        String timestamp = LocalDateTime.now().format(formatter);
        String username = (String) event.getAuthentication().getPrincipal();
        logger.warn("❌ FAILED LOGIN ATTEMPT: {} at {}", username, timestamp);
    }
}
