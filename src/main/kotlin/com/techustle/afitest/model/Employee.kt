package  com.techustle.afitest.model


import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank


enum class Role {
        FINANCE,
        LAWYER,
}

@Entity(name = "employees")
data class Employee(
        @Id
        @GeneratedValue(
                strategy = GenerationType.IDENTITY)
        val id : Long=0,
        @Column(nullable = false)
        val name : String="",
         @Email
        @Column(unique=true, nullable=false)
        val email : String="",
        @Column(nullable=false)
        val password: String="",
        val role:Role=Role.LAWYER
){

}