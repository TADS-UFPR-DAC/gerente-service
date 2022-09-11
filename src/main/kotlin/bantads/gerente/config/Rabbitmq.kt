package bantads.conta.config

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {
    @Bean
    fun contaExchange(): DirectExchange {
        return DirectExchange(GERENTE_EXCHANGE)
    }

    @Bean
    fun mensagemExchange(): DirectExchange {
        return DirectExchange(MENSAGEM_EXCHANGE)
    }


    @Bean
    fun deletarContaQueue(): Queue {
        return QueueBuilder.durable(FILA_DELETAR_GERENTE).build()
    }

    @Bean
    fun mensagemQueue(): Queue {
        return QueueBuilder.durable(FILA_MENSAGEM).build()
    }

    @Bean
    fun deletarContaBinding(): Binding {
        return BindingBuilder.bind(deletarContaQueue()).to(contaExchange()).with(CHAVE_DELETAR_GERENTE)
    }

    @Bean
    fun mensagemBinding(): Binding {
        return BindingBuilder.bind(mensagemQueue()).to(mensagemExchange()).with(CHAVE_MENSAGEM)
    }

    companion object {
        const val GERENTE_EXCHANGE = "gerente"
        const val MENSAGEM_EXCHANGE = "mensagem"
        const val FILA_DELETAR_GERENTE = "DeletarGerenteQueue"
        const val FILA_MENSAGEM = "gerenteQueue"
        const val CHAVE_DELETAR_GERENTE = "deletarGerente"
        const val CHAVE_MENSAGEM = "mensagem"
    }
}