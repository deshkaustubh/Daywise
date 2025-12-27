package tech.kaustubhdeshpande.daywise.data.models

data class Topic(
    val id: Int,
    val dayId: Int,
    val title: String,
    val description: String,
    val isCompleted: TopicStatus
)
