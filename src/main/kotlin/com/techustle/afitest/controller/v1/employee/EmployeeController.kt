/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-01 13:05:37 
 * @Last Modified by: Wisdom Kwarteng
 * @Last Modified time: 2020-06-07 19:57:30
 */
package com.techustle.afitest.controller.v1.employee

import com.techustle.afitest.controller.v1.employee.payload.AddFinancePayload
import com.techustle.afitest.controller.v1.employee.payload.AddLawyerPayload
import com.techustle.afitest.dto.mapper.EmployeeMapper
import com.techustle.afitest.dto.model.EmployeeDto
import com.techustle.afitest.dto.response.Response
import com.techustle.afitest.model.Employee
import com.techustle.afitest.service.EmployeeService
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

class Greeting(
    val id: Long,
    val content: String
)

@RestController
@RequestMapping("/api/v1/employees")
@CrossOrigin(origins = ["http://localhost:5000"])
class UserController(@Autowired private val employeeService: EmployeeService, @Autowired private val passwordEncoder: PasswordEncoder, @Autowired private val employeeMapper: EmployeeMapper) {
    @GetMapping("/law")
    fun getUsers(): Response<List<EmployeeDto>> {
        val lawyers: List<Employee> = employeeService.findEmployeeByRole("LAWYER")
        var employeeDtos: MutableList<EmployeeDto> = ArrayList<EmployeeDto>()

        for (lawyer in lawyers) {
            employeeDtos.add(employeeMapper.toEmployeeDto(lawyer))
        }

       return Response.ok<List<EmployeeDto>>().setResponsePayload(employeeDtos)
    }

    @PostMapping("/law")
    fun addLawyer(@RequestBody @Valid userData: AddLawyerPayload): Response<EmployeeDto> {
       var result = employeeService.signup(email = userData.email, name = userData.name, password = passwordEncoder.encode("123456"), role = "LAWYER", rate = userData.rate)
        return Response.ok<EmployeeDto>().setResponsePayload(employeeMapper.toEmployeeDto(result))
    }

    @PostMapping("/finance")
    fun addFinanceMember(@RequestBody @Valid userData: AddFinancePayload): Response<EmployeeDto> {
        var result = employeeService.signup(email = userData.email, name = userData.name, password = passwordEncoder.encode(userData.password), role = "FINANCE", rate = null)
        return Response.ok<EmployeeDto>().setResponsePayload(employeeMapper.toEmployeeDto(result))
    }

    @GetMapping("/try")
    fun tryy(): String {
        return "fdfdfjjff"
    }
}
