package com.techustle.afitest.service


import com.techustle.afitest.model.User
import org.springframework.stereotype.Service
import java.util.*

/**
 * Created by Arpit Khandelwal.
 */
@Service
interface UserService {
    /**
     * Register a new user
     *
     * @param User
     * @return
     */
    fun signup(user: User): User?

    /**
     * Search an existing user
     *
     * @param email
     * @return
     */
    fun findUserByEmail(email: String): Optional<User> // /**

}