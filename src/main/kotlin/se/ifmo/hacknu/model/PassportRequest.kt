package se.ifmo.hacknu.model

import java.io.File
import java.io.Serializable
import javax.persistence.*


@Entity
@Table(name = "pass_request")
class PassportRequest : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0

    @Column(name = "pending")
    var pending: Boolean = true

    @Column(name = "name")
    var name: String = ""

    @Column(name = "doc")
    var doc: File? = null

    @Column(name = "email", unique = true)
    var email: String = "<blank>"

    @Column(name = "pass_series")
    var passSeries: Long = 0
}