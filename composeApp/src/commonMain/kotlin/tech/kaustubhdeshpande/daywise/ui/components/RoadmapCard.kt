package tech.kaustubhdeshpande.daywise.ui.components

import androidx.compose.foundation. background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui. Modifier
import androidx.compose. ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * Reusable card component for displaying roadmap items in the list.
 * Used in the home screen to show saved roadmaps.
 *
 * Features:
 * - Clean card design with rounded corners
 * - Chevron icon indicating it's clickable
 * - Ripple effect on click
 * - Adapts to light/dark theme
 *
 * @param title The roadmap name (e.g., "IoT Syllabus")
 * @param onClick Callback when card is tapped
 * @param modifier Optional modifier for customization
 */
@Composable
fun RoadmapCard(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme. surface)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Roadmap title
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        // Chevron indicator (using Unicode character)
        Text(
            text = "›",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}