/*
 * @Author: Wisdom Kwarteng
 * @Date: 2020-03-19 10:24:30
 * @Last Modified by: Wisdom Kwarteng
 * @Last Modified time: 2020-03-26 19:00:37
 * Desc: Bean configuration for store2Door
 */
package com.techustle.afitest.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

/**
 * Get access to configuration properties
 */
@Configuration
class CorsConfig {


    @Bean
    fun corsFilter(): FilterRegistrationBean<*> {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        val bean: FilterRegistrationBean<*> = FilterRegistrationBean(CorsFilter(source))
        bean.order = 0
        return bean
    }
}