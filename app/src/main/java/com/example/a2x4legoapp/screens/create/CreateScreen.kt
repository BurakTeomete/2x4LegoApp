package com.example.a2x4legoapp.screens.create

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.a2x4legoapp.DestinationScreen
import com.example.a2x4legoapp.LegoViewModel
import com.example.a2x4legoapp.R
import com.example.a2x4legoapp.main.CommonProgressSpinner
import com.example.a2x4legoapp.ui.theme.BackBlue
import com.example.a2x4legoapp.ui.theme.LegoYellow
import com.example.a2x4legoapp.ui.theme.SoftDark
import com.example.a2x4legoapp.ui.theme.SoftWhite


@Composable
fun CreateScreen(navController: NavController, vm: LegoViewModel) {

    val newPostImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri ->
        uri?.let {
            val encoded = Uri.encode(it.toString())
            val route = DestinationScreen.NewPost.createRoute(encoded)
            navController.navigate(route)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_signup),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .matchParentSize()
                .rotate(180f),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
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
                        .size(48.dp)
                        .clickable { navController.popBackStack() }
                )



            }

        Box(modifier = Modifier.fillMaxWidth()){
            Card(
                modifier = Modifier
                    .padding(start = 140.dp, top = 80.dp)
                    .scale(2f),
                colors = CardDefaults.cardColors(Color.Transparent)
            ) {
                Image(painter = painterResource(id = R.drawable.spaceman), contentDescription = null)
            }}

        Spacer(modifier = Modifier.size(50.dp))

        Card(modifier = Modifier.padding(start = 32.dp, end = 32.dp), shape = RoundedCornerShape(30.dp)) {

            Row(modifier = Modifier
                .fillMaxWidth()
                .background(SoftWhite), horizontalArrangement = Arrangement.Center) {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .scale(1.7f),
                    text = "Lets choose",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = BackBlue
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SoftWhite)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    onClick = {
                        newPostImageLauncher.launch("image/*")
                    },
                    colors = ButtonDefaults.buttonColors(LegoYellow),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, end = 32.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "Post",
                        color = SoftDark,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp
                    )
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SoftWhite)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                    },
                    colors = ButtonDefaults.buttonColors(LegoYellow),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, end = 32.dp, bottom = 32.dp),
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Text(
                        text = "Story",
                        color = SoftDark,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp
                    )
                }

            }
        }


        val inProgress = vm.inProgress.value
        if (inProgress)
            CommonProgressSpinner()

    }


}

