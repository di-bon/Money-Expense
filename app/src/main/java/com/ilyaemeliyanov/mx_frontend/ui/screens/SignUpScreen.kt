package com.ilyaemeliyanov.mx_frontend.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ilyaemeliyanov.mx_frontend.AuthScreens
import com.ilyaemeliyanov.mx_frontend.data.user.Currency
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXInput
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXRectangularButton
import com.ilyaemeliyanov.mx_frontend.ui.composables.MXSecretInput
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXTheme
import com.ilyaemeliyanov.mx_frontend.ui.theme.euclidCircularA
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXAuthViewModel
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel


@Composable
fun SignUpScreen(
    navController: NavController,
    mxAuthViewModel: MXAuthViewModel,
    mxViewModel: MXViewModel,
//    canNavigateBack: Boolean,
//    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    // ---
    // TODO: to be replaced by viewModel class
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    // ---

    var isFirstnameValid by remember { mutableStateOf(true) }
    var isLastnameValid by remember { mutableStateOf(true) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }
    var isConfirmPasswordValid by remember { mutableStateOf(true) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
//        Column (
//            horizontalAlignment = Alignment.Start
//        ) {
//            if (canNavigateBack) {
//                IconButton(
//                    onClick = navigateUp,
//                    modifier = Modifier
//                ) {
//                    Icon(
//                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                        contentDescription = "Back button",
//                        modifier = Modifier
//                    )
//                }
//            }
            Text(
                text = "Sign up",
                style = TextStyle(
                    fontFamily = euclidCircularA,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 64.sp
                )
            )
//        }
        Spacer(modifier = Modifier.height(24.dp))
        Column {
            MXInput(
                titleText = "First name",
                labelText = "Enter your first name...",
                text = firstName,
                onTextChange = { value ->
                    firstName = value
                    isFirstnameValid = true
                               },
                modifier = Modifier.padding(vertical = 8.dp),
                isError = !isFirstnameValid,
                errorMessage = "Enter a valid firstname"
            )
            MXInput(
                titleText = "Last name",
                labelText = "Enter your last name...",
                text = lastName,
                onTextChange = { value ->
                    lastName = value
                    isLastnameValid = true
                               },
                modifier = Modifier.padding(vertical = 8.dp),
                isError = !isLastnameValid,
                errorMessage = "Enter a valid lastname"
            )
            MXInput(
                titleText = "Email",
                labelText = "Enter your email...",
                text = email,
                onTextChange = { value ->
                    email = value
                    isEmailValid = true
                               },
                modifier = Modifier.padding(vertical = 8.dp),
                isError = !isEmailValid,
                errorMessage = "Enter a valid email"
            )
            MXSecretInput(
                titleText = "Password",
                labelText = "Enter your password...",
                text = password,
                onTextChange = { value ->
                    password = value
                    isPasswordValid = true
                               },
                modifier = Modifier.padding(vertical = 8.dp),
                isError = !isPasswordValid,
                errorMessage = "Enter a password of at least 6 characters"
            )
            MXSecretInput(
                titleText = "Confirm password",
                labelText = "Confirm your password...",
                text = confirmPassword,
                onTextChange = { value ->
                    confirmPassword = value
                    isConfirmPasswordValid = true
                               },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.padding(vertical = 8.dp),
                isError = !isConfirmPasswordValid,
                errorMessage = "Enter the same valid password again"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(24.dp))
        MXRectangularButton(
            onClick = {
                isFirstnameValid = mxViewModel.validateContent(firstName)
                isLastnameValid = mxViewModel.validateContent(lastName)
                isEmailValid = mxViewModel.validateEmail(email)
                isPasswordValid = mxViewModel.validatePassword(password) && mxViewModel.checkConfirmPassword(password, confirmPassword)
                isConfirmPasswordValid = mxViewModel.validatePassword(confirmPassword) && mxViewModel.checkConfirmPassword(password, confirmPassword)

                if (isFirstnameValid && isLastnameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid) {
                    mxAuthViewModel.signUp(email, password) { res, error ->
                        if (res) {
                            mxViewModel.email = email
                            mxViewModel.createAndSaveUser(
                                email = email,
                                firstName = firstName,
                                lastName = lastName,
                                password = password,
                                currency = Currency.US_DOLLAR // default currency
                            )
                            navController.navigate(AuthScreens.MXApp.name) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        } else {
                            Toast.makeText(context, "An error occurred during sign up: $error", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                      },
            containerColor = Color.Black,
            contentColor = Color.White,
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Sign up",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    MXTheme {
//        SignUpScreen(
//            navController = rememberNavController(),
//            mxAuthViewModel = MXAuthViewModel(),
//            modifier = Modifier
//                .background(color = Color(246, 246, 246))
//                .padding(32.dp)
//                .fillMaxWidth()
//                .fillMaxHeight()
//        )
    }
}