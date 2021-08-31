package com.ggl.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.ggl.jackson.extensions.encryptor.Encrypt
import com.ggl.jackson.extensions.encryptor.EncryptorModule
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.encrypt.AesBytesEncryptor

internal class EncryptorModuleTest {

    private companion object {

        data class Pojo(
            @field:Encrypt val int: Int,
            @field:Encrypt val float: Float,
            @field:Encrypt val string: String,
            @field:Encrypt val boolean: Boolean,
            val basic: String
        )

        private val objectMapper = ObjectMapper().registerModules(
            KotlinModule(),
            EncryptorModule(AesBytesEncryptor("p4550rD", "73616c74"))
        )

        private val pojo = Pojo(
            int = 123,
            float = 2.45f,
            string = "hello world!",
            boolean = true,
            basic = "basic"
        )

        private const val json = """
            {
              "int": "UjNxTj8jSlkA+/9MJcpLJA==",
              "float": "j5ehoymwH2Ua4bauCSwG4Q==",
              "string": "KsEt234EwCtRnPesoqxXAw==",
              "boolean": "/0DEHwskJc18mexwNQCtCw==",
              "basic": "basic"
            }
        """
    }

    @Test
    internal fun `It should serialize pojo to json`() {
        // When
        val result = objectMapper.writeValueAsString(pojo)

        // Then
        result `should be equal to` json.flatten()
    }

    @Test
    internal fun `It should deserialize json to pojo`() {
        // When
        val result = objectMapper.readValue<Pojo>(json)

        // Then
        result `should be equal to` pojo
    }

    private fun String.flatten() = trimIndent().replace(Regex("\\s+"), "")
}
