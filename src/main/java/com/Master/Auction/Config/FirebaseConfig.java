package com.Master.Auction.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import com.google.firebase.FirebaseApp;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
    private static final Logger logger = Logger.getLogger(FirebaseConfig.class.getName());

    @PostConstruct
    public void init() {
        try {
            ClassPathResource resource = new ClassPathResource("service.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("FirebaseApp initialized successfully.");
            } else {
                logger.info("FirebaseApp already initialized.");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error initializing FirebaseApp", e);
        }
    }
}
