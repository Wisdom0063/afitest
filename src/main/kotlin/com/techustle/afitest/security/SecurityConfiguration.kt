package  com.techustle.afitest.security


import com.techustle.afitest.repository.EmployeeRepository
import org.springframework.boot.configurationprocessor.json.JSONException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
class SecurityConfiguration(private val userPrincipalDetailsService: UserPrincipalDetailsService,
                           private  val employeeRepository: EmployeeRepository) : WebSecurityConfigurerAdapter() {
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
    }

    @Throws(Exception::class, JSONException::class)
    override fun configure(http: HttpSecurity) {
        http // remove csrf and state in session because in jwt we do not need them
                .cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().accessDeniedHandler { request: HttpServletRequest, response: HttpServletResponse,
                                                           accessDeniedException: AccessDeniedException? -> AccessDeniedHandler.handleExeception(request,
                        response, accessDeniedException) }.authenticationEntryPoint { request: HttpServletRequest, response: HttpServletResponse,
                                                                                      authenticationException: AuthenticationException? -> AccessForbiddenHandler.handleExeception(request,
                        response, authenticationException) }.and() // add jwt filters
                .addFilter(JwtAuthorizationFilter(authenticationManager(), employeeRepository)).authorizeRequests() // configure access rules
                .antMatchers("/api/v1/auth/**").permitAll().antMatchers("/api/v1/employees/**").permitAll().antMatchers("/").permitAll().antMatchers("/main.js").permitAll().antMatchers("/printthis.js").permitAll()
                .anyRequest().authenticated()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val daoAuthenticationProvider = DaoAuthenticationProvider()
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder())
        daoAuthenticationProvider.setUserDetailsService(userPrincipalDetailsService)
        return daoAuthenticationProvider
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }

}