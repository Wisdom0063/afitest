/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-01 13:06:05 
 * @Last Modified by:   Wisdom Kwarteng 
 * @Last Modified time: 2020-06-01 13:06:05 
 */
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
     * Register a new employee
     *
     * @param User
     * @return
     */
    fun signup(email: String, name:String, password:String, role:String, rate:Double?): Employee

    /**
     * Search an existing employee by email
     *
     * @param email
     * @return
     */
    fun findEmployeeByEmail(email: String): Employee // /**




    /**
     * Search an existing employee by id
     *
     * @param employeeid
     * @return
     */
    fun findEmployeeById(employeeId: Long): Employee // /**

    /**
     * get lawyers
     *
     * @param role
     * @return
     */
    fun findEmployeeByRole(role: String): List<Employee> // /**

    fun login(email: String, password: String):Employee

}