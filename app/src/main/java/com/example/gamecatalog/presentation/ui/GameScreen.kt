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
import com.example.gamecatalog.presentation.viewmodel.GameUiState
import com.example.gamecatalog.presentation.viewmodel.GameViewModel
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton


@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E))
            .padding(16.dp)
    ) {
        // Header (igual que antes)
        Text(
            text = "🎮 Catálogo de Juegos",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),

            label = {
                Text("Buscar juego")
            },

            placeholder = {
                Text("Ej. Elden Ring")
            },

            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar"
                )
            },

            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(
                        onClick = { searchText = "" }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Limpiar búsqueda"
                        )
                    }
                }
            },

            singleLine = true,

            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,

                focusedBorderColor = Color(0xFF69F0AE),
                unfocusedBorderColor = Color.Gray,

                focusedLabelColor = Color(0xFF69F0AE),
                unfocusedLabelColor = Color.LightGray,

                cursorColor = Color(0xFF69F0AE),

                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray
            )
        )

        // Contenido según el estado: Loading / Success / Error
        when (val state = uiState) {
            is GameUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF69F0AE))
                }
            }

            is GameUiState.Success -> {
                val filteredGames = state.games.filter {
                    it.title.contains(searchText, ignoreCase = true)
                }
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(filteredGames) { game ->
                        GameCard(game = game)
                    }
                }
            }

            is GameUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "⚠️ ${state.message}",
                        color = Color(0xFFFF6B6B),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Button(onClick = { viewModel.loadGames() }) {
                        Text("Reintentar")
                    }
                }
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
        Column {
            if (game.imageUrl != null) {
                AsyncImage(
                    model = game.imageUrl,
                    contentDescription = game.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(Color(0xFF0F1B33)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🎮", fontSize = 36.sp)
                }
            }

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

