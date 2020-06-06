/*
 * @Author: Wisdom Kwarteng
 * @Date: 2020-06-01 13:05:26
 * @Last Modified by:   Wisdom Kwarteng
 * @Last Modified time: 2020-06-01 13:05:26
 */
package  com.techustle.afitest.controller.v1.employee.payload

import java.lang.RuntimeException
import javax.validation.constraints.*

class AddFinancePayload(
        @field: NotEmpty(message = "name is required")
        var name: String,
        @field: Email(message = "Email is invalid")
        @field: NotEmpty(message = "Email is required")
        var email:  String,
        @field:Size(min=6, max = 100, message = "Password length must be between 6 and 100 character")
        @field:NotEmpty(message = "Password is required")
        var password: String
) {

}