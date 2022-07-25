package kr.mashup.ladder.config.context

import com.sun.security.auth.NTDomainPrincipal
import java.security.Principal
import java.util.*

class WsPrincipal : Principal {
    private val name: String = UUID.randomUUID().toString()

    override fun getName(): String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        val that = other as NTDomainPrincipal
        return name == that.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
