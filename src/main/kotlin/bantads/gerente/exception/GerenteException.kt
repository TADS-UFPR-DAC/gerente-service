package bantads.gerente.exception

class GerenteException(
    override val message: String?,
    override val cause: Throwable?,
) : RuntimeException()