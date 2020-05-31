package  com.techustle.afitest.model


import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank


@Entity(name = "users")
data class User(
        @Id
        @GeneratedValue(
                strategy = GenerationType.IDENTITY)
        val id : Long=0,
        @Column(nullable = false)
        val name : String="",
         @Email
        @Column(unique=true)
        val email : String="",
        @Column(nullable=false)
        val password: String=""
){

}