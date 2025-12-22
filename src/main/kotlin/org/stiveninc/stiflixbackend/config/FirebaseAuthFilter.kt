package org.stiveninc.stiflixbackend.config

import com.google.firebase.auth.FirebaseAuth
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

class FirebaseAuthFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization")

        if (header != null && header.startsWith("Bearer ")) {
            val token = header.removePrefix("Bearer ")

            try {
                val decoded = FirebaseAuth.getInstance().verifyIdToken(token)

                val uid = decoded.uid
                val role = decoded.claims["role"] as? String ?: "VIEWER"

                val authorities = listOf(
                    SimpleGrantedAuthority("ROLE_$role")
                )

                val authentication = UsernamePasswordAuthenticationToken(
                    uid,
                    null,
                    authorities
                )

                SecurityContextHolder.getContext().authentication = authentication

            } catch (ex: Exception) {
                response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid or expired Firebase token"
                )
                return
            }
        }

        filterChain.doFilter(request, response)
    }
}