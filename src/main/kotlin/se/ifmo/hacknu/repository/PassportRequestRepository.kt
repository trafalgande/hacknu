package se.ifmo.hacknu.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import se.ifmo.hacknu.model.PassportRequest

interface PassportRequestRepository : CrudRepository<PassportRequest, Long>, JpaRepository<PassportRequest, Long> {
    fun findAllByPendingIsTrue(): List<PassportRequest>
}