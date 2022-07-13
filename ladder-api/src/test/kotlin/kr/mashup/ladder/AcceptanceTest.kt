package kr.mashup.ladder

import io.restassured.RestAssured
import kr.mashup.ladder.util.DatabaseCleanup
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AcceptanceTest {
    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var databaseCleanup: DatabaseCleanup

    @BeforeEach
    fun setUp() {
        RestAssured.port = port
        databaseCleanup.execute()
    }
}
