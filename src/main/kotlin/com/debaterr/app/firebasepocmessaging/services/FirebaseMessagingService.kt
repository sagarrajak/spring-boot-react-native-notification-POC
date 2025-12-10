package com.debaterr.app.firebasepocmessaging.services

import com.debaterr.app.firebasepocmessaging.pojo.NotificationRequest
import com.debaterr.app.firebasepocmessaging.pojo.NotificationResponse
import org.springframework.stereotype.Service
import org.slf4j.LoggerFactory
import com.google.firebase.messaging.*
import java.util.concurrent.ConcurrentHashMap

@Service
class FirebaseMessagingService(
    private val firebaseMessaging: FirebaseMessaging
) {
    private val logger = LoggerFactory.getLogger(FirebaseMessagingService::class.java)
    private val topics = ConcurrentHashMap.newKeySet<String>()

    fun sendToToken(request: NotificationRequest): NotificationResponse {
        return try  {
            val message = buildMessageHelper(request).setToken(request.token).build();
            logger.info("message body {}", message.toString());
            val response = firebaseMessaging.send(message);
            logger.info("successfully send message to the token {}", request.token);
           NotificationResponse(success = true, message = "message send successfully!", responseMessage = response);
        } catch (e: Exception) {
             logger.error("Unable to send notification, err {}", e.message)
             NotificationResponse(success = false, message = "Failed to send message");
        };
    }

    open fun sendToTopics(request: NotificationRequest): NotificationResponse {
        return try {
            val topics = request.topics;
            logger.info("sending notification to topics {}", topics!!.joinToString(","));
            val message =  buildMessageHelper(request).setCondition(buildCondition(topics)).build();
            logger.info("sending following message {} ", message);
            val response = firebaseMessaging.send(message)
            NotificationResponse(success = true, message = "message send successfully!", responseMessage = response);
        } catch (err: Exception) {
            logger.error("Unable to send notification to the topic, err happen {}", err.message)
            NotificationResponse(success = false, message = "Failed to send message");
        }
    }

    open fun subscribeToTopic(token: String, topics: List<String>): Boolean {
        return try {
            topics.forEach { topic ->
                firebaseMessaging.subscribeToTopic(listOf(token), topic)
                logger.info("Token {} is Subscribed to the topic {} ", token,  topic)
            }
            true
        } catch (err: Exception) {
            logger.error("Unabled to subscribed to the topic, error {} happen", err.message);
           false
        }
    }

    open fun unsubscribeFromTopic(token: String, topics: List<String>): Boolean {
        return try {
            topics.forEach { topic ->
                firebaseMessaging.unsubscribeFromTopic(listOf(token), topic);
                logger.info("Token {} is unsubscribed to the topic {}" , token, topic);
            }
            true
        } catch (e: Exception) {
            logger.error("Unbale to unsubscribed to the topic, error  {}", e.message)
            false
        }
    }

    open fun unsubscribeFromTopic(tokens: List<String>, topics: List<String>): Boolean {
       return  try {
            topics.forEach { topic ->
                firebaseMessaging.unsubscribeFromTopic(tokens, topic);
                logger.info("Token {} is unsubscribed to the topics {}" , tokens.joinToString(" ").toString(), topic);
            }
            true
        } catch (e: Exception) {
            logger.error("Unbale to unsubscribed to the topic, error  {}", e.message)
            false
        }
    }

    private fun buildMessageHelper(request: NotificationRequest): Message.Builder {
        val notification = Notification.builder()
            .setBody(request.body)
            .setTitle(request.title)
            .build();
//
//        val data = mutableMapOf<String, String>()
//        data.putAll(request.data);
//
//        // Add title and body to data if not already present
//        if (!data.containsKey("title")) {
//            data["title"] = request.title
//        }
//        if (!data.containsKey("body")) {
//            data["body"] = request.body
//        }

        return Message.builder()
            .setNotification(notification)
    }

    private fun buildCondition(topics: List<String>): String {
        return topics.joinToString(" || ") { "'$it' in topics" }
    }
}