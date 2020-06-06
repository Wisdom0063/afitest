/*
 * @Author: Wisdom Kwarteng
 * @Date: 2020-03-19 10:24:30
 * @Last Modified by: Wisdom Kwarteng
 * @Last Modified time: 2020-03-26 19:00:37
 * Desc: Bean configuration for store2Door
 */
package com.techustle.afitest.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter


/**
 * Get access to configuration properties
 */
@Configuration
class WebConfig : WebMvcConfigurerAdapter() {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
    }
}