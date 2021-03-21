package se.ifmo.hacknu.api

import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import se.ifmo.hacknu.model.PassportRequest
import se.ifmo.hacknu.service.PassportRequestService
import java.net.http.HttpRequest


@RestController
@RequestMapping("/admin")
class AdminController(
    private val passportRequestService: PassportRequestService
) {


    @RequestMapping("/list")
    fun listPendingRequests(): ModelAndView {
        val model = ModelAndView()
        model.addObject("pending_requests", passportRequestService.fetchAllPendingRequests())
        model.viewName = "/table.html"
        return model
    }

    @RequestMapping("/approve/{id}")
    suspend fun approveRequest(
        @PathVariable(name = "id") id: Long
    ) : HttpStatus {
        passportRequestService.approve(id)
        return HttpStatus.OK
    }
}