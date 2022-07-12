package bantads.gerente.controller


import bantads.gerente.exception.ApiRequestException
import bantads.gerente.model.Gerente
import bantads.gerente.repository.GerenteRepository
import bantads.gerente.service.GerenteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.Optional

@RestController
class GerenteController {


    @Autowired
    lateinit var gerenteRepository: GerenteRepository;

    @Autowired
    lateinit var gerenteService: GerenteService;

    @PostMapping("/", produces = ["application/json"])
    fun save(@RequestBody gerente: Gerente): ResponseEntity<Gerente> {
        val saved = gerenteService.insert(gerente)
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }

    @GetMapping("/", produces = ["application/json"])
    fun getAll(@RequestParam cpf: String?): ResponseEntity<Any> {
        val res = gerenteService.findService(cpf)
        return ResponseEntity.ok().body(res)
    }

    @GetMapping("/{id}", produces = ["application/json"])
    fun getByID(@PathVariable id: Long): ResponseEntity<Optional<Gerente>> {
        val gerente = gerenteRepository.findById(id)
        if (gerente.isEmpty) throw ApiRequestException("ID NÃO EXISTE", null, httpStatus = HttpStatus.NOT_FOUND)
        return ResponseEntity.ok().body(gerente)
    }

    @PutMapping(value = ["/", "/{id}"], produces = ["application/json"])
    fun update(@RequestBody gerente: Gerente, @PathVariable id: Long?): ResponseEntity<Gerente> {
        return gerenteService.updateService(gerente, id)
    }


    @DeleteMapping("/{id}", produces = ["application/json"])
    fun delete(@PathVariable id: Long): ResponseEntity<String> {
        return gerenteService.deleteService(id)
    }


}


