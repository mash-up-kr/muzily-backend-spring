package kr.mashup.ladder.util

import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.Table

@Component
class DatabaseCleanup : InitializingBean {
    @PersistenceContext
    lateinit var entityManager: EntityManager

    lateinit var tableNames: List<String>

    override fun afterPropertiesSet() {
        tableNames = entityManager.metamodel.entities
            .filter { it.javaType.getAnnotation(Table::class.java) != null }
            .map { it.javaType.getAnnotation(Table::class.java) }
            .map { it.name }
    }

    @Transactional
    fun execute() {
        entityManager.flush()
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
        for (tableName in tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
    }
}
