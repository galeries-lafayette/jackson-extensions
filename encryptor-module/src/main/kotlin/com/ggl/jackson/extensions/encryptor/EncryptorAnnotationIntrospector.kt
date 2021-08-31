package com.ggl.jackson.extensions.encryptor

import com.fasterxml.jackson.databind.introspect.Annotated
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector
import org.springframework.security.crypto.encrypt.BytesEncryptor

class EncryptorAnnotationIntrospector(private val encryptor: BytesEncryptor) : NopAnnotationIntrospector() {

    override fun findSerializer(annotated: Annotated) = doWhenEncrypt(annotated, ::EncryptorSerializer)

    override fun findDeserializer(annotated: Annotated) = doWhenEncrypt(annotated, ::EncryptorDeserializer)

    private fun doWhenEncrypt(annotated: Annotated, action: (BytesEncryptor) -> Any) =
        action.takeIf { annotated.hasAnnotation(Encrypt::class.java) }?.invoke(encryptor)
}
