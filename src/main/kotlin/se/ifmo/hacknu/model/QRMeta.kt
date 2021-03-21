package se.ifmo.hacknu.model

import com.google.common.hash.Hashing
import org.springframework.util.Base64Utils.encodeToString
import se.ifmo.hacknu.util.generateString
import java.io.Serializable
import java.nio.charset.StandardCharsets
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "qr_meta")
class QRMeta : Serializable {
    @Id
    @Column(name = "uuid", unique = true)
    var uuid: UUID = UUID.randomUUID()

    @Column(name = "name")
    var name: String = ""

    @Column(name = "email")
    var email: String = ""

    @Column(name = "hash", unique = true)
    var hash: String = ""

    @Column(name = "secret", unique = true)
    var secret: String = generateString(10)
}

