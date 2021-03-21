package se.ifmo.hacknu.repository

import org.springframework.data.repository.CrudRepository
import se.ifmo.hacknu.model.ExpiredPassport

interface ExpiredPassportRepository : CrudRepository<ExpiredPassport, Long> {
    fun existsByExpPassNumAndExpPassSer(num: String, serial: String) : Boolean
}