/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-03-19 14:40:05 
 * @Last Modified by: Wisdom Kwarteng
 * @Last Modified time: 2020-04-06 09:35:04
 */
package com.techustle.afitest.exception.handler

import  com.techustle.afitest.dto.response.Response
import com.techustle.afitest.dto.response.ResponseError
import com.techustle.afitest.exception.AfitestException
import com.techustle.afitest.exception.AfitestException.DuplicateEntityException
import com.techustle.afitest.exception.AfitestException.WrongCredentials
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*
import java.util.function.Consumer
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import kotlin.collections.HashSet
import kotlin.collections.MutableList
import kotlin.collections.MutableSet

@ControllerAdvice
@RestController
class CustomizedResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {
    //handle errors when @Valid throws an error
    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val response: Response<Any> = Response.badRequest()
        val errors = ResponseError("Incorrect information provided. Please try again", ex)
        // Get rejected fields and their error messages
        val fieldErrors: MutableList<FieldError> = ArrayList()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldError = error as FieldError
            fieldErrors.add(fieldError)
        })
        logger.info(fieldErrors.toString())
        errors.addValidationErrors(fieldErrors)
        response.setErrors(errors)
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    /**
     * Handle NoHandlerFoundException.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    override fun handleNoHandlerFoundException(
            ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val response: Response<Any> = Response.notFound()
        val message = String.format("Could not find the %s method for URL %s", ex.httpMethod, ex.requestURL)
        logger.info("Could not find the %s method for URL")
        val errors = ResponseError(message, ex)
        response.setErrors(errors)
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    override fun handleMissingServletRequestParameter(
            ex: MissingServletRequestParameterException, headers: HttpHeaders,
            status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val response: Response<Any> = Response.badRequest()
        val message = ex.parameterName + " parameter is missing"
        val errors = ResponseError(message, ex)
        response.setErrors(errors)
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }


    @ExceptionHandler(AfitestException.EntityNotFoundException::class)
    fun handleNotFountExceptions(ex: Exception, request: WebRequest?): ResponseEntity<Any> {
        val response: Response<Any> = Response.notFound()
        val isCustom = true
        val errors = ResponseError(ex.message, isCustom, ex)
        response.setErrors(errors)
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(DuplicateEntityException::class)
    fun handleDuplicateExceptions(ex: Exception, request: WebRequest?): ResponseEntity<Any> {
        val response: Response<Any> = Response.duplicateEntity()

        //Is custom means details is not added to error object
        val isCustom = true
        val errors = ResponseError(ex.message, isCustom, ex)
        response.setErrors(errors)
        return ResponseEntity(response, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(WrongCredentials::class)
    fun handleWrongCredentialExceptions(ex: Exception, request: WebRequest?): ResponseEntity<Any> {
        val response: Response<Any> = Response.wrongCredentials()

        //Is custom means details is not added to error object
        val isCustom = true
        val errors = ResponseError(ex.message, isCustom, ex)
        response.setErrors(errors)
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    /**
     * Handle ConstraintViolationException.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(
            ex: ConstraintViolationException, request: WebRequest?): ResponseEntity<Any> {
        val response: Response<Any> = Response.badRequest()
        val errors = ResponseError("Incorrect information provided. Please try again", ex)
        val constraintViolations: MutableSet<ConstraintViolation<*>> = HashSet()
        ex.constraintViolations.forEach(Consumer { error: ConstraintViolation<*> ->
            constraintViolations.add(error)
        })
        logger.info(constraintViolations.toString())
        errors.addValidationErrors(constraintViolations)
        response.setErrors(errors)
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    override fun handleTypeMismatch(ex: TypeMismatchException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        logger.info(ex.javaClass.name)
        //
        val message = ex.value.toString() + " value for " + ex.propertyName + " should be of type " + ex.requiredType
        val response: Response<Any> = Response.badRequest()
        val errors = ResponseError(message, ex)
        response.setErrors(errors)
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }



    @ExceptionHandler(Exception::class)
    fun handle(ex: Exception): ResponseEntity<Any> {
        ex.printStackTrace()
        val response: Response<Any> = Response.exception()
        val error = ResponseError("Internal server error", ex)
        response.setErrors(error)
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}