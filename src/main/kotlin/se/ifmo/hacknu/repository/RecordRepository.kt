package se.ifmo.hacknu.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import se.ifmo.hacknu.model.CustomRecord

@Repository
interface RecordRepository : CrudRepository<CustomRecord, Long>, JpaRepository<CustomRecord, Long> {
//    fun findCustomRecordByAge(age: Int) -- reference
}