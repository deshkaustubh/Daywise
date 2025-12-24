package tech.kaustubhdeshpande.daywise. data.repository

import kotlinx.coroutines.flow. MutableStateFlow
import kotlinx. coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import tech.kaustubhdeshpande.daywise.data.agent.RoadmapAgent
import tech.kaustubhdeshpande.daywise.data.models.Roadmap
import tech. kaustubhdeshpande. daywise.data.models.TopicStatus

/**
 * Repository for managing roadmap data and coordinating between UI and AI agent.
 * Handles roadmap generation, storage, updates, and provides reactive state management.
 *
 * This repository demonstrates:
 * - Clean architecture pattern (separation of concerns)
 * - Reactive state management with Kotlin Flow
 * - Error handling for AI operations
 * - In-memory storage (can be extended to persistent storage)
 * - 100% shared business logic across all platforms
 *
 * @property apiKey Google AI API key for Gemini model access
 */
class RoadmapRepository(
    private val apiKey: String
) {
    /**
     * AI agent for generating roadmaps from syllabus content.
     */
    private val agent = RoadmapAgent(apiKey)

    /**
     * In-memory storage for created roadmaps.
     * Maps roadmap ID to Roadmap object.
     */
    private val roadmapsStorage = mutableMapOf<String, Roadmap>()

    /**
     * Mutable state flow for roadmap generation status.
     * Exposed as immutable StateFlow to UI layer.
     */
    private val _generationState = MutableStateFlow<RoadmapGenerationState>(
        RoadmapGenerationState.Idle
    )

    /**
     * Observable state for roadmap generation.
     * UI can collect this flow to react to generation progress.
     */
    val generationState: StateFlow<RoadmapGenerationState> = _generationState.asStateFlow()

    /**
     * Mutable state flow for list of all roadmaps.
     * Exposed as immutable StateFlow to UI layer.
     */
    private val _roadmaps = MutableStateFlow<List<Roadmap>>(emptyList())

    /**
     * Observable list of all roadmaps.
     * UI can collect this flow to display roadmap list.
     */
    val roadmaps: StateFlow<List<Roadmap>> = _roadmaps.asStateFlow()

    /**
     * Generates a new roadmap from syllabus content using AI agent.
     * Updates generation state throughout the process.
     *
     * @param syllabusContent Raw text content of the syllabus
     * @param roadmapName User-provided name for the roadmap
     * @param targetDays Number of days to distribute content across
     * @param sourceSyllabusName Optional filename of uploaded syllabus
     * @return Result containing generated Roadmap or error
     */
    suspend fun generateRoadmap(
        syllabusContent: String,
        roadmapName: String,
        targetDays: Int,
        sourceSyllabusName: String? = null
    ): Result<Roadmap> {
        return try {
            // Update state to generating
            _generationState.value = RoadmapGenerationState.Generating

            // Call AI agent to generate roadmap
            val generatedRoadmap = agent.generateRoadmap(
                syllabusContent = syllabusContent,
                roadmapName = roadmapName,
                targetDays = targetDays,
                sourceSyllabusName = sourceSyllabusName
            )

            // Save to storage (agent already set createdAt = 0L, which is fine for now)
            roadmapsStorage[generatedRoadmap.id] = generatedRoadmap
            updateRoadmapsList()

            // Update state to success
            _generationState.value = RoadmapGenerationState.Success(generatedRoadmap)

            Result.success(generatedRoadmap)

        } catch (e: Exception) {
            // Update state to error
            val errorMessage = e.message ?: "Unknown error occurred during roadmap generation"
            _generationState.value = RoadmapGenerationState.Error(errorMessage)

            Result.failure(e)
        }
    }

    /**
     * Retrieves a roadmap by its ID.
     *
     * @param roadmapId Unique identifier of the roadmap
     * @return Roadmap if found, null otherwise
     */
    fun getRoadmapById(roadmapId: String): Roadmap? {
        return roadmapsStorage[roadmapId]
    }

    /**
     * Retrieves all roadmaps sorted by creation date (newest first).
     * Note: Since createdAt is 0L for all, order may not be meaningful yet.
     * Can be enhanced later with proper timestamp management.
     *
     * @return List of all roadmaps
     */
    fun getAllRoadmaps(): List<Roadmap> {
        return roadmapsStorage.values
            .sortedByDescending { it.createdAt }
            .toList()
    }

    /**
     * Updates a topic's status (TODO, COMPLETED, or SKIPPED).
     * Replaces the entire roadmap with updated version.
     *
     * @param roadmapId ID of the roadmap containing the topic
     * @param topicId ID of the topic to update
     * @param newStatus New status for the topic
     * @return Updated Roadmap if successful, null if roadmap or topic not found
     */
    fun updateTopicStatus(
        roadmapId: String,
        topicId: String,
        newStatus: TopicStatus
    ): Roadmap? {
        val roadmap = roadmapsStorage[roadmapId] ?: return null

        // Find and update the topic
        val updatedDays = roadmap.days.map { dayTopics ->
            val updatedTopics = dayTopics.topics.map { topic ->
                if (topic.id == topicId) {
                    topic.copy(status = newStatus)
                } else {
                    topic
                }
            }
            dayTopics.copy(topics = updatedTopics)
        }

        val updatedRoadmap = roadmap.copy(days = updatedDays)

        // Save updated roadmap
        roadmapsStorage[roadmapId] = updatedRoadmap
        updateRoadmapsList()

        return updatedRoadmap
    }

    /**
     * Deletes a roadmap by its ID.
     *
     * @param roadmapId ID of the roadmap to delete
     * @return true if deleted successfully, false if roadmap not found
     */
    fun deleteRoadmap(roadmapId: String): Boolean {
        val removed = roadmapsStorage.remove(roadmapId) != null
        if (removed) {
            updateRoadmapsList()
        }
        return removed
    }

    /**
     * Resets the generation state to Idle.
     * Useful for clearing error messages or success states.
     */
    fun resetGenerationState() {
        _generationState.value = RoadmapGenerationState.Idle
    }

    /**
     * Updates the roadmaps list state flow.
     * Called after any modification to storage.
     */
    private fun updateRoadmapsList() {
        _roadmaps.value = getAllRoadmaps()
    }
}

/**
 * Sealed class representing the state of roadmap generation process.
 * Used for reactive UI updates during AI generation.
 */
sealed class RoadmapGenerationState {
    /**
     * Initial state, no generation in progress.
     */
    data object Idle : RoadmapGenerationState()

    /**
     * Generation is currently in progress.
     */
    data object Generating : RoadmapGenerationState()

    /**
     * Generation completed successfully.
     *
     * @property roadmap The generated roadmap
     */
    data class Success(val roadmap: Roadmap) : RoadmapGenerationState()

    /**
     * Generation failed with an error.
     *
     * @property message Error message describing the failure
     */
    data class Error(val message: String) : RoadmapGenerationState()
}