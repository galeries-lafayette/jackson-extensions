package com.ggl.jackson.extensions.builder

import com.fasterxml.jackson.databind.module.SimpleModule
import com.github.pozo.KotlinBuilder

@KotlinBuilder
data class Module(
    private val builderMethodName: String = DEFAULT_BUILDER_METHOD_NAME,
    private val buildMethodName: String = DEFAULT_BUILD_METHOD_NAME,
    private val withPrefix: String = DEFAULT_PREFIX
) : SimpleModule() {

    companion object {
        private const val DEFAULT_BUILDER_METHOD_NAME = "builder"
        private const val DEFAULT_BUILD_METHOD_NAME = "build"
        private const val DEFAULT_PREFIX = ""
    }

    override fun setupModule(context: SetupContext) {
        super.setupModule(context)
        context.appendAnnotationIntrospector(
            BuilderAnnotationIntrospector(
                builderMethodName,
                buildMethodName,
                withPrefix
            )
        )
    }
}
