package com.ggl.jackson.extensions.encryptor

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.ObjectCodec
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.ContextualDeserializer
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import org.springframework.security.crypto.encrypt.BytesEncryptor

class EncryptorDeserializer(
    private val encryptor: BytesEncryptor,
    private val baseDeserializer: JsonDeserializer<*>? = null
) : StdDeserializer<Any>(Any::class.java), ContextualDeserializer {

    override fun deserialize(parser: JsonParser, context: DeserializationContext) =
        with(parser.codec) {
            val decrypt = readTree<JsonNode>(parser).binaryValue()
                .let(encryptor::decrypt)
                .let(::String)

            baseDeserializer?.deserialize(createParser(decrypt), context) ?: decrypt
        }

    override fun createContextual(context: DeserializationContext, property: BeanProperty) =
        context
            .findContextualValueDeserializer(property.type, property)
            .let { EncryptorDeserializer(encryptor, it) }

    override fun getNullValue(context: DeserializationContext) = baseDeserializer?.getNullValue(context)

    override fun getEmptyValue(context: DeserializationContext) = baseDeserializer?.getEmptyValue(context)

    private fun ObjectCodec.createParser(value: String) =
        factory
            .createParser(value)
            .apply { nextToken() }
}
