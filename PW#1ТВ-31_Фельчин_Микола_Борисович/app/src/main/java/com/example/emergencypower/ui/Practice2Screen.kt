package com.example.emergencypower.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.emergencypower.logic.EmergencyPowerCalculator
import com.example.emergencypower.logic.EmergencyPowerCalculator.BackupResult
import com.example.emergencypower.ui.components.CustomInputField
import com.example.emergencypower.ui.components.HeaderSection
import com.example.emergencypower.ui.components.SubmitButton

@Composable
fun Practice2Screen() {
    val calculator = remember { EmergencyPowerCalculator() }

    var loadWattsText by remember { mutableStateOf("500") }
    var batteryAhText by remember { mutableStateOf("100") }
    var voltageText by remember { mutableStateOf("12") }
    var efficiencyText by remember { mutableStateOf("85") }

    var result by remember {
        mutableStateOf(
            calculator.calculateBackupRuntime(
                loadWattsInput = "500",
                batteryCapacityAhInput = "100",
                systemVoltageInput = "12",
                efficiencyInput = "85"
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderSection(title = "PW 2: EPSS Runtime Calculator")

        CustomInputField(
            value = loadWattsText,
            onValueChange = { loadWattsText = it },
            label = "Connected Emergency Load (Watts)",
            keyboardType = KeyboardType.Number
        )

        CustomInputField(
            value = batteryAhText,
            onValueChange = { batteryAhText = it },
            label = "Battery Capacity (Ah)",
            keyboardType = KeyboardType.Number
        )

        CustomInputField(
            value = voltageText,
            onValueChange = { voltageText = it },
            label = "System Voltage (12V / 24V / 48V)",
            keyboardType = KeyboardType.Number
        )

        CustomInputField(
            value = efficiencyText,
            onValueChange = { efficiencyText = it },
            label = "Inverter Efficiency (%)",
            keyboardType = KeyboardType.Number
        )

        SubmitButton(
            text = "Calculate Emergency Backup Time",
            onClick = {
                result = calculator.calculateBackupRuntime(
                    loadWattsInput = loadWattsText,
                    batteryCapacityAhInput = batteryAhText,
                    systemVoltageInput = voltageText,
                    efficiencyInput = efficiencyText
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        CalculationResultCard(result = result)
    }
}

@Composable
private fun CalculationResultCard(result: BackupResult) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Emergency Power Reserves",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Backup Duration: %.1f hours (%.0f mins)"
                    .format(result.runtimeHours, result.runtimeMinutes),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Usable Energy Reserve: %.0f Wh (Total: %.0f Wh)"
                    .format(result.usableCapacityWh, result.totalCapacityWh),
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Status: ${result.statusMessage}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Load Stress Ratio:",
                style = MaterialTheme.typography.labelSmall
            )
            LinearProgressIndicator(
                progress = { result.loadStressFactor },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}