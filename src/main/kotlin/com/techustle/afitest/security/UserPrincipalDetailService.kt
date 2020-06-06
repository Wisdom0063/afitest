
package com.techustle.afitest.security
//Store2Door packages

import com.techustle.afitest.model.Employee
import com.techustle.afitest.repository.EmployeeRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ExecutionException

@Service
class UserPrincipalDetailsService(employeeRepository: EmployeeRepository) : UserDetailsService {
    private val employeeRepository: EmployeeRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails? {
        var userPrincipal: UserDetails? =null
       val employee = employeeRepository.findByEmail(email)

         if (employee.isPresent()) {
             userPrincipal = UserPrincipal(employee.get())
        } else {
             userPrincipal = null
         }

        return  userPrincipal
         }




    init {
        this.employeeRepository = employeeRepository
    }
}