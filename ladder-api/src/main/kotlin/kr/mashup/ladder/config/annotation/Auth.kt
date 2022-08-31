package kr.mashup.ladder.config.annotation

@Target(AnnotationTarget.FUNCTION)
annotation class Auth(
    val allowedAnonymous: Boolean = true,
    val optionalAuth: Boolean = false,
)
