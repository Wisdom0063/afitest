

package  com.techustle.afitest.controller.v1.schedule.payload
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator


class LongValidator : Validator {
    override fun validate(target: Any, errors: Errors) {
        errors.rejectValue("age", "field.required");    }

    /**
     * This Validator validates *just* Person instances
     */
    override fun supports(clazz: Class<*>): Boolean {
        return Long::class.java == clazz
    }

}