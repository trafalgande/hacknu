package se.ifmo.hacknu.service

import org.springframework.stereotype.Service
import se.ifmo.hacknu.model.PassportRequest
import se.ifmo.hacknu.model.QRMeta
import se.ifmo.hacknu.repository.QRRepository
import se.ifmo.hacknu.util.encode
import se.ifmo.hacknu.util.generateQRCode
import java.util.*

@Service
class QRService(
    private val emailSenderService: EmailSenderService,
    private val qrRepository: QRRepository,
) {

    fun generateAndSendQR(req: PassportRequest) {
        val meta = QRMeta()
        meta.name = req.name
        meta.email = req.email
        meta.hash_(req)
        qrRepository.save(meta)
        emailSenderService.sendEmailWithAttachment(
            targetEmail = req.email,
            desiredFile = generateQRCode(meta.uuid.toString(), "${req.id}"),
            secret = meta.secret,
            link = meta.uuid.toString()
        )
    }

    fun validateQR(uuid: UUID) = qrRepository.findById(uuid).isPresent

    fun QRMeta.hash_(req: PassportRequest) {
        hash = ("${req.passSeries}").encode()

    }

    fun decodeMeta(uuid: UUID) : QRMeta? {
        if (validateQR(uuid)) {
            return qrRepository.findById(uuid).get()
        }
        return null
    }

}