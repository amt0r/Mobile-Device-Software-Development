package com.example.emergencypower.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.emergencypower.logic.AuthValidator
import com.example.emergencypower.ui.components.CustomInputField
import com.example.emergencypower.ui.components.HeaderSection
import com.example.emergencypower.ui.components.SubmitButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun Practice4Screen() {
    var isRegistrationMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isRegistrationMode) { // ViewModel or Navigation Compose are better
            RegistrationSection(
                onSwitchToLogin = { isRegistrationMode = false }
            )
        } else {
            LoginSection(
                onSwitchToRegistration = { isRegistrationMode = true }
            )
        }
    }
}

@Composable
fun LoginSection(onSwitchToRegistration: () -> Unit) {
    val validator = remember { AuthValidator() }
    val coroutineScope = rememberCoroutineScope()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var isErrorStatus by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HeaderSection(title = "EPSS System Login")

        CustomInputField(
            value = username,
            onValueChange = { username = it },
            label = "Operator ID / Username"
        )
        CustomInputField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        SubmitButton(
            text = "Login to System",
            isLoading = isLoading,
            onClick = {
                statusMessage = null
                val validation = validator.validateLogin(username, password)

                if (!validation.isValid) {
                    isErrorStatus = true
                    statusMessage = validation.errorMessage
                } else {
                    isLoading = true
                    coroutineScope.launch {
                        delay(1500.milliseconds)
                        isLoading = false
                        isErrorStatus = false
                        statusMessage = "Authorization successful! System access granted."
                    }
                }
            }
        )

        TextButton(
            onClick = onSwitchToRegistration,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "New Station? Register Account")
        }

        statusMessage?.let { msg ->
            StatusMessageCard(message = msg, isError = isErrorStatus)
        }
    }
}

@Composable
fun RegistrationSection(onSwitchToLogin: () -> Unit) {
    val validator = remember { AuthValidator() }
    val coroutineScope = rememberCoroutineScope()

    var operatorName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var isErrorStatus by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HeaderSection(title = "EPSS Station Registration")

        CustomInputField(
            value = operatorName,
            onValueChange = { operatorName = it },
            label = "Operator Full Name"
        )
        CustomInputField(
            value = email,
            onValueChange = { email = it },
            label = "System Email",
            keyboardType = KeyboardType.Email
        )
        CustomInputField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            isPassword = true
        )
        CustomInputField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirm Password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        SubmitButton(
            text = "Register Station",
            isLoading = isLoading,
            onClick = {
                statusMessage = null
                val validation = validator.validateRegistration(operatorName, email, password, confirmPassword)

                if (!validation.isValid) {
                    isErrorStatus = true
                    statusMessage = validation.errorMessage
                } else {
                    isLoading = true
                    coroutineScope.launch {
                        delay(1500.milliseconds)
                        isLoading = false
                        isErrorStatus = false
                        statusMessage = "Registration successful! Welcome to EPSS."
                    }
                }
            }
        )

        TextButton(
            onClick = onSwitchToLogin,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Already have an account? Login")
        }

        statusMessage?.let { msg ->
            StatusMessageCard(message = msg, isError = isErrorStatus)
        }
    }
}

@Composable
fun StatusMessageCard(message: String, isError: Boolean) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isError) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = message,
            color = if (isError) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Medium
        )
    }
}