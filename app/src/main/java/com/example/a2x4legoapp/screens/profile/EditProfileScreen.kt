package com.example.a2x4legoapp.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.a2x4legoapp.DestinationScreen
import com.example.a2x4legoapp.LegoViewModel
import com.example.a2x4legoapp.R
import com.example.a2x4legoapp.main.CommonDivider
import com.example.a2x4legoapp.main.CommonImage
import com.example.a2x4legoapp.main.CommonProgressSpinner
import com.example.a2x4legoapp.main.navigateTo
import com.example.a2x4legoapp.ui.theme.BackBlue
import com.example.a2x4legoapp.ui.theme.fontLarge
import com.example.a2x4legoapp.ui.theme.fontXLarge


@Composable
fun EditProfileScreen(navController: NavController, vm: LegoViewModel) {
    val isLoading = vm.inProgress.value
    if (isLoading) {
        CommonProgressSpinner()
    } else {
        val userData = vm.userData.value
        var name by rememberSaveable { mutableStateOf(userData?.name ?: "") }
        var username by rememberSaveable { mutableStateOf(userData?.username ?: "") }
        var bio by rememberSaveable { mutableStateOf(userData?.bio ?: "") }

        ProfileContent(
            vm = vm,
            name = name,
            username = username,
            bio = bio,
            onNameChange = { name = it },
            onUserNameChange = { username = it },
            onBioChange = { bio = it },
            onSave = { vm.updateProfileData(name, username, bio) },
            onBack = { navigateTo(navController, DestinationScreen.Profile) },
            onLogout = {
                vm.onLogout()
                navigateTo(navController, DestinationScreen.Login)
            }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    vm: LegoViewModel,
    name: String,
    username: String,
    bio: String,
    onNameChange: (String) -> Unit,
    onUserNameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    onLogout: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val imageUrl = vm.userData?.value?.imageUrl

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .background(BackBlue)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackBlue)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_backbutton),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onBack.invoke() }
            )
            //Text(text = "Back", modifier = Modifier.clickable { onBack.invoke() })
            Text(
                text = "Save",
                modifier = Modifier.clickable { onSave.invoke() },
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = fontXLarge
            )
        }

        CommonDivider()

        ProfileImage(imageUrl = imageUrl, vm = vm)

        CommonDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Column {
                Text(
                    text = "Name:",
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                    color = Color.White,
                    fontSize = fontLarge,
                    fontWeight = FontWeight.SemiBold
                )
                TextField(
                    value = name,
                    onValueChange = onNameChange,
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
            }

            //Text(text = "Name", modifier = Modifier.width(100.dp))
            //TextField(value = name, onValueChange = onNameChange)

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column {
                Text(
                    text = "Username:",
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                    color = Color.White,
                    fontSize = fontLarge,
                    fontWeight = FontWeight.SemiBold
                )
                TextField(
                    value = username,
                    onValueChange = onUserNameChange,
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
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column {
                Text(
                    text = "Bio:",
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                    color = Color.White,
                    fontSize = fontLarge,
                    fontWeight = FontWeight.SemiBold
                )
                TextField(
                    value = bio,
                    onValueChange = onBioChange,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedLabelColor = Color.DarkGray,
                        unfocusedLabelColor = Color.DarkGray,
                        cursorColor = Color.DarkGray,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(30.dp),
                    singleLine = false,
                    modifier = Modifier.height(150.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    onLogout.invoke()
                },
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier
                    .width(150.dp)
            ) {
                Text(
                    text = "Logout",
                    color = Color.Red,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                )
            }
            //Text(text = "Logout", modifier = Modifier.clickable { onLogout.invoke() })
        }

    }

}

@Composable
fun ProfileImage(imageUrl: String?, vm: LegoViewModel) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { vm.uploadProfileImage(uri) }

    }

    Box(modifier = Modifier.height(IntrinsicSize.Min)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { launcher.launch("image/*") },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            ) {
                if (imageUrl.isNullOrEmpty()) {
                    Image(
                        painter = painterResource(id = R.drawable.default_avatar),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    CommonImage(data = imageUrl, modifier = Modifier.fillMaxSize())
                }
            }
            Text(text = "Chance profile picture")

        }

        val isLoading = vm.inProgress.value
        if (isLoading)
            CommonProgressSpinner()
    }
}