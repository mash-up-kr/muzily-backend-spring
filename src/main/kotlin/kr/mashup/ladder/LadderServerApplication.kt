package kr.mashup.ladder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LadderServerApplication

fun main(args: Array<String>) {
    runApplication<LadderServerApplication>(*args)
}
