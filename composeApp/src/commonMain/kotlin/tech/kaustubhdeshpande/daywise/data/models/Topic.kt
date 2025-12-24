package tech.kaustubhdeshpande.daywise.data.models

import kotlinx.serialization.Serializable

/**
 * Represents a single topic or subject within a roadmap.
 * Topics are the smallest unit of learning content that can be tracked individually.
 *
 * @property id Unique identifier for the topic
 * @property title Name/title of the topic (e.g., "Variables and Data Types")
 * @property description Optional detailed description or learning objectives for the topic
 * @property dayNumber The day number this topic is scheduled for (1-based index)
 * @property estimatedMinutes Estimated time in minutes to complete this topic
 * @property status Current completion status of the topic (TODO, COMPLETED, or SKIPPED)
 * @property resources Optional list of learning resource URLs (articles, videos, docs)
 */
@Serializable
data class Topic(
    val id: String,
    val title: String,
    val description: String? = null,
    val dayNumber: Int,
    val estimatedMinutes: Int = 30,
    val status: TopicStatus = TopicStatus.TODO,
    val resources: List<String> = emptyList()
)

/**
 * Represents the completion status of a topic.
 * Used for tracking user progress and filtering topics by status.
 */
@Serializable
enum class TopicStatus {
    /** Topic has not been started yet */
    TODO,

    /** Topic has been completed by the user */
    COMPLETED,

    /** Topic has been skipped by the user */
    SKIPPED
}