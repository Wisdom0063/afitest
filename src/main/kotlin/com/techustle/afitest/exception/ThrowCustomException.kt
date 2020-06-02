package  com.techustle.afitest.exception

object ThrowCustomException {
    /**
     * Returns a new RuntimeException with error messages like user with email example@gmail.com not found. the error template for this is user.not.found
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    fun exception(entityType: EntityType, exceptionType: ExceptionType, vararg args: String?): RuntimeException {
        return AfitestException.throwException(entityType, exceptionType, *args)
    }

    /**
     * Returns a new RuntimeException with error messages like user not found. the error template for this example is not.found
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    fun exception(entityType: EntityType, exceptionType: ExceptionType): RuntimeException {
        return AfitestException.throwException(entityType, exceptionType)
    }

    /**
     * Returns a new RuntimeException
     *
     * @param message
     * @return
     */
    fun exception(exceptionType: ExceptionType, messageTemplate: String, vararg args: String?): RuntimeException {
        return AfitestException.throwException(exceptionType, messageTemplate, *args)
    }
}