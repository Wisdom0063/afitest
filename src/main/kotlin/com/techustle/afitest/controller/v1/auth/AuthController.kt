package  com.techustle.afitest.controller.v1.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC512
import com.techustle.afitest.controller.v1.auth.payload.LoginPayload
import com.techustle.afitest.dto.mapper.EmployeeMapper
import com.techustle.afitest.dto.model.EmployeeDto
import com.techustle.afitest.dto.response.Response
import com.techustle.afitest.model.Employee
import com.techustle.afitest.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/auth")
class  AuthController(@Autowired private val employeeService: EmployeeService, @Autowired private val  employeeMapper: EmployeeMapper){
    @PostMapping("/login")
    fun loginEmployee(@RequestBody @Valid loginData:LoginPayload):Response<EmployeeDto>{
        val employee = employeeService.login(loginData.email, loginData.password)
        // Create JWT Token
        val token: String = JWT.create().withSubject(loginData.email)
                .withExpiresAt(Date(System.currentTimeMillis() + (10 * 24 * 60 * 60000)))
                .sign(HMAC512("SomeSecretForJWTGeneration"));
      var  employeeDto :EmployeeDto = employeeMapper.toEmployeeDto(employee)
        employeeDto.token = token
        return  Response.ok<EmployeeDto>().setResponsePayload(employeeDto)

    }

}