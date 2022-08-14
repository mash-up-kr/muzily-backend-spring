package kr.mashup.ladder.domain.playlist.infra.converter

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
@Component
class OrderConverter(
    private val objectMapper: ObjectMapper,
) : AttributeConverter<MutableList<Long>, String> {
    override fun convertToDatabaseColumn(attribute: MutableList<Long>): String {
        return objectMapper.writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String?): MutableList<Long> {
        if (dbData == null || dbData.isEmpty()) {
            return mutableListOf()
        }

        return objectMapper.readValue(dbData, object : TypeReference<MutableList<Long>>() {})
    }
}
