package com.example.a2x4legoapp.screens.search


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.a2x4legoapp.DestinationScreen
import com.example.a2x4legoapp.LegoViewModel
import com.example.a2x4legoapp.R
import com.example.a2x4legoapp.main.BottomNavigationBar
import com.example.a2x4legoapp.main.BottomNavigationItem
import com.example.a2x4legoapp.main.CommonImage
import com.example.a2x4legoapp.main.NavParam
import com.example.a2x4legoapp.main.navigateTo
import com.example.a2x4legoapp.main.toolbars.LegoToolBar
import com.example.a2x4legoapp.screens.profile.PostList
import com.example.a2x4legoapp.ui.theme.BackBlue

@Composable
fun SearchScreen(navController: NavController, vm: LegoViewModel) {

    val searchedPostsLoading = vm.searchedPostProgress.value
    val searchedPosts = vm.searchedPosts.value
    var searchTerm by rememberSaveable { mutableStateOf("") }
    var isSearchInitiated by remember { mutableStateOf(false) }

    Column {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(BackBlue)
        ) {

            LegoToolBar()

            SearchBar(
                searchTerm = searchTerm,
                onSearchChange = { searchTerm = it },
                onSearch = {
                    vm.searchPosts(searchTerm)
                    isSearchInitiated = true
                }
            )
            if (isSearchInitiated) {
                // Arama yapıldıysa PostList'i göster
                PostList(
                    isContextLoading = false,
                    postsLoading = searchedPostsLoading,
                    posts = searchedPosts,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(8.dp)
                ) { post ->
                    navigateTo(
                        navController = navController,
                        dest = DestinationScreen.SinglePost,
                        NavParam("post", post)
                    )
                }
            } else {
                // Arama yapılmadıysa varsayılan içeriği göster
                SearchHelpScreen() // Varsayılan içerik için bir Composable fonksiyonu
            }
        }
        BottomNavigationBar(
            selectedItem = BottomNavigationItem.SEARCH,
            navController = navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchTerm: String, onSearchChange: (String) -> Unit, onSearch: () -> Unit) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = searchTerm,
        onValueChange = onSearchChange,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, CircleShape),
        shape = CircleShape,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
                focusManager.clearFocus()
            }
        ),
        maxLines = 1,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            textColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            IconButton(onClick = {
                onSearch()
                focusManager.clearFocus()
            }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            }
        }
    )
}

@Composable
fun SearchHelpScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackBlue)
            .verticalScroll(
                rememberScrollState()
            )
    ) {

        Card(
            shape = RoundedCornerShape(corner = CornerSize(20.dp)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .height(150.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.lego_starwars),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(), // Box'un tüm alanını kaplar
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        modifier = Modifier
                            .padding(4.dp)
                            .align(Alignment.BottomEnd),
                        text = "STAR WARS",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

            }
        }

        Row {


            Card(
                shape = RoundedCornerShape(corner = CornerSize(20.dp)),
                modifier = Modifier
                    .padding(start = 24.dp)
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .width(160.dp)
                        .height(324.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(324.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.lego_movie),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(), // Box'un tüm alanını kaplar
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            modifier = Modifier
                                .padding(4.dp)
                                .align(Alignment.BottomCenter),
                            text = "MOVIE",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                }
            }

            Column {

            Card(
                shape = RoundedCornerShape(corner = CornerSize(20.dp)),
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp)
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .width(160.dp)
                        .height(150.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    ) {
                        //MARVEL
                        Image(
                            painter = painterResource(id = R.drawable.lego_marvel),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(), // Box'un tüm alanını kaplar
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            modifier = Modifier
                                .padding(4.dp)
                                .align(Alignment.BottomCenter),
                            text = "MARVEL",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                }
            }

                Card(
                    shape = RoundedCornerShape(corner = CornerSize(20.dp)),
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, top = 24.dp)
                        .wrapContentHeight()
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .width(160.dp)
                            .height(150.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.lego_dc),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize(), // Box'un tüm alanını kaplar
                                contentScale = ContentScale.Crop
                            )

                            Text(
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .align(Alignment.BottomEnd),
                                text = "DC",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                    }
                }




        }

        }

        Card(
            shape = RoundedCornerShape(corner = CornerSize(20.dp)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .height(150.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.lego_ninjago),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(), // Box'un tüm alanını kaplar
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        modifier = Modifier
                            .padding(4.dp)
                            .align(Alignment.BottomEnd),
                        text = "NINJAGO",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun SearchHelpPreview() {
    SearchHelpScreen()
}