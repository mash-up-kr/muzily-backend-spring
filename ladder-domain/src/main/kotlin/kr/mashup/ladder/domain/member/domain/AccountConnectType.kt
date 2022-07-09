package kr.mashup.ladder.domain.member.domain

enum class AccountConnectType(
    private val description: String,
) {

    CONNECTED("계정에 연결된 멤버"),
    UNCONNECTED("계정에 연결되지 않은 멤버")

}
