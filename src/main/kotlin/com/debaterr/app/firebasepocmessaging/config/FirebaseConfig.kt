package com.debaterr.app.firebasepocmessaging.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource


@Configuration
class FirebaseConfig {
    private val log = LoggerFactory.getLogger(FirebaseConfig::class.java);

    @Bean
    fun getInstance(): FirebaseApp? {
        try {
            val serviceAccount = ClassPathResource("firebaseconfig.json").inputStream

            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()

            return FirebaseApp.initializeApp(options);
        } catch (e: Exception) {
            throw RuntimeException("Failed to initialize Firebase Admin SDK", e)
        }
    }

    @Bean
    fun firebaseMessaging(firebaseApp: FirebaseApp): FirebaseMessaging? {
        return FirebaseMessaging.getInstance(firebaseApp)
    }


}