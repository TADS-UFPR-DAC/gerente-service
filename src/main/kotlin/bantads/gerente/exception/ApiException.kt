package bantads.gerente.exception

import org.springframework.http.HttpStatus
import java.time.ZonedDateTime

data class ApiException(
    val message: String?,
    val error: HttpStatus? = HttpStatus.INTERNAL_SERVER_ERROR,
    val timestamp: ZonedDateTime
) {
}