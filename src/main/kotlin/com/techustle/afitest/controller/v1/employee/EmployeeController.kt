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
    @GetMapping("/lawyers")
    fun getUsers(): Response<List<Employee>> {
        val lawyers : List<Employee> = employeeService.findEmployeeByRole("LAWYER")
       return Response.ok<List<Employee>>().setResponsePayload(lawyers)
    }

    @PostMapping("")
    fun signup(  @RequestBody @Valid userData : AddEmployeePayload): Response<Any> {
       var result = userData.password?.let { employeeService.signup(email =  userData.email, name = userData.name, password = it, role = userData.role, rate=userData.rate) }
        return   Response.ok<Any>().setResponsePayload(result)

    }




}