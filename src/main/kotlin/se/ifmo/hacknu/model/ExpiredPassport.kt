package se.ifmo.hacknu.model

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "expired_passports")
class ExpiredPassport(@Column(name = "exp_pass_serial") var expPassSer: String = "",
                      @Column(name = "exp_pass_number") var expPassNum: String = "") : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0
}