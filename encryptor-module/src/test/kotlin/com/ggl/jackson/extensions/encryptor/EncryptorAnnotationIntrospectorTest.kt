package com.ggl.jackson.extensions.encryptor

import com.fasterxml.jackson.databind.introspect.Annotated
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should be null`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.encrypt.BytesEncryptor

@ExtendWith(MockKExtension::class)
internal class EncryptorAnnotationIntrospectorTest {

    @MockK
    private lateinit var encryptor: BytesEncryptor

    @InjectMockKs
    private lateinit var annotationIntrospector: EncryptorAnnotationIntrospector

    @Test
    internal fun `It should get encryptor serializer when annotated with encrypt`() {
        // Given
        val annotated = mockk<Annotated>()

        every { annotated.hasAnnotation(Encrypt::class.java) } returns true

        // When
        val serializer = annotationIntrospector.findSerializer(annotated)

        // Then
        serializer `should be instance of` EncryptorSerializer::class

        verify { annotated.hasAnnotation(Encrypt::class.java) }
    }

    @Test
    internal fun `It should not get serializer when annotated without encrypt`() {
        // Given
        val annotated = mockk<Annotated>()

        every { annotated.hasAnnotation(Encrypt::class.java) } returns false

        // When
        val serializer = annotationIntrospector.findSerializer(annotated)

        // Then
        serializer.`should be null`()

        verify { annotated.hasAnnotation(Encrypt::class.java) }
    }

    @Test
    internal fun `It should get encryptor deserializer when annotated with encrypt`() {
        // Given
        val annotated = mockk<Annotated>()

        every { annotated.hasAnnotation(Encrypt::class.java) } returns true

        // When
        val deserializer = annotationIntrospector.findDeserializer(annotated)

        // Then
        deserializer `should be instance of` EncryptorDeserializer::class

        verify { annotated.hasAnnotation(Encrypt::class.java) }
    }

    @Test
    internal fun `It should not get deserializer when annotated without encrypt`() {
        // Given
        val annotated = mockk<Annotated>()

        every { annotated.hasAnnotation(Encrypt::class.java) } returns false

        // When
        val serializer = annotationIntrospector.findDeserializer(annotated)

        // Then
        serializer.`should be null`()

        verify { annotated.hasAnnotation(Encrypt::class.java) }
    }
}
