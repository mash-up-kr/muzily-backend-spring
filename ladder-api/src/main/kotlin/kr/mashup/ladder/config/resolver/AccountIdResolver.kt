package kr.mashup.ladder.config.resolver

import kr.mashup.ladder.config.annotation.AccountId
import kr.mashup.ladder.domain.common.error.model.UnknownErrorException
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

const val ACCOUNT_ID = "ACCOUNT_ID"

@Component
class AccountIdResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(AccountId::class.java) && parameter.parameterType == Long::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? {
        return webRequest.getAttribute(ACCOUNT_ID, 0)
            ?: throw UnknownErrorException("예상치 못한 에러가 발생하였습니다. accountId를 받아오지 못했습니다")
    }

}
