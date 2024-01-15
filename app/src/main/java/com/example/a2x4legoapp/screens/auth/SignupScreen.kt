package com.example.a2x4legoapp.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.a2x4legoapp.DestinationScreen
import com.example.a2x4legoapp.LegoViewModel
import com.example.a2x4legoapp.R
import com.example.a2x4legoapp.main.CheckSignedIn
import com.example.a2x4legoapp.main.CommonProgressSpinner
import com.example.a2x4legoapp.main.navigateTo

//navController: NavController, vm: LegoViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController, vm: LegoViewModel) {

    CheckSignedIn(vm = vm, navController = navController)

    val focus = LocalFocusManager.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_signup),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .verticalScroll(
                rememberScrollState()
            ),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {

            val name = remember { mutableStateOf(TextFieldValue()) }
            val usernameState = remember { mutableStateOf(TextFieldValue()) }
            val emailState = remember{ mutableStateOf(TextFieldValue()) }
            val passState = remember { mutableStateOf(TextFieldValue()) }

            Spacer(modifier = Modifier.size(150.dp))

            Text(
                modifier = Modifier
                    .scale(1.2f),
                text = "SIGN UP",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.size(64.dp))

            TextField(
                value = usernameState.value,
                onValueChange = {usernameState.value = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 64.dp, end = 64.dp),
                label = {
                    Text(text = "Username")
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedLabelColor = Color.DarkGray,
                    unfocusedLabelColor = Color.DarkGray,
                    cursorColor = Color.DarkGray,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(30.dp),
            )

            Spacer(modifier = Modifier.size(32.dp))

            TextField(
                value = emailState.value,
                onValueChange = {emailState.value = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 64.dp, end = 64.dp),
                label = {
                    Text(text = "Email")
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedLabelColor = Color.DarkGray,
                    unfocusedLabelColor = Color.DarkGray,
                    cursorColor = Color.DarkGray,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(30.dp),
            )

            Spacer(modifier = Modifier.size(32.dp))

            TextField(
                value = passState.value,
                onValueChange = {passState.value = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 64.dp, end = 64.dp),
                label = {
                    Text(text = "Password")
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedLabelColor = Color.DarkGray,
                    unfocusedLabelColor = Color.DarkGray,
                    cursorColor = Color.DarkGray,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(30.dp),
                visualTransformation = PasswordVisualTransformation(),
            )

            Spacer(modifier = Modifier.size(32.dp))

            Button(
                onClick = {
                    focus.clearFocus(true)
                          vm.onSignup(
                              usernameState.value.text,
                              emailState.value.text,
                              passState.value.text
                          )
                },
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier
                    .width(150.dp)) {
                Text(text = "Sign Up", color = Color.Red, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.size(200.dp))

            Row {
                Text(text = "Already have an account!", color = Color.White,modifier = Modifier.padding(top = 14.dp))
                //Spacer(modifier = Modifier.size(8.dp))
                TextButton(onClick = { navigateTo(navController, DestinationScreen.Login) }) {
                    Text(text = "Login", color = Color.Yellow, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                }
            }





        }

        //Yuklenme ekrani
        val isLoading = vm.inProgress.value
        if (isLoading) {
            CommonProgressSpinner()
        }

    }
}


@Preview(showBackground = true)
@Composable
fun SignupScreenPreview(){

}


