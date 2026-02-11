package com.example.littlelemon

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@SuppressLint("ResourceType")
@Composable
fun Profile(navController: NavController) {

    val context = LocalContext.current
    val sharedPreferences =
        context.getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
    val firstName: String = sharedPreferences.getString("firstName", "").toString()
    val lastName: String = sharedPreferences.getString("lastName", "").toString()
    val email: String = sharedPreferences.getString("email", "").toString()
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
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp, top = 10.dp)
            )

            Text("Last Name")
            OutlinedTextField(
                value = lastName,
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp, top = 10.dp)
            )

            Text("Email")
            OutlinedTextField(
                value = email,
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 80.dp, top = 10.dp)
            )

            Button(
                onClick = {

                    sharedPreferences.edit { clear() }
                    navController.navigate(Onboarding.route)

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
                    "Log out",
                    fontSize = 22.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfile(){
    val navController: NavHostController= rememberNavController()
    Profile(navController)
}

