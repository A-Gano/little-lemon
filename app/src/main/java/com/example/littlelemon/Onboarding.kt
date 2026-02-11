package com.example.littlelemon

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@SuppressLint("ResourceType")
@Composable
fun Onboarding(navController: NavHostController) {

    val context = LocalContext.current
    val sharedPreferences =
        context.getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var showError by remember { mutableStateOf(false) }

    // -------- Alert Dialog ----------
    if (showError) {
        AlertDialog(
            onDismissRequest = { showError = false },
            confirmButton = {
                TextButton(onClick = { showError = false }) {
                    Text("OK")
                }
            },
            title = { Text("Registration Error") },
            text = { Text("Please fill all fields") }
        )
    }

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            Modifier.padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.raw.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(80.dp)
                    .width(200.dp)
            )
        }

        Column(
            Modifier
                .height(150.dp)
                .fillMaxWidth()
                .background(Color(0xFF495E57)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Let's get to know you",
                fontSize = 36.sp,
                color = Color.White
            )
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
        ) {

            Text(
                "Personal Information",
                modifier = Modifier.padding(top = 20.dp, bottom = 40.dp),
                fontSize = 22.sp
            )

            Text("First Name")
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp, top = 10.dp)
            )

            Text("Last Name")
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp, top = 10.dp)
            )

            Text("Email")
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 80.dp, top = 10.dp)
            )

            Button(
                onClick = {

                    if (firstName.isEmpty() ||
                        lastName.isEmpty() ||
                        email.isEmpty()
                    ) {

                        showError = true

                    } else {

                        // -------- Save Data ----------
                        sharedPreferences.edit {
                            putString("firstName", firstName)
                                .putString("lastName", lastName)
                                .putString("email", email)
                        }

                        // -------- Navigate ----------
                        navController.navigate("Home") {
                            popUpTo("Onboarding") { inclusive = true }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF4CE14)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp)
            ) {

                Text(
                    "Register",
                    fontSize = 22.sp,
                    color = Color.Black
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewOnboarding(){
    val navController: NavHostController= rememberNavController()
    Onboarding(navController)
}
