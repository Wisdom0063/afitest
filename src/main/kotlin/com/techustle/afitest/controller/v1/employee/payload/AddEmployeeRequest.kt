package  com.techustle.afitest.controller.v1.employee.payload

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

 class AddEmployeeRequest(   @field:NotEmpty(message = "Password is required")
                               @field: Size(min =6, message = "Password must be a minimum of 6 characters")

                             var password: String,
                              @field: NotEmpty(message = "name is required")
                              var name: String,
                            @field: Email(message = "Email is invalid")
                             @field: NotEmpty(message = "Email is required")
                             var email:  String,
                             @field: NotEmpty(message = "role is required")
                             var role: String

 ) {


}