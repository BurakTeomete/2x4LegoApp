package com.example.a2x4legoapp.screens.store

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.a2x4legoapp.LegoViewModel
import com.example.a2x4legoapp.R
import com.example.a2x4legoapp.main.BottomNavigationBar
import com.example.a2x4legoapp.main.BottomNavigationItem
import com.example.a2x4legoapp.main.UserImageCard
import com.example.a2x4legoapp.main.toolbars.LegoToolBar
import com.example.a2x4legoapp.ui.theme.BackBlue

@Composable
fun StoreScreen(navController: NavController, vm: LegoViewModel) {

    val userData = vm.userData.value

    Column {
        Column(
        modifier = Modifier
            .weight(1f)
            .background(BackBlue)
    ) {

            LegoToolBar()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(BackBlue)
            ) {
                UserImageCard(userImage = userData?.imageUrl)
                Column {
                    Text(
                        text = userData?.username ?: "",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 12.dp),
                        fontSize = 16.sp
                    )

                    Row(modifier = Modifier.padding(bottom = 2.dp)) {
                        Image(painter = painterResource(id = R.drawable.coin ), contentDescription = null, modifier = Modifier.scale(0.9f) )
                        Text(
                            text = userData?.coinBalance.toString() ,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .padding(start = 4.dp),
                            fontSize = 24.sp
                        )
                    }

                }
            }

            StoreImageSlider()
    }

        BottomNavigationBar(
            selectedItem = BottomNavigationItem.STORE,
            navController = navController
        )
    }
}