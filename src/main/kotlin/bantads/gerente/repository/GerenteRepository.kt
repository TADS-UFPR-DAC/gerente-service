package bantads.gerente.repository


import bantads.gerente.model.Gerente
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface GerenteRepository : JpaRepository<Gerente, Long> {
    fun findByCpf(cpf: String): Optional<Gerente>
}

