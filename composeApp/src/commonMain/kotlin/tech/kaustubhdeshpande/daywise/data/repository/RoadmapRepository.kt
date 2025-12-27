package tech.kaustubhdeshpande.daywise.data.repository

import tech.kaustubhdeshpande.daywise.data.models.Day
import tech.kaustubhdeshpande.daywise.data.models.Roadmap
import tech.kaustubhdeshpande.daywise.data.models.Topic
import tech.kaustubhdeshpande.daywise.data.models.TopicStatus

class RoadmapRepository {

    // Hardcoded roadmap metadata
    fun getRoadmap(): Roadmap {
        return Roadmap(
            id = "kotlin-101",
            title = "5-Day Kotlin Learning Plan",
            totalDays = 5,
            completedDays = 0
        )
    }

    // Hardcoded days
    fun getDays(): List<Day> {
        return listOf(
            Day(
                dayNumber = 1,
                totalTopics = 3,
                remainingCount = 3,
                completedCount = 0,
                skippedCount = 0
            ),
            Day(
                dayNumber = 2,
                totalTopics = 3,
                remainingCount = 3,
                completedCount = 0,
                skippedCount = 0
            ),
            Day(
                dayNumber = 3,
                totalTopics = 3,
                remainingCount = 3,
                completedCount = 0,
                skippedCount = 0
            ),
            Day(
                dayNumber = 4,
                totalTopics = 3,
                remainingCount = 3,
                completedCount = 0,
                skippedCount = 0
            ),
            Day(
                dayNumber = 5,
                totalTopics = 3,
                remainingCount = 3,
                completedCount = 0,
                skippedCount = 0
            )
        )
    }

    // Hardcoded topics per day
    fun getTopicsForDay(dayId: Int): List<Topic> {
        return when (dayId) {
            1 -> listOf(
                Topic(
                    id = 1,
                    dayId = 1,
                    title = "Setup Kotlin environment",
                    description = "Install IDE and configure Kotlin compiler",
                    isCompleted = TopicStatus.TODO
                ),
                Topic(
                    id = 2,
                    dayId = 1,
                    title = "val vs var basics",
                    description = "Understand immutability vs mutability",
                    isCompleted = TopicStatus.TODO
                ),
                Topic(
                    id = 3,
                    dayId = 1,
                    title = "Data types & type inference",
                    description = "Learn Int, String, Boolean, and type inference",
                    isCompleted = TopicStatus.TODO
                )
            )

            2 -> listOf(
                Topic(
                    id = 4,
                    dayId = 2,
                    title = "Conditionals",
                    description = "Practice if/else and when expressions",
                    isCompleted = TopicStatus.TODO
                ),
                Topic(
                    id = 5,
                    dayId = 2,
                    title = "Loops",
                    description = "Use for, while, and ranges",
                    isCompleted = TopicStatus.TODO
                ),
                Topic(
                    id = 6,
                    dayId = 2,
                    title = "Collections basics",
                    description = "Intro to List, Set, Map",
                    isCompleted = TopicStatus.TODO
                )
            )

            3 -> listOf(
                Topic(
                    id = 7,
                    dayId = 3,
                    title = "Functions",
                    description = "Define functions and pass parameters",
                    isCompleted = TopicStatus.TODO
                ),
                Topic(
                    id = 8,
                    dayId = 3,
                    title = "Classes & objects",
                    description = "Learn class structure and object creation",
                    isCompleted = TopicStatus.TODO
                ),
                Topic(
                    id = 9,
                    dayId = 3,
                    title = "Constructors & properties",
                    description = "Primary vs secondary constructors",
                    isCompleted = TopicStatus.TODO
                )
            )

            4 -> listOf(
                Topic(
                    id = 10,
                    dayId = 4,
                    title = "Inheritance & interfaces",
                    description = "Extend classes and implement interfaces",
                    isCompleted = TopicStatus.TODO
                ),
                Topic(
                    id = 11,
                    dayId = 4,
                    title = "Data classes & enums",
                    description = "Use data classes and enum types",
                    isCompleted = TopicStatus.TODO
                ),
                Topic(
                    id = 12,
                    dayId = 4,
                    title = "Null safety",
                    description = "Nullable types, safe calls, and smart casts",
                    isCompleted = TopicStatus.TODO
                )
            )

            5 -> listOf(
                Topic(
                    id = 13,
                    dayId = 5,
                    title = "Collections deep dive",
                    description = "Work with List, Set, Map operations",
                    isCompleted = TopicStatus.TODO
                ),
                Topic(
                    id = 14,
                    dayId = 5,
                    title = "Higher-order functions",
                    description = "Learn lambdas, map, filter, reduce",
                    isCompleted = TopicStatus.TODO
                ),
                Topic(
                    id = 15,
                    dayId = 5,
                    title = "Extension functions",
                    description = "Add new functionality to existing classes",
                    isCompleted = TopicStatus.TODO
                )
            )

            else -> emptyList()
        }
    }
}
