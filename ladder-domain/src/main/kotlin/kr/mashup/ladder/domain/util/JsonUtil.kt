package kr.mashup.ladder.domain.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule

object JsonUtil {
    private val objectMapper = ObjectMapper().registerModule(kotlinModule())

    fun toJson(value: Any): String {
        return objectMapper.writeValueAsString(value)
    }

    fun <T> fromByteArray(src: ByteArray, valueType: Class<T>): T {
        return objectMapper.readValue(src, valueType)
    }

    fun <T> fromByteArray(src: ByteArray, valueTypeRef: TypeReference<T>): T {
        return objectMapper.readValue(src, valueTypeRef)
    }

    fun <T> fromJson(content: String, valueType: Class<T>): T {
        return objectMapper.readValue(content, valueType)
    }

    fun <T> fromJson(content: String, valueTypeRef: TypeReference<T>): T {
        return objectMapper.readValue(content, valueTypeRef)
    }

    // TODO : 개선
    fun <T> reDeserialize(value: Any, valueTypeRef: TypeReference<T>): T {
        val content = objectMapper.writeValueAsString(value)
        return objectMapper.readValue(content, valueTypeRef)
    }
}
