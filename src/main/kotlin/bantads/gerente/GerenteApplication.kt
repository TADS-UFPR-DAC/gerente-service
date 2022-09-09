package bantads.gerente

import bantads.gerente.queue.Consumer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class GerenteApplication

fun main(args: Array<String>) {
	runApplication<GerenteApplication>(*args)
}
