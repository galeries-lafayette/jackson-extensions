package com.ggl.jackson.extensions.encryptor

import com.fasterxml.jackson.databind.module.SimpleModule
import org.springframework.security.crypto.encrypt.BytesEncryptor

class EncryptorModule(private val encryptor: BytesEncryptor) : SimpleModule() {

    override fun setupModule(context: SetupContext) =
        context.appendAnnotationIntrospector(EncryptorAnnotationIntrospector(encryptor))
}
