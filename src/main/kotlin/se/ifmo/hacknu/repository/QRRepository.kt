package se.ifmo.hacknu.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import se.ifmo.hacknu.model.QRMeta
import java.util.*

@Repository
interface QRRepository : CrudRepository<QRMeta, UUID>, JpaRepository<QRMeta, UUID> {
}