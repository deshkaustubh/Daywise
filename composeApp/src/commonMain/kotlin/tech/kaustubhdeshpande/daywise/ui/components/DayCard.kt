package tech.kaustubhdeshpande.daywise.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Card component for displaying individual day items in a roadmap.
 * Shows day number, total topics, and color-coded statistics.
 *
 * Design:
 * - Left:  "Day X" + "Topics: Y"
 * - Right: R (Remaining), C (Completed), S (Skipped) with counts
 *
 * @param dayNumber The current day number (1, 2, 3, etc.)
 * @param totalTopics Total number of topics for this day
 * @param remainingCount Number of remaining topics (blue)
 * @param completedCount Number of completed topics (green)
 * @param skippedCount Number of skipped topics (red)
 * @param onClick Callback when card is tapped
 * @param modifier Optional modifier for customization
 */
@Composable
fun DayCard(
    dayNumber: Int,
    totalTopics: Int,
    remainingCount: Int = 0,
    completedCount: Int = 0,
    skippedCount: Int = 0,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side - Day info
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // "Day 1"
            Text(
                text = "Day $dayNumber",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )

            // "Topics: 10"
            Text(
                text = "Topics: $totalTopics",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        // Right side - R C S stats
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // R - Remaining (Blue)
            StatColumn(
                label = "R",
                count = remainingCount,
                color = Color(0xFFF44336) // Red
            )

            // C - Completed (Green)
            StatColumn(
                label = "C",
                count = completedCount,
                color = Color.Blue // Blue
            )

            // S - Skipped (Red)
            StatColumn(
                label = "S",
                count = skippedCount,
                color = MaterialTheme.colorScheme.primary // Black
            )
        }
    }
}

/**
 * Helper composable for displaying a single stat column (R/C/S).
 */
@Composable
private fun StatColumn(
    label: String,
    count: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Label (R, C, or S)
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = color
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Count number
        Text(
            text = count.toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}