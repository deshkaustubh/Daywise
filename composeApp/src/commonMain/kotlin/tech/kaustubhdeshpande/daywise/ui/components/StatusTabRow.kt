package tech.kaustubhdeshpande.daywise.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation. layout.Arrangement
import androidx. compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose. foundation.layout.Row
import androidx.compose.foundation.layout. Spacer
import androidx.compose. foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose. foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui. Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Filter state for topic list display.
 * Separate from TopicStatus to allow "show all" functionality.
 */
enum class TopicFilter {
    ALL,        // Shows all topics
    COMPLETED,  // Shows only done topics
    SKIPPED     // Shows only skipped topics
}

@Composable
fun StatusTabRow(
    selectedFilter: TopicFilter,
    onFilterSelected: (TopicFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Topics Tab (shows ALL topics regardless of status)
            StatusTab(
                label = "Topics",
                isSelected = selectedFilter == TopicFilter.ALL,
                onClick = { onFilterSelected(TopicFilter. ALL) },
                modifier = Modifier.weight(1f)
            )

            // Done Tab (filters to show only completed topics)
            StatusTab(
                label = "Done",
                isSelected = selectedFilter == TopicFilter.COMPLETED,
                onClick = { onFilterSelected(TopicFilter.COMPLETED) },
                modifier = Modifier.weight(1f)
            )

            // Skipped Tab (filters to show only skipped topics)
            StatusTab(
                label = "Skipped",
                isSelected = selectedFilter == TopicFilter.SKIPPED,
                onClick = { onFilterSelected(TopicFilter.SKIPPED) },
                modifier = Modifier. weight(1f)
            )
        }
    }
}

@Composable
private fun StatusTab(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val indicatorHeight by animateDpAsState(
        targetValue = if (isSelected) 3.dp else 0.dp,
        animationSpec = tween(durationMillis = 300),
        label = "indicator_animation"
    )

    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tab Label
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) {
                MaterialTheme.colorScheme.onSurface
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Animated Underline Indicator (same color for all)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(indicatorHeight)
                .background(MaterialTheme.colorScheme.primary)
        )
    }
}