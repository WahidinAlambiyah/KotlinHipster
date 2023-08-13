package com.mycompany.myapp

import com.mycompany.myapp.KotlinHipsterApp
import com.mycompany.myapp.config.AsyncSyncConfiguration
import com.mycompany.myapp.config.EmbeddedKafka
import com.mycompany.myapp.config.EmbeddedSQL
import com.mycompany.myapp.config.EmbeddedRedis
import org.junit.jupiter.api.extension.ExtendWith

import org.springframework.test.annotation.DirtiesContext
import org.springframework.boot.test.context.SpringBootTest

/**
 * Base composite annotation for integration tests.
 */
@kotlin.annotation.Target(AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(classes = [KotlinHipsterApp::class, AsyncSyncConfiguration::class])
@EmbeddedRedis
@EmbeddedKafka
@EmbeddedSQL
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
annotation class IntegrationTest {
}
