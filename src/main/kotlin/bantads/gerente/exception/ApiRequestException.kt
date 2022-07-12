package bantads.gerente.exception

import org.springframework.http.HttpStatus

class ApiRequestException(
    override val message: String?,
    override val cause: Throwable?,
    val httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
) : RuntimeException(message, cause) {
}