package kr.mashup.ladder

import kr.mashup.ladder.domain.common.constants.PackageConstants.LADDER_BASE_PACKAGE
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan(basePackages = [LADDER_BASE_PACKAGE])
@SpringBootApplication(scanBasePackages = [LADDER_BASE_PACKAGE])
class LadderServerApplication

fun main(args: Array<String>) {
    runApplication<LadderServerApplication>(*args)
}
