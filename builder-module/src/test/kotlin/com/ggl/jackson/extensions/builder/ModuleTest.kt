package com.ggl.jackson.extensions.builder

import com.fasterxml.jackson.databind.ObjectMapper
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ModuleTest {

    private data class Pojo(val str: String? = null) {
        companion object {
            data class PojoBuilder(var str: String? = "dummy") {
                fun build(): Pojo = Pojo(str)
            }

            @JvmStatic fun builder(): PojoBuilder = PojoBuilder()
        }
    }

    private data class Pojo2(val str: String? = null) {
        companion object {
            data class PojoBuilder(var str: String? = "dummy") {
                fun create(): Pojo2 = Pojo2(str)
            }

            @JvmStatic fun newBuilder(): PojoBuilder = PojoBuilder()
        }
    }

    @Nested
    inner class PojoTest {

        @Test
        internal fun `It should deserialize Pojo with null value when builder module is missing`() {
            // Given
            val objectMapper = ObjectMapper()
            val json = "{}"

            // When
            val result = objectMapper.readValue(json, Pojo::class.java)

            // Then
            result `should be equal to` Pojo(null)
        }

        @Test
        internal fun `It should deserialize Pojo with dummy value when builder module`() {
            // Given
            val objectMapper = ObjectMapper().registerModule(Module())
            val json = "{}"

            // When
            val result = objectMapper.readValue(json, Pojo::class.java)

            // Then
            result `should be equal to` Pojo("dummy")
        }

        @Test
        internal fun `It should deserialize Pojo with value`() {
            // Given
            val objectMapper = ObjectMapper().registerModule(Module())
            val json = "{\"str\": \"value\"}"

            // When
            val result = objectMapper.readValue(json, Pojo::class.java)

            // Then
            result `should be equal to` Pojo("value")
        }
    }

    @Nested
    inner class Pojo2Test {

        @Test
        internal fun `It should deserialize Pojo2 with null value when builder module is missing`() {
            // Given
            val objectMapper = ObjectMapper()
            val json = "{}"

            // When
            val result = objectMapper.readValue(json, Pojo2::class.java)

            // Then
            result `should be equal to` Pojo2(null)
        }

        @Test
        internal fun `It should deserialize Pojo2 - Kotlin way`() {
            // Given
            val objectMapper = ObjectMapper().registerModule(
                Module(
                    builderMethodName = "newBuilder",
                    buildMethodName = "create"
                )
            )

            val json = "{}"

            // When
            val result = objectMapper.readValue(json, Pojo2::class.java)

            // Then
            result `should be equal to` Pojo2("dummy")
        }

        @Test
        internal fun `It should deserialize Pojo2 - Java way`() {
            // Given
            val objectMapper = ObjectMapper().registerModule(
                ModuleBuilder.builder()
                    .setBuilderMethodName("newBuilder")
                    .setBuildMethodName("create")
                    .setWithPrefix("")
                    .create()
            )

            val json = "{}"

            // When
            val result = objectMapper.readValue(json, Pojo2::class.java)

            // Then
            result `should be equal to` Pojo2("dummy")
        }
    }
}
