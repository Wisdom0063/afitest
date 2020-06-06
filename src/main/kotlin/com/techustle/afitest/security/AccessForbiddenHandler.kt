package  com.techustle.afitest.security

import org.springframework.boot.configurationprocessor.json.JSONException
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.security.core.AuthenticationException
import java.io.IOException
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


object AccessForbiddenHandler {
    fun handleExeception(request: HttpServletRequest, response: HttpServletResponse,
                         authenticationException: AuthenticationException?) {
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