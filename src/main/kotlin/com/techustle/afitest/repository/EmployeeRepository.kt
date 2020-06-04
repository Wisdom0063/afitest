/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-01 13:05:52 
 * @Last Modified by:   Wisdom Kwarteng 
 * @Last Modified time: 2020-06-01 13:05:52 
 */
package  com.techustle.afitest.repository;

import com.techustle.afitest.model.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.validation.constraints.Email


@Repository
interface  EmployeeRepository : JpaRepository<Employee, Long>{
    fun findByEmail(email: String):Optional<Employee>
    fun findByRole(role: String):List<Employee>


}