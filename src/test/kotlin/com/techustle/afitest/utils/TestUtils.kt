package  com.techustle.afitest.utils

import com.github.javafaker.Faker
import com.techustle.afitest.controller.v1.employee.payload.AddLawyerPayload
import org.springframework.core.ParameterizedTypeReference

var faker = Faker()



object CreateUrlWithPort {
    fun create(uri: String, port: Int): String {
        return "http://localhost:$port$uri"
    }
}

val responseType = object : ParameterizedTypeReference<Map<String, Any>>() {}

fun getAddLawyerPayload ():AddLawyerPayload{
    return AddLawyerPayload(name = faker.name().fullName(), email = "${faker.name().firstName()}@gmail.com", rate = 10.0)


}






