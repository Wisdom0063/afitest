/*
 * @Author: Wisdom Kwarteng
 * @Date: 2020-03-16 19:58:20
 * @Last Modified by: Wisdom Kwarteng
 * @Last Modified time: 2020-03-19 14:00:57
 * Desc: Handles all exceptions and reformat them nicely
 */
package com.techustle.afitest.exception

import com.techustle.afitest.config.ErrorConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.text.MessageFormat
import java.util.*

@Component
class AfitestException @Autowired constructor(errorConfig: ErrorConfig?) {
    class EntityNotFoundException(message: String?) : RuntimeException(message) {
        companion object {
            private const val serialVersionUID = 1L
        }
    }

    class DuplicateEntityException(message: String?) : RuntimeException(message) {
        companion object {
            private const val serialVersionUID = 2L
        }
    }

    class WrongCredentials(message: String?) : RuntimeException(message) {
        companion object {
            private const val serialVersionUID = 2L
        }
    }

    companion object {
        private var errorConfig: ErrorConfig? = null

        /**
         * Returns a new RuntimeException based on message template and args
         *
         * @param messageTemplate
         * @param args
         * @return
         */
        fun throwException(message: String?): RuntimeException {
            return RuntimeException(message)
        }

        /**
         * Returns a new RuntimeException based on message template and args
         *
         * @param messageTemplate
         * @param args
         * @return
         */
        fun throwException(messageTemplate: String, vararg args: String?): RuntimeException {
            return RuntimeException(format(messageTemplate, *args as Array<out String>))
        }

        /**
         * Returns a new RuntimeError based on entityType, exceptionType and args
         *
         * @param entityType
         * @param exceptionType
         * @param args
         * @return
         */
        fun throwException(entityType: EntityType, exceptionType: ExceptionType, vararg args: String?): RuntimeException {
            val messageTemplate = getMessageTemplate(entityType, exceptionType)
            return throwException(exceptionType, messageTemplate, *args)
        }

        /**
         * Returns new RuntimeException based on EntityType, ExceptionType, id and args
         *
         * @param entityType
         * @param exceptionType
         * @param args
         * @return
         */
        fun throwExceptionWithId(entityType: EntityType, exceptionType: ExceptionType, id: String, vararg args: String?): RuntimeException {
            val messageTemplate = getMessageTemplate(entityType, exceptionType) + "." + id
            return throwException(exceptionType, messageTemplate, *args)
        }

        /**
         * Returns new RuntimeException based on EntityType, ExceptionType, messageTemplate and args
         *
         * @param entityType
         * @param exceptionType
         * @param messageTemplate
         * @param args
         * @return
         */
        fun throwExceptionWithTemplate(entityType: EntityType?, exceptionType: ExceptionType, messageTemplate: String, vararg args: String?): RuntimeException {
            return throwException(exceptionType, messageTemplate, *args)
        }

        /**
         * Returns new RuntimeException based on exceptionType, template and args
         *
         * @param messageTemplate
         * @param args
         * @return
         */
        fun throwException(exceptionType: ExceptionType, messageTemplate: String, vararg args: String?): RuntimeException {
            if (ExceptionType.ENTITY_NOT_FOUND == exceptionType) {
                return EntityNotFoundException(format(messageTemplate, *args as Array<out String>))
            } else if (ExceptionType.DUPLICATE_ENTITY == exceptionType) {
                return DuplicateEntityException(format(messageTemplate, *args as Array<out String>))
            } else if (ExceptionType.WRONG_CREDENTIAL == exceptionType) {
                return WrongCredentials(format(messageTemplate, *args as Array<out String>))
            }
            return RuntimeException(format(messageTemplate, *args as Array<out String>))
        }

        private fun getMessageTemplate(entityType: EntityType, exceptionType: ExceptionType): String {
            return entityType.name.plus(".").plus(exceptionType.value).toLowerCase()
        }

        private fun format(template: String, vararg args: String): String {
            val templateContent = Optional.ofNullable(errorConfig!!.getConfigValue(template))
            val argsObj: Array<out String> = args
            return if (templateContent.isPresent) {
                MessageFormat.format(templateContent.get(), *argsObj)
            } else String.format(template, *argsObj)
        }
    }

    init {
        Companion.errorConfig = errorConfig
    }
}