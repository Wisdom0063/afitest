package com.techustle.afitest.security

import com.techustle.afitest.model.Employee
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*


class UserPrincipal(employee: Employee) : UserDetails {
    private val employee: Employee
    override fun getAuthorities(): Collection<GrantedAuthority> {
        val authorities: MutableList<GrantedAuthority> = ArrayList()

            val authority: GrantedAuthority = SimpleGrantedAuthority("ROLE_${employee.role}")
            authorities.add(authority)
        return authorities
    }

    override fun getPassword(): String {
        return employee.password
    }

    override fun getUsername(): String {
        return employee.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }


    init {
        this.employee = employee
    }
} 