package com.example.emergencypower.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.emergencypower.logic.PowerSourceMode
import com.example.emergencypower.logic.getDetailsForSource
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.example.emergencypower.R
import com.example.emergencypower.ui.components.HeaderSection


@Composable
fun Practice3Screen() {
    var currentMode by remember { mutableStateOf(PowerSourceMode.GRID) }

    val modeDetails = getDetailsForSource(currentMode)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection(title = "System Status Forecast")

        Spacer(modifier = Modifier.height(24.dp))

        ModeSelectionRow(
            selectedMode = currentMode,
            onModeSelected = { newMode -> currentMode = newMode }
        )

        Spacer(modifier = Modifier.height(32.dp))

        VisualStatusIndicator(mode = currentMode)

        Spacer(modifier = Modifier.weight(1f))

        DetailsDisplayCard(
            mode = currentMode,
            power = modeDetails.expectedPowerKw,
            comment = modeDetails.systemComment,
            isCritical = modeDetails.isCritical
        )
    }
}

@Composable
fun ModeSelectionRow(
    selectedMode: PowerSourceMode,
    onModeSelected: (PowerSourceMode) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        PowerSourceMode.entries.forEach { mode ->
            val isSelected = mode == selectedMode
            Button(
                onClick = { onModeSelected(mode) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text(text = mode.name)
            }
        }
    }
}

@Composable
fun VisualStatusIndicator(mode: PowerSourceMode) {
    val imageResId = when (mode) {
        PowerSourceMode.GRID -> R.drawable.img_grid
        PowerSourceMode.BATTERY -> R.drawable.img_battery
        PowerSourceMode.GENERATOR -> R.drawable.img_generator
    }

    val backgroundColor = MaterialTheme.colorScheme.primary

    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "${mode.displayName} Status Image",
            modifier = Modifier
                .size(150.dp)
                .padding(16.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun DetailsDisplayCard(mode: PowerSourceMode, power: Int, comment: String, isCritical: Boolean) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isCritical) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Mode: ${mode.displayName}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Forecasted Power: $power kW",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Comment: $comment",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}