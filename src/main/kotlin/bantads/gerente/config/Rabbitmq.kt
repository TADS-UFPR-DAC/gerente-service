package bantads.conta.config

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {
    @Bean
    fun contaExchange(): DirectExchange {
        return DirectExchange(CONTA_EXCHANGE)
    }

    @Bean
    fun mensagemExchange(): DirectExchange {
        return DirectExchange(MENSAGEM_EXCHANGE)
    }


    @Bean
    fun deletarContaQueue(): Queue {
        return QueueBuilder.durable(FILA_DELETAR_CONTA).build()
    }

    @Bean
    fun mensagemQueue(): Queue {
        return QueueBuilder.durable(FILA_MENSAGEM).build()
    }

    @Bean
    fun deletarContaBinding(): Binding {
        return BindingBuilder.bind(deletarContaQueue()).to(contaExchange()).with(CHAVE_DELETAR_CONTA)
    }

    @Bean
    fun mensagemBinding(): Binding {
        return BindingBuilder.bind(mensagemQueue()).to(mensagemExchange()).with(CHAVE_MENSAGEM)
    }

    companion object {
        const val CONTA_EXCHANGE = "conta"
        const val MENSAGEM_EXCHANGE = "mensagem"
        const val FILA_DELETAR_CONTA = "DeletarContaQueue"
        const val FILA_MENSAGEM = "MensagemQueue"
        const val CHAVE_DELETAR_CONTA = "deletarConta"
        const val CHAVE_MENSAGEM = "mensagem"
    }
}