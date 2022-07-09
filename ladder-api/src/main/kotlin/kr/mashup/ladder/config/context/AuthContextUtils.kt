package kr.mashup.ladder.config.context

object AuthContextUtils {

    private val AUTH_CONTEXT_THREAD_LOCAL: ThreadLocal<AuthContext> = ThreadLocal<AuthContext>()

    fun get(): AuthContext? {
        return AUTH_CONTEXT_THREAD_LOCAL.get()
    }

    fun set(authContext: AuthContext) {
        AUTH_CONTEXT_THREAD_LOCAL.set(authContext)
    }

    fun remove() {
        AUTH_CONTEXT_THREAD_LOCAL.remove()
    }

}
