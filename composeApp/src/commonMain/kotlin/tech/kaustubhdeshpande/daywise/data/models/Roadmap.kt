package tech.kaustubhdeshpande.daywise.data.models

import kotlinx.serialization.Serializable

/**
 * Represents a complete learning roadmap generated from a syllabus.
 * A roadmap breaks down learning content into day-by-day topics for structured progress.
 *
 * @property id Unique identifier for the roadmap
 * @property name User-given name for the roadmap (e.g., "Python Mastery 2024", "IoT Syllabus")
 * @property totalDays Total number of days in the roadmap (e.g., 30 days)
 * @property days List of daily topic schedules, one for each day
 * @property createdAt Timestamp when the roadmap was created (Unix epoch milliseconds)
 * @property sourceSyllabusName Optional name of the uploaded syllabus file
 */
@Serializable
data class Roadmap(
    val id: String,
    val name: String,
    val totalDays: Int,
    val days: List<DayTopics>,
    val createdAt: Long,  // ← CHANGED:  No default value (pass when creating)
    val sourceSyllabusName: String? = null
) {
    /**
     * Total number of topics across all days in the roadmap.
     */
    val totalTopics: Int
        get() = days.sumOf { it.topics.size }

    /**
     * Aggregate status counts for all topics in the roadmap.
     * Combines counts from all days into a single summary.
     */
    val overallStatusCounts: TopicStatusCounts
        get() {
            val allTopics = days.flatMap { it.topics }
            return TopicStatusCounts(
                remaining = allTopics.count { it.status == TopicStatus.TODO },
                completed = allTopics.count { it.status == TopicStatus.COMPLETED },
                skipped = allTopics.count { it.status == TopicStatus.SKIPPED }
            )
        }

    /**
     * Overall completion percentage for the entire roadmap.
     * Calculated based on completed topics vs total topics across all days.
     *
     * @return Percentage (0.0 to 1.0) of roadmap completion
     */
    val overallCompletionPercentage: Float
        get() {
            if (totalTopics == 0) return 0f
            val completedCount = overallStatusCounts.completed
            return completedCount.toFloat() / totalTopics.toFloat()
        }

    /**
     * Checks if the entire roadmap is completed.
     * Returns true only if all topics in all days are marked as COMPLETED.
     *
     * @return true if roadmap is fully completed, false otherwise
     */
    val isFullyCompleted: Boolean
        get() = totalTopics > 0 && days.all { it.isCompleted }

    /**
     * Checks if the user has started working on the roadmap.
     * Returns true if at least one topic has been completed or skipped.
     *
     * @return true if roadmap has been started, false if untouched
     */
    val hasStarted: Boolean
        get() {
            val counts = overallStatusCounts
            return counts.completed > 0 || counts.skipped > 0
        }

    /**
     * Gets a human-readable status label for the roadmap.
     * Used for displaying status badges in the UI.
     *
     * @return Status string:  "Completed", "Just Started", or "X% Complete"
     */
    val statusLabel: String
        get() = when {
            isFullyCompleted -> "Completed"
            !hasStarted -> "Just Started"
            else -> "${(overallCompletionPercentage * 100).toInt()}% Complete"
        }

    /**
     * Total estimated time in minutes to complete the entire roadmap.
     */
    val totalEstimatedMinutes: Int
        get() = days.sumOf { it.totalEstimatedMinutes }

    /**
     * Finds a specific day's topics by day number.
     *
     * @param dayNumber The day number to find (1-based index)
     * @return DayTopics for the specified day, or null if not found
     */
    fun getDayTopics(dayNumber: Int): DayTopics? {
        return days.find { it.dayNumber == dayNumber }
    }

    /**
     * Gets all topics across all days as a flat list.
     *
     * @return List of all topics in the roadmap
     */
    fun getAllTopics(): List<Topic> {
        return days.flatMap { it.topics }
    }

    /**
     * Filters topics by their status across all days.
     *
     * @param status The status to filter by (TODO, COMPLETED, or SKIPPED)
     * @return List of topics matching the specified status
     */
    fun getTopicsByStatus(status: TopicStatus): List<Topic> {
        return getAllTopics().filter { it.status == status }
    }
}