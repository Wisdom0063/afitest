/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-02 19:54:53 
 * @Last Modified by: Wisdom Kwarteng
 * @Last Modified time: 2020-06-07 19:55:36
 * @Desc: Security configuration for this application
 */
package  com.techustle.afitest.security


import com.techustle.afitest.repository.EmployeeRepository
import org.springframework.boot.configurationprocessor.json.JSONException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
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
                .addFilter(JwtAuthorizationFilter(authenticationManager(), employeeRepository)).authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/employees/**").hasRole("FINANCE") // configure access rules
                .antMatchers(HttpMethod.POST,"/api/v1/auth/login").permitAll().antMatchers(HttpMethod.GET,"/").permitAll().antMatchers(HttpMethod.GET,"/login").permitAll().antMatchers(HttpMethod.GET,"/main.js").permitAll().antMatchers(HttpMethod.GET,"/printthis.js").permitAll()
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