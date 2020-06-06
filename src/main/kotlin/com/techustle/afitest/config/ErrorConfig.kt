/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-03-19 11:19:44 
 * @Last Modified by: Wisdom Kwarteng
 * @Last Modified time: 2020-03-19 12:10:04
 * Desc: Config to get error message template from error.properties
 */
package com.techustle.afitest.config

import org.modelmapper.ModelMapper
import org.modelmapper.convention.NamingConventions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor


/**
 * loading errors confg template
 */
@Configuration
@PropertySource("classpath:error.properties")
class ErrorConfig {
    @Autowired
    private val env: Environment? = null
    fun getConfigValue(configKey: String?): String? {
        return env!!.getProperty(configKey!!)
    }

    @Bean
    fun modelMapper(): ModelMapper? {
        val modelMapper = ModelMapper()
        modelMapper.configuration.setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE).sourceNamingConvention = NamingConventions.JAVABEANS_MUTATOR
        return modelMapper
        // https://github.com/modelmapper/modelmapper/issues/212
    }

    @Bean
    fun methodValidationPostProcessor(): MethodValidationPostProcessor? {
        return MethodValidationPostProcessor()
    }
}