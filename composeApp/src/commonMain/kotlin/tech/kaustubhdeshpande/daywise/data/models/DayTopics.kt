package tech.kaustubhdeshpande.daywise.data.models

import kotlinx.serialization.Serializable

/**
 * Represents a collection of topics grouped by a specific day in the roadmap.
 * This structure organizes the learning content into daily chunks for better planning.
 *
 * @property dayNumber The day number in the roadmap (1-based index, e.g., Day 1, Day 2)
 * @property topics List of topics scheduled for this specific day
 * @property totalEstimatedMinutes Total estimated time in minutes to complete all topics for this day
 * @property date Optional specific date when this day is scheduled (ISO 8601 format:  YYYY-MM-DD)
 */
@Serializable
data class DayTopics(
    val dayNumber: Int,
    val topics: List<Topic>,
    val totalEstimatedMinutes: Int = topics.sumOf { it.estimatedMinutes },
    val date: String? = null
) {
    /**
     * Counts topics by their current status.
     * Useful for displaying progress statistics (e.g., "5 remaining, 3 done, 1 skipped").
     */
    val statusCounts: TopicStatusCounts
        get() = TopicStatusCounts(
            remaining = topics.count { it.status == TopicStatus.TODO },
            completed = topics.count { it.status == TopicStatus.COMPLETED },
            skipped = topics.count { it.status == TopicStatus.SKIPPED }
        )

    /**
     * Calculates the completion percentage for this day.
     * Based on the number of completed topics vs total topics.
     *
     * @return Percentage (0.0 to 1.0) of topics completed
     */
    val completionPercentage: Float
        get() {
            if (topics.isEmpty()) return 0f
            val completed = topics.count { it.status == TopicStatus.COMPLETED }
            return completed.toFloat() / topics.size.toFloat()
        }

    /**
     * Checks if all topics for this day are completed.
     *
     * @return true if all topics are COMPLETED, false otherwise
     */
    val isCompleted: Boolean
        get() = topics.isNotEmpty() && topics.all { it.status == TopicStatus.COMPLETED }
}

/**
 * Holds count statistics for topics grouped by status.
 * Used for displaying progress indicators like "R-C-S" (Remaining-Completed-Skipped).
 *
 * @property remaining Number of topics with TODO status
 * @property completed Number of topics with COMPLETED status
 * @property skipped Number of topics with SKIPPED status
 */
@Serializable
data class TopicStatusCounts(
    val remaining: Int,
    val completed: Int,
    val skipped: Int
) {
    /**
     * Total number of topics across all statuses.
     */
    val total: Int
        get() = remaining + completed + skipped
}