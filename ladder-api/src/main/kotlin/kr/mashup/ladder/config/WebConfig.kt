package kr.mashup.ladder.config

import kr.mashup.ladder.config.interceptor.AuthInterceptor
import kr.mashup.ladder.config.resolver.AccountIdResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
class WebConfig(
    private val authInterceptor: AuthInterceptor,
    private val accountIdResolver: AccountIdResolver,
) : WebMvcConfigurationSupport() {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authInterceptor)
    }

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.add(accountIdResolver)
    }

}
