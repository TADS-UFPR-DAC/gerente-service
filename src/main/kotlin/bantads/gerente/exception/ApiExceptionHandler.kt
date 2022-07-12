package bantads.gerente.exception


import org.postgresql.util.PSQLException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


import java.time.ZonedDateTime

@ControllerAdvice
class ApiExceptionHandler() {


    @ExceptionHandler(ApiRequestException::class)
    fun handleApiRequestException(e: ApiRequestException): ResponseEntity<ApiException> {
        val apiException = ApiException(
            message = e.message,
            error = e.httpStatus,
            timestamp = ZonedDateTime.now()
        )
        return ResponseEntity(apiException, e.httpStatus)
    }

    @ExceptionHandler(Exception::class)
    fun defaultApiExceptionHandler(e: Exception): ResponseEntity<ApiException> {
        val apiException = ApiException(
            message = e.message,
            timestamp = ZonedDateTime.now()
        )
        return ResponseEntity(apiException, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class, IllegalArgumentException::class)
    fun handleWrongPayload(e: Exception): ResponseEntity<ApiException> {
        val statusCode = HttpStatus.BAD_REQUEST
        val apiException = ApiException(
            message = "Failed to parse payload as JSON, verify your parameters",
            error = statusCode,
            timestamp = ZonedDateTime.now()
        )
        return ResponseEntity(apiException, statusCode)
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun uniqueValue(e: DataIntegrityViolationException): ResponseEntity<ApiException> {
        val statusCode = HttpStatus.CONFLICT
        val apiException = ApiException(
            message = "Duplicate Key!",
            error = statusCode,
            timestamp = ZonedDateTime.now()
        )
        return ResponseEntity(apiException, statusCode)
    }
    @ExceptionHandler(PSQLException::class)
    fun postgreException(e: PSQLException): ResponseEntity<ApiException> {
        val statusCode = HttpStatus.CONFLICT
        val apiException = ApiException(
            message = e.message,
            error = statusCode,
            timestamp = ZonedDateTime.now()
        )
        return ResponseEntity(apiException, statusCode)
    }


}

