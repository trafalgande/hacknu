package se.ifmo.hacknu.model

import java.io.Serializable
import javax.persistence.*


@Entity
@Table(name = "records")
class CustomRecord : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0


    @Column(name = "age")
    var age: Int = 0
        private set

    @Column(name = "sex")
    var sex: Int = 0
        private set
}