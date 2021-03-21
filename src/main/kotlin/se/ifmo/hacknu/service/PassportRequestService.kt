package se.ifmo.hacknu.service

import lombok.extern.log4j.Log4j2
import org.springframework.stereotype.Service
import se.ifmo.hacknu.model.PassportRequest
import se.ifmo.hacknu.repository.ExpiredPassportRepository
import se.ifmo.hacknu.repository.PassportRequestRepository
import se.ifmo.hacknu.util.loggerFor
import se.ifmo.hacknu.util.validatePassportData
import java.io.File

@Service
class PassportRequestService(
    private val passportRequestRepository: PassportRequestRepository,
    private val qrService: QRService
) {
    private val log = loggerFor(javaClass)

    fun createPassportRequest(email: String, num: Long, name: String, f: File) {
        val req = PassportRequest()
        req.email = email
        req.passSeries = num.toString().substring(4).toLong()
        req.name = name
        req.doc = f
        passportRequestRepository.save(req)
        log.info("Request #${req.id} was saved")
    }

    fun fetchAllPendingRequests(): List<PassportRequest> = passportRequestRepository.findAllByPendingIsTrue()

    fun approve(id: Long) {
        val req = passportRequestRepository.findById(id).get()
        passportRequestRepository.delete(req)
        if (validatePassportData(
                req.passSeries.toString().substring(0, 3).toLong(),
                req.passSeries.toString().substring(3, req.passSeries.toString().length).toLong()
            )
        ) {
            qrService.generateAndSendQR(req)
        }
    }


}