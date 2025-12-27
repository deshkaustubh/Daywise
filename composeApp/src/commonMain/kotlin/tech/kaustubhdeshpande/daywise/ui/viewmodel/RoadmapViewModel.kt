package tech.kaustubhdeshpande.daywise.ui.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tech.kaustubhdeshpande.daywise.data.models.Day
import tech.kaustubhdeshpande.daywise.data.models.Roadmap
import tech.kaustubhdeshpande.daywise.data.models.Topic
import tech.kaustubhdeshpande.daywise.data.models.TopicStatus
import tech.kaustubhdeshpande.daywise.data.repository.RoadmapRepository

sealed class RoadmapUiState {
    object Loading : RoadmapUiState()
    data class Success(
        val roadmap: Roadmap,
        val days: List<Day>,
        val topics: List<Topic>
    ) : RoadmapUiState()

    data class Error(val message: String) : RoadmapUiState()
}

open class SharedViewModel {
    protected val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    fun clear() {
        viewModelScope.cancel()
    }
}


class RoadmapViewModel(private val repository: RoadmapRepository = RoadmapRepository()) :
    SharedViewModel() {

    // backing state
    private val _uiState = MutableStateFlow<RoadmapUiState>(RoadmapUiState.Loading)
    val uiState: StateFlow<RoadmapUiState> = _uiState.asStateFlow()

    // loading roadmaps and RCS (Days)
    fun loadRoadmap() {
        viewModelScope.launch {
            try {
                val roadmap = repository.getRoadmap()
                val days = repository.getDays()
                _uiState.value = RoadmapUiState.Success(roadmap, days, emptyList())
            } catch (e: Exception) {
                _uiState.value = RoadmapUiState.Error("Failed to load roadmap: ${e.message}")
            }
        }
    }

    // loading topics for a specific day
    fun loadTopics(dayId: Int) {
        viewModelScope.launch {
            try {
                val roadmap = repository.getRoadmap()
                val days = repository.getDays()
                val topics = repository.getTopicsForDay(dayId)
                _uiState.value = RoadmapUiState.Success(roadmap, days, topics)
            } catch (e: Exception) {
                _uiState.value = RoadmapUiState.Error("Failed to load topics: ${e.message}")
            }
        }
    }

    // updating the status of the topic
    fun updateTopicStatus(dayId: Int, topicId: Int, newStatus: TopicStatus) {
        val currentState = _uiState.value
        if (currentState is RoadmapUiState.Success) {
            val updatedTopics = currentState.topics.map { topic ->
                if (topic.dayId == dayId && topic.id == topicId) {
                    topic.copy(isCompleted = newStatus)
                } else {
                    topic
                }
            }
            val updatedDays = currentState.days.map { day ->
                if (day.dayNumber == dayId) {
                    val completedCount =
                        updatedTopics.count { it.dayId == dayId && it.isCompleted == TopicStatus.COMPLETED }
                    val skippedCount =
                        updatedTopics.count { it.dayId == dayId && it.isCompleted == TopicStatus.SKIPPED }
                    val remainingCount = day.totalTopics - (completedCount + skippedCount)

                    day.copy(
                        completedCount = completedCount,
                        skippedCount = skippedCount,
                        remainingCount = remainingCount
                    )
                } else day
            }
            _uiState.value = RoadmapUiState.Success(
                roadmap = currentState.roadmap,
                days = currentState.days,
                topics = currentState.topics,
            )
        }
    }
}