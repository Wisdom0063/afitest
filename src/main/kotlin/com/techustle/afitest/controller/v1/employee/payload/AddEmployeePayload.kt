/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-01 13:05:26 
 * @Last Modified by:   Wisdom Kwarteng 
 * @Last Modified time: 2020-06-01 13:05:26 
 */
package  com.techustle.afitest.controller.v1.employee.payload

import java.lang.RuntimeException
import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

 class AddEmployeePayload(
//         @field:NotEmpty(
//         message = "Password is required")
//                               @field: Size(min =6, message = "Password must be a minimum of 6 characters")

                             var password: String?=null,
                          @field: NotEmpty(message = "name is required")
                              var name: String,
                          @field: Email(message = "Email is invalid")
                             @field: NotEmpty(message = "Email is required")
                             var email:  String,
                          @field: NotEmpty(message = "role is required")
                             var role: String,
                          @field:Min(value = 0, message = "Rate must be greater than or equal to 0")
                          var rate: Double? = null
 ) {
     init {
         if (role == "LAWYER" && rate == null) {
             throw RuntimeException("Rate is required")

         }

         if (password == null) {
            password="123456"
         }

     }
 }