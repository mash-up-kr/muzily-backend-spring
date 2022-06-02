package kr.mashup.ladder.domain.config

import kr.mashup.ladder.domain.LadderDomainRoot
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaAuditing
@Configuration
@EntityScan(basePackageClasses = [LadderDomainRoot::class])
@EnableJpaRepositories(basePackageClasses = [LadderDomainRoot::class])
class JpaConfig
