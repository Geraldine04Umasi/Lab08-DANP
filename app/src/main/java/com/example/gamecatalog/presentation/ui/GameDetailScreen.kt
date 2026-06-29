package com.example.gamecatalog.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.gamecatalog.data.model.GameDetail
import com.example.gamecatalog.presentation.viewmodel.GameDetailUiState
import com.example.gamecatalog.presentation.viewmodel.GameDetailViewModel

@Composable
fun GameDetailScreen(
    gameId: Int,
    onBack: () -> Unit,
    viewModel: GameDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E))
            .padding(16.dp)
    ) {
        // Botón volver
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White
            )
        }

        when (val state = uiState) {
            is GameDetailUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF69F0AE))
                }
            }
            is GameDetailUiState.Success -> {
                GameDetailContent(gameDetail = state.gameDetail)
            }
            is GameDetailUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "⚠️ ${state.message}",
                        color = Color(0xFFFF6B6B),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = { viewModel.loadGameDetail() }) {
                        Text("Reintentar")
                    }
                }
            }
        }
    }
}

@Composable
private fun GameDetailContent(gameDetail: GameDetail) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        // Imagen
        if (gameDetail.imageUrl != null) {
            AsyncImage(
                model = gameDetail.imageUrl,
                contentDescription = gameDetail.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Color(0xFF0F1B33), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("🎮", fontSize = 48.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Título
        Text(
            text = gameDetail.title,
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Fecha y rating
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("📅 ${gameDetail.released}", color = Color(0xFFAAAAAA), fontSize = 14.sp)
            RatingBar(rating = gameDetail.rating)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Géneros
        SectionTitle("Géneros")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            gameDetail.genres.forEach { genre ->
                InfoChip(label = genre, color = Color(0xFF533483))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Plataformas
        SectionTitle("Plataformas")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            gameDetail.platforms.forEach { platform ->
                InfoChip(label = platform, color = Color(0xFF1A4A6E))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Descripción
        SectionTitle("Descripción")
        Text(
            text = gameDetail.description,
            color = Color(0xFFCCCCCC),
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        color = Color(0xFF69F0AE),
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}