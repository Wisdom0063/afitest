package  com.techustle.afitest.dto.model


import com.fasterxml.jackson.annotation.JsonInclude
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank


data class EmployeeDto(
        val id : Long?=null,
        val name : String?=null,
        val email : String?=null,
        val role:String?=null,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var token:String?=null,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val rate:Double?=null
){

}