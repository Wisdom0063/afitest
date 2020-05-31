package  com.techustle.afitest.repository;

import com.techustle.afitest.model.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface  EmployeeRepository : JpaRepository<Employee, Long>{

}