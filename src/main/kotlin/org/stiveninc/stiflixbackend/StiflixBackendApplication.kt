package org.stiveninc.stiflixbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class StiflixBackendApplication

fun main(args: Array<String>) {
    runApplication<StiflixBackendApplication>(*args)
}
