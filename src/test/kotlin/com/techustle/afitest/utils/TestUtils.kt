package  com.techustle.afitest.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.github.javafaker.Faker
import org.springframework.core.ParameterizedTypeReference
import java.util.*

var faker = Faker()



object CreateUrlWithPort {
    fun create(uri: String, port: Int): String {
        return "http://localhost:$port$uri"
    }
}

class  Listclass{
   private  val status:String?=null
    private  val payload: List<Any>?=null
}

val responseType = object : ParameterizedTypeReference<Map<String, Any>>() {}
val listResponseType = object : ParameterizedTypeReference<Any>() {}


fun generateToken(email:String):String {
    val token: String = JWT.create().withSubject(email)
            .withExpiresAt(Date(System.currentTimeMillis() + (10 * 24 * 60 * 60000)))
            .sign(Algorithm.HMAC512("SomeSecretForJWTGeneration"));

    return  token

}








