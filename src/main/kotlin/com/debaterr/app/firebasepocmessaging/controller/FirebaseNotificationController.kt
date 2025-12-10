package com.debaterr.app.firebasepocmessaging.controller

import com.debaterr.app.firebasepocmessaging.pojo.NotificationRequest
import com.debaterr.app.firebasepocmessaging.pojo.NotificationResponse
import com.debaterr.app.firebasepocmessaging.services.FirebaseMessagingService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("api/v1/notification")
class FirebaseNotificationController(
    private val notificationService: FirebaseMessagingService
) {
    private val log = LoggerFactory.getLogger(FirebaseNotificationController::class.java)

    @PostMapping("/send/topics")
    fun sendToTopics(@RequestBody request: NotificationRequest): ResponseEntity<NotificationResponse> {
        return ResponseEntity.ok(notificationService.sendToTopics(request));
    }

    @PostMapping("/send/token")
    open fun sendToToken(@RequestBody request: NotificationRequest): ResponseEntity<NotificationResponse> {
        log.info("Sending notification for the token {}", request.token)
        return ResponseEntity.ok(notificationService.sendToToken(request))
    }

    @GetMapping("/topics")
    open fun getAllTopics() {}

    @PostMapping("subscribe")
    open fun subscribeToTopic() {}

    @PostMapping("unsubscribe")
    open fun unsubscribeToTopic() {}
}