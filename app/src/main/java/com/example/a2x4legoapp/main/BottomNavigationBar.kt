package com.example.a2x4legoapp.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.a2x4legoapp.DestinationScreen
import com.example.a2x4legoapp.R
import com.example.a2x4legoapp.ui.theme.BackBlue
import com.example.a2x4legoapp.ui.theme.LegoYellow

enum class BottomNavigationItem(val icon: Int, val navDestination: DestinationScreen) {
    HOME(R.drawable.ic_nav_home, DestinationScreen.Home),
    SEARCH(R.drawable.ic_nav_search, DestinationScreen.Search),
    CREATE(R.drawable.ic_nav_create, DestinationScreen.Create),
    STORE(R.drawable.ic_nav_store, DestinationScreen.Store),
    PROFILE(R.drawable.ic_nav_profile, DestinationScreen.Profile)
}

@Composable
fun BottomNavigationBar(selectedItem: BottomNavigationItem, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .height(72.dp)
            .background(BackBlue),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 18.dp, end = 18.dp)
                .height(96.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            for (item in BottomNavigationItem.values()) {
                Image(
                    painter = painterResource(id = item.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(6.dp)
                        .weight(1f)
                        .clickable {
                            navigateTo(navController, item.navDestination)
                        },
                    colorFilter = if (item == selectedItem) ColorFilter.tint(LegoYellow)
                    else null
                )
            }
        }


    }
}