package kr.mashup.ladder.config.context

object WsRoomSessionContext {
    private val sessionIdsByRoomId: MutableMap<Long, MutableSet<String>> = mutableMapOf()

    fun isEmpty(roomId: Long): Boolean {
        val sessionIds = sessionIdsByRoomId[roomId]
        return sessionIds?.isEmpty() ?: true
    }

    fun add(roomId: Long, sessionId: String) {
        val sessionIds = sessionIdsByRoomId.getOrDefault(roomId, mutableSetOf())
        sessionIds.add(sessionId)
        sessionIdsByRoomId[roomId] = sessionIds
    }

    fun remove(roomId: Long, sessionId: String) {
        val sessionIds = sessionIdsByRoomId.getOrDefault(roomId, mutableSetOf())
        sessionIds.remove(sessionId)
        sessionIdsByRoomId[roomId] = sessionIds
    }

    fun remove(sessionId: String) {
        sessionIdsByRoomId.forEach { e -> e.value.remove(sessionId) }
    }
}
