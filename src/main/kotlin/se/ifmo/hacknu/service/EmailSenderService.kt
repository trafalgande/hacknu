package se.ifmo.hacknu.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.io.File
import javax.mail.internet.MimeMessage

@Service
class EmailSenderService(
    private val emailSender: JavaMailSender,
    private val template: SimpleMailMessage
) {
    fun sendEmailWithAttachment(targetEmail: String, desiredFile: File, secret: String, link: String) {
        val message: MimeMessage = emailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        val text = template.text
        helper.setTo(targetEmail)
        helper.setSubject("HackNU | Microsoft task | QR Code and helping information")
        helper.setText(text!!.format(secret, link))
        helper.addAttachment(desiredFile.name, desiredFile)
        emailSender.send(message)
    }
}