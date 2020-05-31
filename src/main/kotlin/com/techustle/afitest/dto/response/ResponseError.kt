package  com.techustle.afitest.dto.response
/*
 * @Author: Wisdom Kwarteng
 * @Date: 2020-03-19 12:39:05
 * @Last Modified by: Wisdom Kwarteng
 * @Last Modified time: 2020-03-19 13:39:03
 * Desc: This defines error structure when a request throws an error
 */
//import co.africa8d.authentication.util.LowerCaseClassNameResolver
import com.techustle.afitest.dto.response.SubError
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import java.time.LocalDateTime
import java.util.*
import java.util.function.Consumer
import javax.validation.ConstraintViolation


@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
//@JsonTypeIdResolver(LowerCaseClassNameResolver::class)
@JsonInclude(JsonInclude.Include.NON_NULL)
class ResponseError private constructor() {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private val timestamp: LocalDateTime = LocalDateTime.now()
    var message: String? = null

    @JsonInclude(JsonInclude.Include.NON_NULL)
    var details: String? = null

    @JsonInclude(JsonInclude.Include.NON_NULL)
    var subErrors: MutableList<SubError>? = null

    /**
     * Error without a message
     * @param ex
     */
    constructor(ex: Throwable) : this() {
        message = "Unexpected error"
        details = ex.localizedMessage
    }

    /**
     * Error with error message
     * @param message
     * @param ex
     */
    constructor(message: String?, ex: Throwable) : this() {
        this.message = message
        details = ex.localizedMessage
    }

    /**
     * A custom error thrown with an error message
     * @param message
     * @param isCustom
     * @param ex
     */
    constructor(message: String?, isCustom: Boolean?, ex: Throwable?) : this() {
        this.message = message
        // this.details = ex.getLocalizedMessage();
    }

    /**
     * Add suberrors to the error object
     * @param subError
     */
    private fun addSubError(subError: SubError) {
        if (subErrors == null) {
            subErrors = ArrayList<SubError>()
        }
        subErrors!!.add(subError)
    }

    /**
     * Add validation errors with field and rejected values
     * @param object
     * @param field
     * @param rejectedValue
     * @param message
     */
    private fun addValidationError(errorObj: String, field: String, rejectedValue: Any?, message: String?) {
        addSubError(RequestValidationError(errorObj, field, rejectedValue, message))
    }

    /**
     *
     * @param object
     * @param message
     */
    private fun addValidationError(errorObj: String, message: String?) {
        addSubError(RequestValidationError(errorObj, message))
    }

    private fun addValidationError(fieldError: FieldError) {
        this.addValidationError(
                fieldError.objectName,
                fieldError.field,
                fieldError.rejectedValue,
                fieldError.defaultMessage)
    }

    fun addValidationErrors(fieldErrors: List<FieldError>) {
        fieldErrors.forEach(Consumer { fieldError: FieldError -> this.addValidationError(fieldError) })
    }

    private fun addValidationError(objectError: ObjectError) {
        this.addValidationError(
                objectError.objectName,
                objectError.defaultMessage)
    }

    fun addValidationError(globalErrors: List<ObjectError>) {
        globalErrors.forEach(Consumer { objectError: ObjectError -> this.addValidationError(objectError) })
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
     *
     * @param cv the ConstraintViolation
     */
    private fun addValidationError(cv: ConstraintViolation<*>) {
        this.addValidationError(
                cv.rootBeanClass.simpleName,
                cv.propertyPath.toString(),
                cv.invalidValue,
                cv.message)
    }

    fun addValidationErrors(constraintViolations: Set<ConstraintViolation<*>>) {
        constraintViolations.forEach(Consumer { cv: ConstraintViolation<*> -> this.addValidationError(cv) })
    }

}