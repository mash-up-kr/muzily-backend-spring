package kr.mashup.ladder.domain

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@ComponentScan(basePackageClasses = [LadderDomainRoot::class])
@Configuration
class LadderDomainRoot
