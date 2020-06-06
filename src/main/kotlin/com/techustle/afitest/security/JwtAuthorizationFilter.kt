package  com.techustle.afitest.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC512
import com.techustle.afitest.model.Employee
import com.techustle.afitest.repository.EmployeeRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthorizationFilter(authenticationManager: AuthenticationManager?, employeeRepository: EmployeeRepository) : BasicAuthenticationFilter(authenticationManager) {
    private val employeeRepository: EmployeeRepository = employeeRepository
    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        // Read the Authorization header, where the JWT token should be
        val header = request.getHeader("Authorization")

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response)
            return
        }

        // If header is present, try grab employee principal from database and perform authorization
        val authentication = getEmailPasswordAuthentication(request)
        SecurityContextHolder.getContext().authentication = authentication

        // Continue filter execution
        chain.doFilter(request, response)
    }

    private fun getEmailPasswordAuthentication(request: HttpServletRequest): Authentication? {
        val token = request.getHeader("Authorization")
                .replace("Bearer ", "")
        if (token != null) {
            // parse the token and validate it
            val userName: String = JWT.require(HMAC512("SomeSecretForJWTGeneration".toByteArray()))
                    .build()
                    .verify(token)
                    .getSubject()

            // Search in the DB if we find the employee by token subject (username)
            // If so, then grab employee details and create spring auth token using username, pass, authorities/roles
            if (userName != null) {
                val employee: Optional<Employee> = employeeRepository.findByEmail(userName)
                if(employee!=null && employee.isPresent){
                    val principal = UserPrincipal(employee.get())
                    return UsernamePasswordAuthenticationToken(userName, null, principal.authorities)

                }
                return  null

            }
            return null
        }
        return null
    }

}


