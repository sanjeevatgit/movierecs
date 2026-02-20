package com.moviegeek.tv.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.moviegeek.tv.data.RecommendationItem
import com.moviegeek.tv.data.RecommendationRail

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0B1020), Color(0xFF101B33))
                )
            )
            .padding(horizontal = 36.dp, vertical = 28.dp)
    ) {
        Text(
            text = "MovieGeek TV",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Recommendations from your MovieGeek backend",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFFB3C0D9),
            modifier = Modifier.padding(top = 6.dp, bottom = 18.dp)
        )

        when {
            uiState.loading -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            uiState.error != null -> {
                ErrorState(message = uiState.error, onRetry = onRetry)
            }

            else -> {
                RailList(rails = uiState.rails)
            }
        }
    }
}

@Composable
private fun ErrorState(message: String, onRetry: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(text = "Could not load recommendations", color = Color(0xFFFCA5A5))
        Text(text = message, color = Color(0xFFE2E8F0))
        Button(onClick = onRetry) { Text("Retry") }
    }
}

@Composable
private fun RailList(rails: List<RecommendationRail>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        items(rails) { rail ->
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = rail.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(end = 24.dp)
                ) {
                    items(rail.items) { item ->
                        RecommendationCard(item = item)
                    }
                }
            }
        }
    }
}

@Composable
private fun RecommendationCard(item: RecommendationItem) {
    Row(
        modifier = Modifier
            .focusable()
            .background(
                color = Color(0xFF1E2A44),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "Movie ${item.id}",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = item.score?.let { "Score: %.2f".format(it) } ?: "No score",
                color = Color(0xFFA8B8D8),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
