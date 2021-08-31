package com.ggl.jackson.extensions.builder

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder.Value
import com.fasterxml.jackson.databind.introspect.AnnotatedClass
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector

class BuilderAnnotationIntrospector(
    private val builderMethodName: String,
    private val buildMethodName: String,
    private val withPrefix: String
) : JacksonAnnotationIntrospector() {

    override fun findPOJOBuilderConfig(ac: AnnotatedClass) =
        if (ac.hasAnnotation(JsonPOJOBuilder::class.java)) super.findPOJOBuilderConfig(ac)
        else Value(buildMethodName, withPrefix)

    override fun findPOJOBuilder(ac: AnnotatedClass) =
        super.findPOJOBuilder(ac)
            ?: ac.factoryMethods
                .filter<AnnotatedMethod> { it.name == builderMethodName }
                .map { it.rawReturnType }
                .firstOrNull()
}
