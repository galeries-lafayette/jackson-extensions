package com.ggl.jackson.extensions.encryptor

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.springframework.security.crypto.encrypt.BytesEncryptor
import java.io.StringWriter

class EncryptorSerializer(private val encryptor: BytesEncryptor) : StdSerializer<Any>(Any::class.java) {

    override fun serialize(value: Any, jGen: JsonGenerator, provider: SerializerProvider) =
        with(StringWriter()) {
            defaultSerializeValue(this, value, jGen, provider)
            encrypt(buffer.toString(), jGen)
        }

    private fun encrypt(value: String, jGen: JsonGenerator) =
        if (value == "null")
            jGen.writeNull()
        else
            jGen.writeBinary(encryptor.encrypt(value.toByteArray()))

    private fun defaultSerializeValue(
        writer: StringWriter,
        value: Any,
        jGen: JsonGenerator,
        provider: SerializerProvider
    ) {
        val nestedGenerator = jGen.codec.factory.createGenerator(writer)
        provider.defaultSerializeValue(value, nestedGenerator)
        nestedGenerator.close()
    }
}
