package se.ifmo.hacknu.api

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.ModelAndView
import se.ifmo.hacknu.model.DTO
import se.ifmo.hacknu.service.PassportRequestService
import se.ifmo.hacknu.service.PredictService
import se.ifmo.hacknu.service.QRService
import java.io.File
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@RestController
@RequestMapping("/api")
class ApiController(
    private val passportRequestService: PassportRequestService,
    private val qrService: QRService,
    private val predictService: PredictService
) {
    /*@GetMapping("/predict")
    fun predict(
        @RequestParam(name = "sex", required = true) sex: String,
        @RequestParam(name = "number_of_days_from_onset", required = false) nofdo: String = "",
        @RequestParam(name = "age", required = false) age: String = "",
        @RequestParam(name = "temperature", required = false) temperature: String = "",
        @RequestParam(name = "weakness", required = false) weakness: String = "",
        @RequestParam(name = "loss_of_smell_taste", required = false) lost: String = "",
        @RequestParam(name = "coronary_heart_disease", required = false) chd: String = "",
        @RequestParam(name = "aks", required = false) aks: String = "",
        @RequestParam(name = "cerebovascular_disease", required = false) cd: String = "",
        @RequestParam(name = "num_of_days_of_IMV", required = false) nodi: String = "",
        @RequestParam(name = "pcr", required = false) pcr: String = "",
        @RequestParam(name = "white_blood_cell_count", required = false) wbcc: String = "",
        @RequestParam(name = "neutrophil_count", required = false) nc: String = "",
        @RequestParam(name = "l1_count", required = false) l1c: String = "",
        @RequestParam(name = "lactate_dehydrogenase_count", required = false) ldc: String = "",
        @RequestParam(name = "c-reactive_protein_count", required = false) crpc: String = "",
        @RequestParam(name = "ferrytin_count", required = false) fc: String = "",
        @RequestParam(name = "d-dymer_count", required = false) ddc: String = "",
        @RequestParam(name = "interleukin-6_count", required = false) ic: String = "",
        @RequestParam(name = "creatinine", required = false) creatinine: String = "",
        @RequestParam(name = "glucose", required = false) glucose: String = "",
        @RequestParam(name = "procalcitonin_count", required = false) pc: String = "",
        @RequestParam(name = "NEWS_index", required = false) newsIndex: String = "",
        @RequestParam(name = "degree_of_raspiratory_failure", required = false) dorf: String = ""
    ): ModelAndView {
        val model = ModelAndView()
        model.viewName = "/index.html"
        return model
    }*/


    @GetMapping("/predict")
    fun predict(@RequestParam params: MultiValueMap<String, String>): ModelAndView {
        val model = ModelAndView()
        val str = StringBuffer()
        for ((_, v) in params) {
            str.append(v).append(",")
        }
        val final = str.toString().replace("[", "").replace("]", "")

        val lines =
            Files.readAllLines(
                Path.of(
                    "E:/CODE/hacknu/python/${
                        predictService.resolve(
                            final.substring(
                                0,
                                final.length - 1
                            )
                        )
                    }"
                )
            )
        val doubles = mutableListOf<Double>()
        for (l in lines[0].replace("[", "").replace("]", "").split(","))
            doubles.add(l.toDouble())

        doubles.forEach { println(it) }
        println(doubles.size)
        model.addObject("result", DTO(doubles[0], doubles[1], doubles[2], doubles[3], doubles[4]))
        model.viewName = "/index.html"
        return model
    }


    @GetMapping("/passport_request")
    fun passportRequest(
        @RequestParam(name = "email", required = true) email: String,
        @RequestParam(name = "pass_num", required = true) pass_num: Long,
        @RequestParam(name = "name", required = true) name: String,
        @RequestParam(name = "file_", required = false) file: MultipartFile?
    ) {
//        Files.copy(file.inputStream, Paths.get("python/" + file.originalFilename))
        passportRequestService.createPassportRequest(email, pass_num, name, File("stock/card.png"))
    }

    @GetMapping("/view/{uuid}")
    fun passportView(
        @PathVariable(name = "uuid") uuid: UUID
    ): ModelAndView {
        val model = ModelAndView()
        model.addObject("meta", qrService.decodeMeta(uuid))
        model.viewName = "/card.html"
        return model
    }

}