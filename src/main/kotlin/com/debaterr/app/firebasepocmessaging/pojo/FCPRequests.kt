package com.debaterr.app.firebasepocmessaging.pojo

class FCPRequests {
}

data class NotificationRequest (
    val title: String,
    val body: String,
    val topics: List<String>? = null,
    val token: String? = null,
    val data: Map<String, String> = emptyMap<String, String>()
)

data class SubscriptionRequest(
    val topics: List<String> = emptyList<String>(),
    val token: String
)
data class UnSubscriptionRequest(
    val topics: List<String> = emptyList<String>(),
    val token: String
)

data class NotificationResponse(
    val success: Boolean,
    val message: String? = null,
    val error: String? = null,
    val responseMessage: String? = null
)