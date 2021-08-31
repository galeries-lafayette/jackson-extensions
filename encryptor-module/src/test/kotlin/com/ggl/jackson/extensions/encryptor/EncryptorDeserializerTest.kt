package com.ggl.jackson.extensions.encryptor

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.encrypt.AesBytesEncryptor

internal class EncryptorDeserializerTest {

    private companion object {
        private val objectMapper = ObjectMapper()
            .registerModules(
                EncryptorModule(
                    AesBytesEncryptor("p4550rD", "73616c74")
                ),
                KotlinModule()
            )

        private data class Pojo(@field:Encrypt val value: String)
    }

    @Test
    internal fun `It should deserialize json`() {
        // Given
        val json = "{\"value\":\"MDwEBNCpeGyYxcAa++zKQA==\"}"

        // When
        val data = objectMapper.readValue<Pojo>(json)

        // Then
        data `should be equal to` Pojo("data")
    }
}
