package kr.mashup.ladder.config.resolver

import kr.mashup.ladder.config.annotation.MemberId
import org.springframework.core.MethodParameter
import org.springframework.messaging.Message
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component

@Component
class WsMemberIdResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(MemberId::class.java) && parameter.parameterType == Long::class.java
    }

    override fun resolveArgument(parameter: MethodParameter, message: Message<*>): Long {
        val accessor = StompHeaderAccessor.wrap(message)
        val memberIdStr = accessor.sessionAttributes?.get(MEMBER_ID)?.toString() ?: throw IllegalStateException()
        return memberIdStr.toLong()
    }

    companion object {
        private const val MEMBER_ID = "MEMBER_ID"
    }
}
