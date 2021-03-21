package se.ifmo.hacknu

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HacknuApplication

fun main(args: Array<String>) {
    runApplication<HacknuApplication>(*args)
}
