package kr.mashup.ladder.config.context

import java.security.Principal

object WsMemberPrincipalContext {
    private val principalByMemberId: MutableMap<Long, Principal> = mutableMapOf()

    fun get(memberId: Long): Principal? {
        return principalByMemberId[memberId]
    }

    fun put(memberId: Long, principal: Principal?) {
        if (principal == null) {
            return
        }
        principalByMemberId[memberId] = principal
    }

    fun remove(memberId: Long) {
        principalByMemberId.remove(memberId)
    }
}
