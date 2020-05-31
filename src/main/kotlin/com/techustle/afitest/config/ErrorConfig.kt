/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-03-19 11:19:44 
 * @Last Modified by: Wisdom Kwarteng
 * @Last Modified time: 2020-03-19 12:10:04
 * Desc: Config to get error message template from error.properties
 */
package com.techustle.afitest.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment

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
}