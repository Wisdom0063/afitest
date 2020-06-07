package  com.techustle.afitest.model

import java.time.LocalDateTime
import javax.persistence.*


@Entity(name = "projects")
data class Project(
        @Id
        @GeneratedValue(
                strategy = GenerationType.IDENTITY)
        val id: Long=0,
        @Column(nullable=false, unique = true)
        val name: String =""
){

}