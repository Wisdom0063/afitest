/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-04 19:53:45 
 * @Last Modified by:   Wisdom Kwarteng 
 * @Last Modified time: 2020-06-07 19:53:45 
 */
package com.techustle.afitest.security

import java.io.IOException
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.boot.configurationprocessor.json.JSONException
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.security.core.AuthenticationException

object AccessForbiddenHandler {
    fun handleExeception(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authenticationException: AuthenticationException?
    ) {
        response.contentType = "application/json;charset=UTF-8"
        response.status = HttpServletResponse.SC_FORBIDDEN
        try {
            // set default status and message
            val message = "Access denied"
            val status = "FORBIDDEN"
            response.writer.write(JSONObject().put("status", status)
                    .put("error", JSONObject().put("timestamp", Date()).put("message", message)).toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
