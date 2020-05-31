package  com.techustle.afitest.service

import com.techustle.afitest.model.Employee
import com.techustle.afitest.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class EmployeeServiceImpl(@Autowired var employeeRepository: EmployeeRepository):EmployeeService{

    override fun signup(user: Employee): Employee? {
        return  employeeRepository.save(user)

    }

    override fun findUserByEmail(email: String): Optional<Employee> {
            return employeeRepository.findById(1)
    }

}