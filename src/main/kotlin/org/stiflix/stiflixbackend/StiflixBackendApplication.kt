package org.stiflix.stiflixbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StiflixBackendApplication

fun main(args: Array<String>) {
    runApplication<StiflixBackendApplication>(*args)
}
