package com.example.emergencypower.logic

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)

class AuthValidator {
    fun validateLogin(usernameInput: String, passwordInput: String): ValidationResult {
        if (usernameInput.isBlank()) {
            return ValidationResult(false, "Operator ID / Username cannot be empty.")
        }
        if (passwordInput.length < 4) {
            return ValidationResult(false, "Security PIN / Password must be at least 4 characters.")
        }
        return ValidationResult(true)
    }

    fun validateRegistration(
        operatorName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): ValidationResult {
        if (operatorName.isBlank()) {
            return ValidationResult(false, "Operator Full Name is required.")
        }
        if (!email.contains("@") || !email.contains(".")) {
            return ValidationResult(false, "Invalid system email address format.")
        }
        if (password.length < 4) {
            return ValidationResult(false, "Password must be at least 4 characters long.")
        }
        if (password != confirmPassword) {
            return ValidationResult(false, "Passwords do not match.")
        }
        return ValidationResult(true)
    }
}