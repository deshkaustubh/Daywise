package tech.kaustubhdeshpande.daywise.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StatusTabRow(
    selectedStatus: TopicStatus,
    onStatusSelected: (TopicStatus) -> Unit,
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
            // Remaining Tab
            StatusTab(
                label = "Remaining",
                isSelected = selectedStatus == TopicStatus.TODO,
                onClick = { onStatusSelected(TopicStatus.TODO) },
                indicatorColor = Color(0xFFE53935), // Red
                modifier = Modifier.weight(1f)
            )

            // Done Tab
            StatusTab(
                label = "Done",
                isSelected = selectedStatus == TopicStatus.COMPLETED,
                onClick = { onStatusSelected(TopicStatus.COMPLETED) },
                indicatorColor = Color(0xFF1E88E5), // Blue
                modifier = Modifier.weight(1f)
            )

            // Skipped Tab
            StatusTab(
                label = "Skipped",
                isSelected = selectedStatus == TopicStatus.SKIPPED,
                onClick = { onStatusSelected(TopicStatus.SKIPPED) },
                indicatorColor = Color(0xFF000000), // Black
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StatusTab(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    indicatorColor: Color,
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

        // Animated Underline Indicator
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(indicatorHeight)
                .background(indicatorColor)
        )
    }
}