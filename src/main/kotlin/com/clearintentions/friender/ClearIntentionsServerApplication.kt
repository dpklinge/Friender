package com.clearintentions.friender

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@EnableR2dbcRepositories
class ClearIntentionsServerApplication

fun main(args: Array<String>) {
    runApplication<ClearIntentionsServerApplication>(*args)
}
