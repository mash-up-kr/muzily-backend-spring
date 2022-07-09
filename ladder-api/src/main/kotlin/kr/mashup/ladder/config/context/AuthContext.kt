package kr.mashup.ladder.config.context

data class AuthContext(
    val memberId: Long,
) {

    companion object {
        fun of(memberId: Long): AuthContext {
            return AuthContext(
                memberId = memberId
            )
        }
    }

}
