package com.techustle.afitest.v1
import com.fasterxml.jackson.databind.ObjectMapper
import com.techustle.afitest.controller.v1.employee.payload.AddFinancePayload
import com.techustle.afitest.controller.v1.employee.payload.AddLawyerPayload
import com.techustle.afitest.dto.model.EmployeeDto
import com.techustle.afitest.utils.CreateUrlWithPort
import com.techustle.afitest.utils.getAddFinanceMemberPayload
import com.techustle.afitest.utils.getAddLawyerPayload
import com.techustle.afitest.utils.responseType
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles


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
        val actual = response.statusCode
        assertTrue(actual.is2xxSuccessful);
        assertTrue(userDto.name== addLawyerPayload.name)
        assertTrue(userDto.email== addLawyerPayload.email)
        assertTrue(userDto.role== "LAWYER")

    }

    @Test
    @Throws(Exception::class)
    fun `It should return an bad request error if lawyers email is not provided`() {
      var payload :  Map<String, Any> = HashMap<String, Any>()
        payload.plus(Pair("name", "Test name")).plus(Pair("rate", 50.0))
        val entity: HttpEntity<Map<String, Any>> = HttpEntity<Map<String, Any>>(payload,
                headers)

        val response = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/employees/law", port),
                HttpMethod.POST, entity, responseType)

        val actual = response.statusCode
        assertTrue(actual.is4xxClientError);
    }

    @Test
    @Throws(Exception::class)
    fun `It should return an bad request error if lawyer's name is not provided`() {
        var payload :  Map<String, Any> = HashMap<String, Any>()
        payload.plus(Pair("email", "example@gmail.com")).plus(Pair("rate", 50.0))
        val entity: HttpEntity<Map<String, Any>> = HttpEntity<Map<String, Any>>(payload,
                headers)

        val response = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/employees/law", port),
                HttpMethod.POST, entity, responseType)

        val actual = response.statusCode
        assertTrue(actual.is4xxClientError);
    }

    @Test
    @Throws(Exception::class)
    fun `It should return an bad request error if lawyers rate is not provided`() {
        var payload :  Map<String, Any> = HashMap<String, Any>()
        payload.plus(Pair("email", "example@gmail.com")).plus(Pair("name", "test name"))
        val entity: HttpEntity<Map<String, Any>> = HttpEntity<Map<String, Any>>(payload,
                headers)

        val response = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/employees/law", port),
                HttpMethod.POST, entity, responseType)

        val actual = response.statusCode
        assertTrue(actual.is4xxClientError);
    }

    @Test
    @Throws(Exception::class)
    fun `It should successfully add a finance team member`() {
        val addFinancePayload = getAddFinanceMemberPayload()
        val entity: HttpEntity<AddFinancePayload> = HttpEntity<AddFinancePayload>(addFinancePayload,
                headers)

        val response = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/employees/finance", port),
                HttpMethod.POST, entity, responseType)
        val user = response.body?.get("payload")
        val mapper = ObjectMapper()
        val userDto: EmployeeDto = mapper.convertValue(user, EmployeeDto::class.java)
        val actual = response.statusCode
        assertTrue(actual.is2xxSuccessful);
        assertTrue(userDto.name== addFinancePayload.name)
        assertTrue(userDto.email== addFinancePayload.email)
        assertTrue(userDto.role== "FINANCE")

    }

    @Test
    @Throws(Exception::class)
    fun `It should return an bad request error if finance member password is not provided`() {
        var payload :  Map<String, Any> = HashMap<String, Any>()
        payload.plus(Pair("email", "example@gmail.com")).plus(Pair("name", "test name"))
        val entity: HttpEntity<Map<String, Any>> = HttpEntity<Map<String, Any>>(payload,
                headers)

        val response = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/employees/law", port),
                HttpMethod.POST, entity, responseType)

        val actual = response.statusCode
        assertTrue(actual.is4xxClientError);

    }

    @Test
    @Throws(Exception::class)
    fun `It should return an bad request error if finance member name is not provided`() {
        var payload :  Map<String, Any> = HashMap<String, Any>()
        payload.plus(Pair("email", "example@gmail.com")).plus(Pair("password", "testpassword"))
        val entity: HttpEntity<Map<String, Any>> = HttpEntity<Map<String, Any>>(payload,
                headers)

        val response = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/employees/law", port),
                HttpMethod.POST, entity, responseType)

        val actual = response.statusCode
        assertTrue(actual.is4xxClientError);

    }


    @Test
    @Throws(Exception::class)
    fun `It should return an bad request error if finance member email is not provided`() {
        var payload :  Map<String, Any> = HashMap<String, Any>()
        payload.plus(Pair("name", "test name")).plus(Pair("password", "testpassword"))
        val entity: HttpEntity<Map<String, Any>> = HttpEntity<Map<String, Any>>(payload,
                headers)

        val response = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/employees/law", port),
                HttpMethod.POST, entity, responseType)

        val actual = response.statusCode
        assertTrue(actual.is4xxClientError);

    }

    companion object {
        @AfterAll
        fun deleteUser() {
           println("Done testinggggggggggggggggggggggggg. Hope you are fineeeeeeeeeeeeeeeeeeeeeeeee")
        }
    }
}