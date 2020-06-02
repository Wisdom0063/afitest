/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-01 13:05:37 
 * @Last Modified by:   Wisdom Kwarteng 
 * @Last Modified time: 2020-06-01 13:05:37 
 */
package  com.techustle.afitest.controller.v1.employee

import com.techustle.afitest.controller.v1.employee.payload.AddEmployeePayload
import com.techustle.afitest.dto.response.Response
import com.techustle.afitest.model.Employee
import com.techustle.afitest.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.concurrent.atomic.AtomicLong
import javax.validation.Valid


class Greeting(
        val id: Long,
        val content: String
)



@RestController
@RequestMapping("/api/v1/employees")
class UserController(@Autowired private  val employeeService: EmployeeService) {
    val counter = AtomicLong()
    @GetMapping("")
    fun getUsers(): Response<Greeting> {
       return Response.ok<Greeting>().setResponsePayload(Greeting(counter.incrementAndGet(), "Wisdom"))
    }

    @PostMapping("")
    fun signup(  @RequestBody @Valid userData : AddEmployeePayload): Response<Any> {
       var result = employeeService.signup(email =  userData.email, name = userData.name, password = userData.password, role = userData.role, rate=userData.rate)
        return   Response.ok<Any>().setResponsePayload(result)

    }




}