package tech.kaustubhdeshpande.daywise.data.models

data class Roadmap(
    val id: String,
    val title: String,
    val totalDays: Int,
    val completedDays: Int
)