package  com.techustle.afitest.controller.v1.employee

import com.techustle.afitest.controller.v1.employee.payload.AddEmployeeRequest
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
    fun signup(  @RequestBody @Valid userData : AddEmployeeRequest): Response<Any> {
        var user = Employee(email =  userData.email, name = userData.name, password = userData.password)
       var result = employeeService.signup(user)
        return   Response.ok<Any>().setResponsePayload(result)

    }




}