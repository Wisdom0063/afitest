package  com.techustle.afitest.repository;

import com.techustle.afitest.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface  UserRepository : JpaRepository<User, Long>{

}