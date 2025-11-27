package com.debaterr.app.firebasepocmessaging.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import javax.annotation.PostConstruct


@Configuration
class FirebaseConfig {
    @PostConstruct
    fun initialize() {
        try {
            val serviceAccount = ClassPathResource("firebaseconfig.json").inputStream

            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options)
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to initialize Firebase Admin SDK", e)
        }
    }
}