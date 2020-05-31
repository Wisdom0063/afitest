package  com.techustle.afitest.controller

import com.techustle.afitest.dto.response.Response
import com.techustle.afitest.model.User
import com.techustle.afitest.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.concurrent.atomic.AtomicLong
import javax.validation.Valid
import javax.validation.constraints.*


class Greeting(
        val id: Long,
        val content: String
)

 open class SignupValidator(    @field:NotEmpty(message = "Password is required")
                                @field: Size(min =6, message = "Password must be a minimum of 6 characters")

                                var password: String
 ) {
     lateinit var name: @NotEmpty(message = "Email is required") String
    @Email(message = "Email is invalid")
     lateinit var email: @NotEmpty(message = "Email is required") String

}

@RestController
@RequestMapping("/api")
class UserController(@Autowired private  val userService: UserService) {
    val counter = AtomicLong()
    @GetMapping("")
    fun getUsers(): Response<Greeting> {
       return Response.ok<Greeting>().setResponsePayload(Greeting(counter.incrementAndGet(), "Wisdom"))
    }

    @PostMapping("")
    fun signup(  @RequestBody @Valid userData : SignupValidator): Response<Any> {
        var user = User(email =  userData.email, name = userData.name, password = userData.password, id=counter.incrementAndGet())
       var result = userService.signup(user)
        return   Response.ok<Any>().setResponsePayload(result)

    }




}