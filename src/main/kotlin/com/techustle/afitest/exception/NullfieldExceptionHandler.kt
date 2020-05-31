package  com.techustle.afitest.exception

import com.techustle.afitest.dto.response.Response
import com.techustle.afitest.dto.response.ResponseError
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
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

   @ExceptionHandler(value = [MissingKotlinParameterException::class])
   fun handleMissingKotlinParameter(ex: MissingKotlinParameterException): ResponseEntity<Any> {
      ex.printStackTrace()
      val fieldName = ex.path.joinToString(separator = ".") { it.fieldName }
      val response: Response<Any> = Response.exception()
      val error = ResponseError("$fieldName must not be null", ex)
      response.setErrors(error)
      return ResponseEntity(response, HttpStatus.BAD_REQUEST)
   }
}