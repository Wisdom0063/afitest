/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-01 13:05:26 
 * @Last Modified by: Wisdom Kwarteng
 * @Last Modified time: 2020-06-07 19:57:23
 */
package com.techustle.afitest.controller.v1.employee.payload

import javax.validation.constraints.*

class AddLawyerPayload(
    @field: NotEmpty(message = "name is required")
        var name: String,
    @field: Email(message = "Email is invalid")
       @field: NotEmpty(message = "Email is required")
       var email: String,
    @field:Min(value = 0, message = "Rate must be greater than or equal to 0")
    @field:NotNull(message = "Billabe rate is required")
    var rate: Double
)
