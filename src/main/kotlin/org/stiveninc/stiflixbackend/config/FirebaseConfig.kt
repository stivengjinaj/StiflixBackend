package org.stiveninc.stiflixbackend.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream

@Configuration
class FirebaseConfig (
    @Value("\${GOOGLE_APPLICATION_CREDENTIALS}")
    private val credentialsPath: String
) {

    @PostConstruct
    fun initialize() {
        if (FirebaseApp.getApps().isEmpty()) {
            val credentials = GoogleCredentials.fromStream(
                FileInputStream(credentialsPath)
            )

            val options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build()

            FirebaseApp.initializeApp(options)
        }
    }

    @Bean
    fun firestore(): Firestore =
        FirestoreClient.getFirestore()
}
