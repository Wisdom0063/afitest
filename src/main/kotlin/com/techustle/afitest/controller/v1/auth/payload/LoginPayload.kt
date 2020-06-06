/*
 * @Author: Wisdom Kwarteng
 * @Date: 2020-06-01 13:04:58
 * @Last Modified by:   Wisdom Kwarteng
 * @Last Modified time: 2020-06-01 13:04:58
 */
package  com.techustle.afitest.controller.v1.auth.payload

import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class LoginPayload(
        @field: NotEmpty(message = "Email is required")
        @field: Email(message = "Provide a valid email")
        val email: String,
        @field: NotEmpty(message = "Password is required")
        val password: String
){

}