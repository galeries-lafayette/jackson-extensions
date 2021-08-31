package com.ggl.jackson.extensions.encryptor

import com.fasterxml.jackson.databind.ObjectMapper
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.encrypt.AesBytesEncryptor

internal class EncryptorSerializerTest {

    private companion object {
        private val objectMapper = ObjectMapper()
            .registerModule(
                EncryptorModule(
                    AesBytesEncryptor("p4550rD", "73616c74")
                )
            )

        private data class Pojo(@field:Encrypt val value: String)
    }

    @Test
    internal fun `It should serialize data`() {
        // Given
        val data = Pojo("data")

        // When
        val json = objectMapper.writeValueAsString(data)

        // Then
        json `should be equal to` "{\"value\":\"MDwEBNCpeGyYxcAa++zKQA==\"}"
    }
}
