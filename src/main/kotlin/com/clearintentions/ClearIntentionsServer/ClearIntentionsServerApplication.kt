package com.clearintentions.ClearIntentionsServer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ClearIntentionsServerApplication

fun main(args: Array<String>) {
	runApplication<ClearIntentionsServerApplication>(*args)
}
