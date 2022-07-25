package kr.mashup.ladder.config.context

import java.security.Principal

object WsMemberPrincipalContext {
    private val principalsByMemberId: MutableMap<Long, MutableSet<Principal>> = mutableMapOf()

    fun get(memberId: Long): Set<Principal> {
        return principalsByMemberId.getOrDefault(memberId, setOf())
    }

    fun add(memberId: Long, principal: Principal?) {
        if (principal == null) {
            return
        }

        val principals = principalsByMemberId.getOrDefault(memberId, mutableSetOf())
        principals.add(principal)
        principalsByMemberId[memberId] = principals
    }

    fun remove(memberId: Long, principal: Principal?) {
        if (principal == null) {
            return
        }

        val principals = principalsByMemberId.getOrDefault(memberId, mutableSetOf())
        principals.remove(principal)
        principalsByMemberId[memberId] = principals
    }
}
