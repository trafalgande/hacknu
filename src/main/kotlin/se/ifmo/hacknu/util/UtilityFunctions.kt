package se.ifmo.hacknu.util

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import org.apache.commons.exec.CommandLine
import org.apache.commons.exec.DefaultExecutor
import org.apache.commons.exec.PumpStreamHandler
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

private const val URL = "http://localhost:8080/api/view/%s"

fun generateQRCode(text: String, filename: String): File {
    val bitMatrix = QRCodeWriter()
        .encode(URL.format(text), BarcodeFormat.QR_CODE, 250, 250)
    val tmp = File("E:/CODE/hacknu/src/main/resources/stock/$filename.png")

    MatrixToImageWriter.writeToPath(
        bitMatrix,
        "PNG",
        tmp.toPath()
    )
    return tmp
}

fun generateString(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

fun <T> loggerFor(clazz: Class<T>) = LoggerFactory.getLogger(clazz)

fun validatePassportData(series: Long, num: Long): Boolean {
    var valid = true
    csvReader().open("E:/CODE/hacknu/src/main/resources/list_of_expired_passports.csv") {
        readAllAsSequence().forEach loop@{ row ->
            run {
                if (row[0] == series.toString() && row[1] == num.toString()) {
                    valid = false
                    return@loop
                }
            }
        }
    }
    return valid
}

fun String.encode(): String = Base64.getEncoder().encodeToString(this.toByteArray())

fun String.decode(): String = String(Base64.getDecoder().decode(this))

fun String.exec(): String {
    val cmdLine = CommandLine.parse(this)
    val stdout = ByteArrayOutputStream()
    val stderr = ByteArrayOutputStream()
    val streamHandler = PumpStreamHandler(stdout, stderr)
    val executor = DefaultExecutor()
    executor.workingDirectory = File("python/")
    executor.streamHandler = streamHandler
    executor.execute(cmdLine)
    return stdout.toString()
}