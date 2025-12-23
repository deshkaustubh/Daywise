package tech.kaustubhdeshpande.daywise

import androidx.compose.foundation.background
import androidx.compose. foundation.layout.Arrangement
import androidx.compose.foundation.layout. Column
import androidx.compose.foundation.layout. Spacer
import androidx.compose. foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose. foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime. Composable
import androidx.compose. runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime. setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tech.kaustubhdeshpande.daywise.ui.components.DayCard
import tech. kaustubhdeshpande. daywise.ui.components.RoadmapCard
import tech. kaustubhdeshpande. daywise.ui.components.TopicCard
import tech.kaustubhdeshpande.daywise.ui.components.TopicStatus
import tech.kaustubhdeshpande.daywise.ui.theme.AppTheme


// ============================================
// MAIN APP
// ============================================

/**
 * Main application composable.
 * Entry point for the UI across all platforms (Android, iOS, Web, Desktop).
 */
@Composable
fun App() {
    var isDarkMode by remember { mutableStateOf(false) }

    AppTheme(useDarkTheme = isDarkMode) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme. background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Screen title
            Text(
                text = "Roadmaps",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme. colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Roadmap cards
            RoadmapCard(
                title = "IoT Syllabus",
                onClick = { isDarkMode = !isDarkMode } // Toggle theme on click
            )

            RoadmapCard(
                title = "Kotlin Basics",
                onClick = {}
            )

            RoadmapCard(
                title = "Math Prep",
                onClick = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Day cards section
            Text(
                text = "Day Progress",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Example day cards with R-C-S stats
            DayCard(
                dayNumber = 1,
                totalTopics = 10,
                remainingCount = 5,
                completedCount = 2,
                skippedCount = 1,
                onClick = {}
            )

            DayCard(
                dayNumber = 2,
                totalTopics = 8,
                remainingCount = 2,
                completedCount = 5,
                skippedCount = 1,
                onClick = {}
            )

            DayCard(
                dayNumber = 3,
                totalTopics = 12,
                remainingCount = 8,
                completedCount = 4,
                skippedCount = 0,
                onClick = {}
            )

            DayCard(
                dayNumber = 4,
                totalTopics = 6,
                remainingCount = 0,
                completedCount = 6,
                skippedCount = 0,
                onClick = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Topic cards section
            Text(
                text = "Topics Preview",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Example topic cards with different states
            var topic1Status by remember { mutableStateOf(TopicStatus. TODO) }
            var topic2Status by remember { mutableStateOf(TopicStatus.COMPLETED) }
            var topic3Status by remember { mutableStateOf(TopicStatus.SKIPPED) }

            TopicCard(
                title = "Introduction to IoT",
                status = topic1Status,
                onStatusChange = { newStatus -> topic1Status = newStatus }
            )

            TopicCard(
                title = "Communication Protocols",
                status = topic2Status,
                onStatusChange = { newStatus -> topic2Status = newStatus }
            )

            TopicCard(
                title = "Security in IoT",
                status = topic3Status,
                onStatusChange = { newStatus -> topic3Status = newStatus }
            )
        }
    }
}