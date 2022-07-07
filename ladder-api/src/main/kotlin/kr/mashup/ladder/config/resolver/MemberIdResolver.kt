package kr.mashup.ladder.config.resolver

import kr.mashup.ladder.config.annotation.MemberId
import kr.mashup.ladder.domain.common.error.model.UnknownErrorException
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

const val MEMBER_ID = "MEMBER_ID"

@Component
class MemberIdResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(MemberId::class.java) && parameter.parameterType == Long::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? {
        return webRequest.getAttribute(MEMBER_ID, 0)
            ?: throw UnknownErrorException("예상치 못한 에러가 발생하였습니다. memberid를 받아오지 못했습니다")
    }

}
