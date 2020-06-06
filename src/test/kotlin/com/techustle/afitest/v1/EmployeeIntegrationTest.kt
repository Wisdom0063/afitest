package com.techustle.afitest.v1
import com.fasterxml.jackson.databind.ObjectMapper
import com.techustle.afitest.controller.v1.employee.payload.AddLawyerPayload
import com.techustle.afitest.dto.model.EmployeeDto
import com.techustle.afitest.utils.CreateUrlWithPort
import com.techustle.afitest.utils.getAddLawyerPayload
import com.techustle.afitest.utils.responseType
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import javax.validation.constraints.AssertTrue


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AuthIntegrationTest {
    // bind the above RANDOM_PORT
    @LocalServerPort
      var port:Int=0

    @Autowired
   private val restTemplate  = TestRestTemplate()

    var headers = HttpHeaders()


    @Test
    @DisplayName("It should successfully signup a user")
    @Throws(Exception::class)
    fun `It should successfully add a lawyer`() {
        val addLawyerPayload = getAddLawyerPayload()
        val entity: HttpEntity<AddLawyerPayload> = HttpEntity<AddLawyerPayload>(addLawyerPayload,
                headers)

        val response = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/employees/law", port),
                HttpMethod.POST, entity, responseType)
        val user = response.body?.get("payload")
       val mapper = ObjectMapper()
       val userDto: EmployeeDto = mapper.convertValue(user, EmployeeDto::class.java)
        println(userDto.name)
        val actual = response.statusCode
        assertTrue(actual.is2xxSuccessful);
        assertTrue(userDto.name== addLawyerPayload.name)
    }

    companion object {
        @AfterAll
        fun deleteUser() {
           println("Done testinggggggggggggggggggggggggg. Hope you are fineeeeeeeeeeeeeeeeeeeeeeeee")
        }
    }
}