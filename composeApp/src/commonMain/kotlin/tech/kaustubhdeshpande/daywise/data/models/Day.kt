package tech.kaustubhdeshpande.daywise.data.models

data class Day(
    val dayNumber: Int,
    val totalTopics: Int,
    var remainingCount: Int,
    var completedCount: Int,
    var skippedCount: Int
)
