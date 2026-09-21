package com.example.emergencypower.logic

enum class PowerSourceMode(val displayName: String) {
    GRID("Grid Power"),
    BATTERY("Battery Backup"),
    GENERATOR("Diesel Generator")
}

data class SourceDetails(
    val expectedPowerKw: Int,
    val systemComment: String,
    val isCritical: Boolean
)

fun getDetailsForSource(mode: PowerSourceMode): SourceDetails {
    return when (mode) {
        PowerSourceMode.GRID -> SourceDetails(
            expectedPowerKw = 15,
            systemComment = "System stable. Batteries are charging.",
            isCritical = false
        )
        PowerSourceMode.BATTERY -> SourceDetails(
            expectedPowerKw = 5,
            systemComment = "Warning: Operating on battery reserve. Shed non-essential loads.",
            isCritical = true
        )
        PowerSourceMode.GENERATOR -> SourceDetails(
            expectedPowerKw = 25,
            systemComment = "Generator active. Max capacity available. Refuel in 12 hours.",
            isCritical = false
        )
    }
}