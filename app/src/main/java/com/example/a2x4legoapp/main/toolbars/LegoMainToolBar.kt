package com.example.a2x4legoapp.main.toolbars

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.a2x4legoapp.R
import com.example.a2x4legoapp.ui.theme.BackBlue
import com.example.a2x4legoapp.ui.theme.spacingLarge
import com.example.a2x4legoapp.ui.theme.spacingMedium


@Composable
fun LegoToolBar() {

    Box(modifier = Modifier.background(color = BackBlue)) {

        Row(
            modifier = Modifier
                .padding(horizontal = spacingLarge)
                .height(64.dp), // 56.dp olarak da dene
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.legoapp_icon),
                modifier = Modifier
                    .size(64.dp)
                    .padding(start = spacingMedium),
                contentDescription = "Lego App Logo"

            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.ic_lego_head),
                modifier = Modifier
                    .size(42.dp)
                    .padding(start = spacingMedium),
                contentDescription = "Lego kafasi"
            )

            Image(
                painter = painterResource(id = R.drawable.messages_toolbar),
                modifier = Modifier
                    .size(48.dp)
                    .padding(start = spacingMedium)
                    .clickable {  },
                contentDescription = "Messages"
            )


        }
    }

}