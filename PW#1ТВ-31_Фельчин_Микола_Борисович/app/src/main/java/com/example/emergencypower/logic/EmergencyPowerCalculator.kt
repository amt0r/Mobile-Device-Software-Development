package com.example.emergencypower.logic

class EmergencyPowerCalculator {

    data class BackupResult(
        val runtimeHours: Double,
        val runtimeMinutes: Double,
        val totalCapacityWh: Double,
        val usableCapacityWh: Double,
        val statusMessage: String,
        val loadStressFactor: Float
    )

    fun calculateBackupRuntime(
        loadWattsInput: String,
        batteryCapacityAhInput: String,
        systemVoltageInput: String,
        efficiencyInput: String = "85", // Default inverter efficiency 85%
        dodInput: String = "80"         // Default depth of discharge 80%
    ): BackupResult {
        val loadWatts = loadWattsInput.toDoubleOrNull() ?: 0.0
        val batteryCapacityAh = batteryCapacityAhInput.toDoubleOrNull() ?: 0.0
        val systemVoltage = systemVoltageInput.toDoubleOrNull() ?: 0.0
        val efficiency = (efficiencyInput.toDoubleOrNull() ?: 85.0) / 100.0
        val dod = (dodInput.toDoubleOrNull() ?: 80.0) / 100.0

        val totalCapacityWh = batteryCapacityAh * systemVoltage

        val usableCapacityWh = totalCapacityWh * efficiency * dod

        val runtimeHours = if (loadWatts > 0) usableCapacityWh / loadWatts else 0.0
        val runtimeMinutes = runtimeHours * 60.0

        val status = when {
            loadWatts <= 0 -> "No Load Connected"
            runtimeHours >= 8.0 -> "Optimal: Extended Emergency Backup"
            runtimeHours >= 2.0 -> "Standard: Sufficient Reserve"
            runtimeHours > 0.0 -> "Warning: Low Backup Time (< 2 hours)"
            else -> "Critical: System Overload"
        }

        // Standard 2000W emergency load limit
        val stressFactor = (loadWatts / 2000.0).toFloat().coerceIn(0f, 1f)

        return BackupResult(
            runtimeHours = runtimeHours,
            runtimeMinutes = runtimeMinutes,
            totalCapacityWh = totalCapacityWh,
            usableCapacityWh = usableCapacityWh,
            statusMessage = status,
            loadStressFactor = stressFactor
        )
    }
}