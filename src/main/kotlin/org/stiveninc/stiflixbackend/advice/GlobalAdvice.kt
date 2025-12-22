package org.stiveninc.stiflixbackend.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.stiveninc.stiflixbackend.exceptions.UserNotFoundException

@RestControllerAdvice
class GlobalAdvice {
        @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(msg: String = "User not found"): ResponseEntity<String> {
        return ResponseEntity.status(404).body(msg)
    }
}