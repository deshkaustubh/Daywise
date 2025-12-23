package tech.kaustubhdeshpande.daywise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tech.kaustubhdeshpande.daywise.ui.components.DayCard
import tech.kaustubhdeshpande.daywise.ui.components.DaySlider
import tech.kaustubhdeshpande.daywise.ui.components.FileUploadArea
import tech.kaustubhdeshpande.daywise.ui.components.RoadmapCard
import tech.kaustubhdeshpande.daywise.ui.components.RoadmapNameField
import tech.kaustubhdeshpande.daywise.ui.components.StatusTabRow
import tech.kaustubhdeshpande.daywise.ui.components.TopicCard
import tech.kaustubhdeshpande.daywise.ui.components.TopicFilter
import tech.kaustubhdeshpande.daywise.ui.components.TopicStatus
import tech.kaustubhdeshpande.daywise.ui.components.UploadState
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
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Screen title
            Text(
                text = "Roadmaps",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
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

            // Status tabs section
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

            // File upload section
            Text(
                text = "File Upload Preview",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            var uploadState by remember {
                mutableStateOf<UploadState>(UploadState.Empty)
            }

            FileUploadArea(
                uploadState = uploadState,
                onUploadClick = {
                    // Simulate uploading
                    uploadState = UploadState.Uploading(
                        fileName = "CS101_Syllabus_2024.pdf",
                        progress = 0.75f,
                        uploadedMB = 7.5f,
                        totalMB = 10f
                    )
                },
                onCancelClick = {
                    uploadState = UploadState.Empty
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Test buttons to change states
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { uploadState = UploadState.Empty },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Empty", style = MaterialTheme.typography.labelSmall)
                }

                Button(
                    onClick = {
                        uploadState = UploadState.Uploading(
                            fileName = "IoT_Syllabus. pdf",
                            progress = 0.45f,
                            uploadedMB = 4.5f,
                            totalMB = 10f
                        )
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Uploading", style = MaterialTheme.typography.labelSmall)
                }

                Button(
                    onClick = {
                        uploadState = UploadState.Uploaded(
                            fileName = "Syllabus_Final.pdf",
                            fileSizeMB = 8.2f
                        )
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Uploaded", style = MaterialTheme.typography.labelSmall)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Day slider section
            Text(
                text = "Day Slider Preview",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            var selectedDays by remember { mutableStateOf(30) }

            DaySlider(
                value = selectedDays,
                onValueChange = { newValue -> selectedDays = newValue }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Selected:  $selectedDays days",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Roadmap name field section
            Text(
                text = "Roadmap Name Field Preview",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            var roadmapName by remember { mutableStateOf("") }
            var showError by remember { mutableStateOf(false) }

            RoadmapNameField(
                value = roadmapName,
                onValueChange = { newValue ->
                    roadmapName = newValue
                    showError = false  // Clear error when user types
                },
                isError = showError && roadmapName.isBlank(),
                errorMessage = if (showError && roadmapName.isBlank()) {
                    "Roadmap name is required"
                } else null
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Test button to trigger validation
            Button(
                onClick = { showError = true },
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text("Test Validation")
            }

            Text(
                text = "Character count: ${roadmapName.length}/50",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
    }
}