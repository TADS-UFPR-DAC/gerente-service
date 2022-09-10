package bantads.gerente.queue

import bantads.gerente.service.GerenteService
import com.rabbitmq.client.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets

@Component
class Consumer(@Value("\${RABBIT_HOST:localhost}") private val rabbitHost:String) {


    val logger: Logger = LoggerFactory.getLogger(Consumer::class.java);


    @Autowired
    lateinit var gerenteService: GerenteService

    @Bean
     fun rabbitmq() {
        val factory = ConnectionFactory()
        val connection = factory.newConnection("amqp://guest:guest@$rabbitHost:5672/")
        val channel = connection.createChannel()
        val consumerTag = "GerenteConsumer"

        channel.queueDeclare("GERENTE", false, false, false, null)

        logger.info("[$consumerTag] Waiting for messages...")
        val deliverCallback = DeliverCallback { consumerTag: String?, delivery: Delivery ->
            val message = String(delivery.body, StandardCharsets.UTF_8);
            try {
                val id = message.toLong();
                logger.info("[$consumerTag] Received message: '$message'")
                gerenteService.deleteService(id)
            } catch (e: Error) {
                logger.error("[$consumerTag] Failed to Consume Message: '$message'");
            }

        }
        val cancelCallback = CancelCallback { consumerTag: String? ->
            logger.error("[$consumerTag] was canceled")
        }

        channel.basicConsume("GERENTE", true, consumerTag, deliverCallback, cancelCallback)
    }
}