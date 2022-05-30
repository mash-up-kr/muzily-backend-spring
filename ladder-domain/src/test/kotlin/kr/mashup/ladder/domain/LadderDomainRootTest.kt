package kr.mashup.ladder.domain

import kr.mashup.ladder.domain.common.constants.PackageConstants.LADDER_BASE_PACKAGE
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@ConfigurationPropertiesScan(basePackages = [LADDER_BASE_PACKAGE])
@SpringBootApplication(scanBasePackages = [LADDER_BASE_PACKAGE])
internal class LadderDomainRootTest : LadderDomainRoot() {

    @Test
    fun contextsLoad() {

    }

}
