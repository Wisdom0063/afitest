package  com.techustle.afitest.service

import com.techustle.afitest.model.User
import com.techustle.afitest.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserServiceImpl(@Autowired var userRepository: UserRepository):UserService{

    override fun signup(user: User): User? {
        return  userRepository.save(user)

    }

    override fun findUserByEmail(email: String): Optional<User> {
            return userRepository.findById(1)
    }

}