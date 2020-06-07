/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-01 13:06:10 
 * @Last Modified by:   Wisdom Kwarteng 
 * @Last Modified time: 2020-06-01 13:06:10 
 */
package com.techustle.afitest.service

import com.techustle.afitest.exception.EntityType.EMPLOYEE
import com.techustle.afitest.exception.ExceptionType.*
import com.techustle.afitest.exception.ThrowCustomException
import com.techustle.afitest.model.BillableRate
import com.techustle.afitest.model.Employee
import com.techustle.afitest.model.Role
import com.techustle.afitest.repository.BillableRateRepository
import com.techustle.afitest.repository.EmployeeRepository
import java.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class EmployeeServiceImpl(@Autowired var employeeRepository: EmployeeRepository, @Autowired private val passwordEncoder: PasswordEncoder, @Autowired private val billableRateRepository: BillableRateRepository) : EmployeeService {
/**
 * <p>Signs up an employee</p>
  * <p>Ensure email does not exist</p>
  * <p>Check if user is lawyer and adds his/her billable rate</p>

 */
    override fun signup(email: String, name: String, password: String, role: Role, rate: Double?): Employee {
        var existingEmployee = employeeRepository.findByEmail(email)
        if (existingEmployee.isPresent) {
            throw ThrowCustomException.exception(EMPLOYEE, DUPLICATE_ENTITY, email)
        }
        var employee = Employee(email = email, name = name, password = password, role = role)

        val result = employeeRepository.save(employee)

        if (role == Role.LAWYER) {
            val billableRate = BillableRate(employee = result, rate = rate as Double)
            billableRateRepository.save(billableRate)
        }

        return result
    }

    /**
     * Find employee by email
     * It throws an error if employee is not found
     */

    override fun findEmployeeByEmail(email: String): Employee {
            var optionalEmployee = employeeRepository.findByEmail(email)
        if (optionalEmployee.isPresent) {
            return optionalEmployee.get()
        }

        throw ThrowCustomException.exception(EMPLOYEE, ENTITY_NOT_FOUND, email)
    }

        /**
     * Find employee by email
     * It throws an error if employee is not found
     */

    override fun findEmployeeById(employeeId: Long): Employee {
        var optionalEmployee = employeeRepository.findById(employeeId)
        if (optionalEmployee.isPresent) {
            return optionalEmployee.get()
        }

        throw ThrowCustomException.exception(EMPLOYEE, ENTITY_NOT_FOUND)
    }

    override fun findEmployeeByRole(role: String): List<Employee> {
        return employeeRepository.findByRole(role)
    }

    /**
     * Login a user
     * it throw error is email and password do not match
     */

    override fun login(email: String, password: String): Employee {
        var optionalEmployee = employeeRepository.findByEmail(email)
        if (optionalEmployee.isPresent) {
            val employee = optionalEmployee.get()
         val matches: Boolean = passwordEncoder.matches(password, employee.password)
            if (matches) {
                return employee
            }
            throw ThrowCustomException.exception(WRONG_CREDENTIAL, "eamil or password incorrect")
        }

        throw ThrowCustomException.exception(WRONG_CREDENTIAL, "eamil or password incorrect")
    } }
