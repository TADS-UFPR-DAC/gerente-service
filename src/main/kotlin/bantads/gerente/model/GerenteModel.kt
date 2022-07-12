package bantads.gerente.model

import javax.persistence.*

@Entity
@Table
class Gerente(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    @Column(nullable = false, unique = true)
    val cpf: String,
    @Column(nullable = false)
    val nome: String,
    @Column(nullable = false, unique = true)
    val email: String
)
