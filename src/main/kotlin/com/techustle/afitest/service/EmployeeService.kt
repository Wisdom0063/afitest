package com.techustle.afitest.service


import com.techustle.afitest.model.Employee
import org.springframework.stereotype.Service
import java.util.*

/**
 * Created by Arpit Khandelwal.
 */
@Service
interface EmployeeService {
    /**
     * Register a new user
     *
     * @param User
     * @return
     */
    fun signup(user: Employee): Employee?

    /**
     * Search an existing user
     *
     * @param email
     * @return
     */
    fun findUserByEmail(email: String): Optional<Employee> // /**

}