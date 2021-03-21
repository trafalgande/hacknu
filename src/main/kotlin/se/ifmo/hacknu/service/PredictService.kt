package se.ifmo.hacknu.service

import org.springframework.stereotype.Service
import se.ifmo.hacknu.util.exec
import java.io.File
import java.nio.file.Files


@Service
class PredictService {

    fun resolve(parameters: String) : File {
        val f = File("python/data.txt")
        Files.write(f.toPath(), parameters.toByteArray()).fileName
        return File("python script.py data.txt".exec().split(" ")[3].trim())
    }
}