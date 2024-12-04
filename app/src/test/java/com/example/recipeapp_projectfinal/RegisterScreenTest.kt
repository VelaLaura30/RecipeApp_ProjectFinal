package com.example.recipeapp_projectfinal

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.*
import com.example.recipeapp_projectfinal.composables.RegisterScreen
import org.junit.Rule
import org.junit.Test

class RegisterScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun registerScreen_validInput_registersUser() {

        var registerSuccess = false


        composeTestRule.setContent {
            RegisterScreen(onRegisterClick = { registerSuccess = true })
        }

        composeTestRule.onNodeWithTag("nameField").performTextInput("John Doe")
        composeTestRule.onNodeWithTag("emailField").performTextInput("johndoe@example.com")
        composeTestRule.onNodeWithTag("passwordField").performTextInput("password123")
        composeTestRule.onNodeWithTag("confirmPasswordField").performTextInput("password123")

        composeTestRule.onNodeWithTag("registerButton").performClick()


        assert(registerSuccess)
    }

    @Test
    fun registerScreen_invalidInput_showsError() {

        var registerSuccess = false


        composeTestRule.setContent {
            RegisterScreen(onRegisterClick = { registerSuccess = true })
        }

        composeTestRule.onNodeWithTag("nameField").performTextInput("John Doe")
        composeTestRule.onNodeWithTag("emailField").performTextInput("johndoe@example.com")
        composeTestRule.onNodeWithTag("passwordField").performTextInput("password123")
        composeTestRule.onNodeWithTag("confirmPasswordField").performTextInput("password456")


        composeTestRule.onNodeWithTag("registerButton").performClick()


        assert(!registerSuccess)
    }
}

