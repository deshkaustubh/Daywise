package tech.kaustubhdeshpande.daywise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tech.kaustubhdeshpande.daywise.data.models.TopicStatus
import tech.kaustubhdeshpande.daywise.ui.components.DayCard
import tech.kaustubhdeshpande.daywise.ui.components.RoadmapCard
import tech.kaustubhdeshpande.daywise.ui.components.StatusTabRow
import tech.kaustubhdeshpande.daywise.ui.components.TopicCard
import tech.kaustubhdeshpande.daywise.ui.components.TopicFilter
import tech.kaustubhdeshpande.daywise.ui.theme.AppTheme

@Composable
fun App() {
    var isDarkMode by remember { mutableStateOf(false) }

    AppTheme(useDarkTheme = isDarkMode) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Roadmaps",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            RoadmapCard(
                title = "Math Prep",
                totalDays = 24,
                completedDays = 3,
                onClick = { isDarkMode = !isDarkMode }
            )

            RoadmapCard(
                title = "Design Systems",
                totalDays = 5,
                completedDays = 5,
                onClick = {}
            )

            RoadmapCard(
                title = "Kotlin Basics",
                totalDays = 30,
                completedDays = 0,
                onClick = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Day Progress",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

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

            Text(
                text = "Topics Preview",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            var topic1Status by remember { mutableStateOf(TopicStatus.TODO) }
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

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Filter Tabs Preview",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            var selectedFilter by remember { mutableStateOf(TopicFilter.ALL) }

            StatusTabRow(
                selectedFilter = selectedFilter,
                onFilterSelected = { newFilter -> selectedFilter = newFilter }
            )

            Text(
                text = "Selected:  ${
                    when (selectedFilter) {
                        TopicFilter.ALL -> "Topics (All)"
                        TopicFilter.COMPLETED -> "Done"
                        TopicFilter.SKIPPED -> "Skipped"
                    }
                }",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}