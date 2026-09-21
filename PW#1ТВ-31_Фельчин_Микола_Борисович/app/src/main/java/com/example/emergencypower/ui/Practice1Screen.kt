package com.example.emergencypower.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.emergencypower.ui.components.HeaderSection
import com.example.emergencypower.ui.components.MetricDisplay
import com.example.emergencypower.ui.components.SubmitButton
import kotlin.random.Random

@Composable
fun Practice1Screen() {
    var powerLoad by remember { mutableIntStateOf(2200) }
    var batteryLevel by remember { mutableIntStateOf(100) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HeaderSection(title = "Emergency Power System")

        Spacer(modifier = Modifier.height(32.dp))

        MetricDisplay(
            label = "Load Forecast",
            value = powerLoad,
            unit = "W"
        )

        Spacer(modifier = Modifier.height(8.dp))

        MetricDisplay(
            label = "Battery Charge Level",
            value = batteryLevel,
            unit = "%"
        )

        Spacer(modifier = Modifier.height(48.dp))

        SubmitButton(
            text = "Update Forecast",
            onClick = {
                powerLoad = Random.nextInt(1000, 5000)
                batteryLevel = Random.nextInt(1, 100)
            }
        )
    }
}