package  com.techustle.afitest.exception

import com.techustle.afitest.dto.response.Response
import com.techustle.afitest.dto.response.ResponseError
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestControllerAdvice
class ItemControllerAdvice {

//    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
//    fun handleMethodArgumentNotValid(exception: MethodArgumentNotValidException): ValidationError {
//        val violations = exception.bindingResult.allErrors
//                .mapNotNull { error ->
//                    when (error) {
//                        is FieldError -> Violation(error.field, error.defaultMessage ?: "")
//                        is ObjectError -> Violation(error.objectName, error.defaultMessage ?: "")
//                        else -> null
//                    }
//                }
//                .toList()
//        return ValidationError(violations)
//    }

   @ExceptionHandler(value = [ValueInstantiationException::class])
   fun handleValueInstantiationException(ex: ValueInstantiationException): ResponseEntity<Any> {
      ex.printStackTrace()
      val fullErrordetails = ex.localizedMessage
     val message = fullErrordetails.split(regex = "problem: ".toRegex(), limit = 0)[1].split(regex = "\\n".toRegex(), limit = 0)[0]
      println(message)
      val response: Response<Any> = Response.exception()
      val error = ResponseError("$message", ex)
      response.setErrors(error)
      return ResponseEntity(response, HttpStatus.BAD_REQUEST)
   }

   @ExceptionHandler(value = [MissingKotlinParameterException::class])
   fun handleMissingKotlinParameter(ex: MissingKotlinParameterException): ResponseEntity<Any> {
      ex.printStackTrace()
      val fieldName = ex.path.joinToString(separator = ".") { it.fieldName }
      val response: Response<Any> = Response.exception()
      val error = ResponseError("$fieldName is required", ex)
      response.setErrors(error)
      return ResponseEntity(response, HttpStatus.BAD_REQUEST)
   }
}