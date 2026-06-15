package com.example.gamecatalog.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gamecatalog.data.model.Game
import com.example.gamecatalog.presentation.viewmodel.GameViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel()
) {
    val games by viewModel.games.collectAsState()
    val useFake by viewModel.useFake.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E))
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "🎮 Catálogo de Juegos",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Toggle Real / Fake
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = if (useFake) "Modo: FAKE (test)" else "Modo: REAL",
                color = if (useFake) Color(0xFFFFAB40) else Color(0xFF69F0AE),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = useFake,
                onCheckedChange = { viewModel.toggleSource() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFFFFAB40),
                    checkedTrackColor = Color(0xFF4A3F00),
                    uncheckedThumbColor = Color(0xFF69F0AE),
                    uncheckedTrackColor = Color(0xFF003020)
                )
            )
        }

        // Lista de juegos
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(games) { game ->
                GameCard(game = game)
            }
        }
    }
}

@Composable
fun GameCard(game: Game) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF16213E)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = game.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoChip(label = game.genre, color = Color(0xFF533483))
                InfoChip(label = game.year.toString(), color = Color(0xFF1A4A6E))
            }
            Spacer(modifier = Modifier.height(8.dp))
            RatingBar(rating = game.rating)
        }
    }
}

@Composable
fun InfoChip(label: String, color: Color) {
    Box(
        modifier = Modifier
            .background(color, shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text = label, color = Color.White, fontSize = 12.sp)
    }
}

@Composable
fun RatingBar(rating: Float) {
    val fullStars = rating.toInt()
    val hasHalf = (rating - fullStars) >= 0.5f

    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(5) { index ->
            val star = when {
                index < fullStars -> "★"
                index == fullStars && hasHalf -> "½"
                else -> "☆"
            }
            Text(
                text = star,
                color = Color(0xFFFFD700),
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = String.format("%.1f", rating),
            color = Color(0xFFAAAAAA),
            fontSize = 13.sp
        )
    }
}

