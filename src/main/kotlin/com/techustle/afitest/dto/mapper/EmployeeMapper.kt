package  com.techustle.afitest.dto.mapper

// import com.starterkit.springboot.brs.dto.model.user.RoleDto;
import com.techustle.afitest.dto.model.EmployeeDto
import com.techustle.afitest.model.Employee
import com.techustle.afitest.repository.BillableRateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class EmployeeMapper(private  val billableRateRepository: BillableRateRepository) {
        fun toUserDto(employee: Employee): EmployeeDto {
            if (employee.role == "LAWYER") {
              val billableRate = billableRateRepository.findByEmployee(employee)

                if (billableRate != null) {
                    return EmployeeDto(id=employee.id, name = employee.name, email = employee.email, role = employee.role, rate = billableRate.get().rate)
                }
            }
            return EmployeeDto(id=employee.id, name = employee.name, email = employee.email, role = employee.role)

        }
    }
