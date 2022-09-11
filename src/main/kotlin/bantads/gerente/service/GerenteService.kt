package bantads.gerente.service

import bantads.conta.config.RabbitMQConfig
import bantads.conta.config.RabbitMQConfig.Companion.FILA_DELETAR_CONTA
import bantads.gerente.exception.ApiRequestException
import bantads.gerente.model.Gerente
import bantads.gerente.repository.GerenteRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
class GerenteService {


    val logger: Logger = LoggerFactory.getLogger(GerenteService::class.java);

    @Autowired
    lateinit var gerenteRepository: GerenteRepository;

    @Throws(ApiRequestException::class, IllegalArgumentException::class)
    fun insert(gerente: Gerente): Gerente {
        val exists = gerenteRepository.findByCpf(gerente.cpf);
        if (exists.isPresent) throw ApiRequestException("GERENTE JÁ EXISTE!", Throwable(), HttpStatus.CONFLICT);
        logger.info("---- SALVANDO NOVO GERENTE, CPF: ${gerente.cpf} ----");
        return gerenteRepository.save(gerente);
    }

    fun findService(cpf: String?): Any {
        cpf?.let {
            val exists = gerenteRepository.findByCpf(cpf);
            if (exists.isEmpty) throw ApiRequestException("CPF NÃO ENCONTRADO", Throwable(), HttpStatus.NOT_FOUND);
            return gerenteRepository.findByCpf(cpf);
        }
        return gerenteRepository.findAll()
    }

    fun updateService(gerente: Gerente, id: Long?): ResponseEntity<Gerente> {
        val gID = id ?: gerente.id;
        gID?.let {
            val gerenteNew = Gerente(
                gID,
                gerente.cpf,
                gerente.nome,
                gerente.email,
            );
            return ResponseEntity(gerenteRepository.save(gerenteNew), HttpStatus.OK);
        }
        return ResponseEntity(insert(gerente), HttpStatus.CREATED);
    }

    fun deleteService(id: Long): ResponseEntity<String> {
        val exists = gerenteRepository.findById(id);
        if (exists.isEmpty) throw ApiRequestException("GERENTE NÃO EXISTE", null, HttpStatus.NOT_FOUND);
        logger.info("--- DELETING USER: $id ---");
        gerenteRepository.deleteById(id);
        return ResponseEntity("Resource deleted!", HttpStatus.OK);
    }

    @RabbitListener(
        bindings = [QueueBinding(
            value = Queue(FILA_DELETAR_CONTA),
            exchange = Exchange(name = RabbitMQConfig.CONTA_EXCHANGE),
            key = arrayOf(RabbitMQConfig.CHAVE_DELETAR_CONTA)
        )]
    )
    fun deleteQueue(message: Message?, gerente: Gerente) {
        logger.info("Deletando conta do usuário de id " + gerente.id)
        gerente.id?.let { deleteService(it) }
    }


}