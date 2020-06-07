/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-04 19:52:57 
 * @Last Modified by: Wisdom Kwarteng
 * @Last Modified time: 2020-06-07 19:54:44
 * @Desc: Handler when user do not have access
 */
package  com.techustle.afitest.security

import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.security.access.AccessDeniedException
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


object AccessDeniedHandler {
    fun handleExeception(request: HttpServletRequest, response: HttpServletResponse, accessDeniedException: AccessDeniedException?) {
        response.contentType = "application/json;charset=UTF-8"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        try {
            response.writer
                    .write(JSONObject().put("status", "UNAUTHORIZED")
                            .put("error", JSONObject().put("timestamp", Date()).put("message",
                                    "You are not authorize to perform this request"))
                            .toString())
        } catch (e: Exception) {
        }
    }
}