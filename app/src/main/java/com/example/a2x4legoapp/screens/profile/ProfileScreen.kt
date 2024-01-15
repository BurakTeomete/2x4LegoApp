package com.example.a2x4legoapp.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.a2x4legoapp.DestinationScreen
import com.example.a2x4legoapp.LegoViewModel
import com.example.a2x4legoapp.data.PostData
import com.example.a2x4legoapp.main.BottomNavigationBar
import com.example.a2x4legoapp.main.BottomNavigationItem
import com.example.a2x4legoapp.main.CommonImage
import com.example.a2x4legoapp.main.CommonProgressSpinner
import com.example.a2x4legoapp.main.NavParam
import com.example.a2x4legoapp.main.UserFigureCard
import com.example.a2x4legoapp.main.UserImageCard
import com.example.a2x4legoapp.main.UserWallpaperCard
import com.example.a2x4legoapp.main.navigateTo
import com.example.a2x4legoapp.main.toolbars.LegoProfileToolBar
import com.example.a2x4legoapp.ui.theme.BackBlue

data class PostRow(
    var post1: PostData? = null,
    var post2: PostData? = null,
) {
    fun isFull() = post1 != null && post2 != null
    fun add(post: PostData) {
        if (post1 == null) {
            post1 = post
        } else if (post2 == null) {
            post2 = post
        }
    }
}

@Composable
fun ProfileScreen(navController: NavController, vm: LegoViewModel) {

    val userData = vm.userData.value
    val isLoading = vm.inProgress.value

    val postsLoading = vm.refreshPostsProgress.value
    val posts = vm.posts.value

    val followers = vm.followers.value

    Column {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(BackBlue)
        ) {
            LegoProfileToolBar(navController)

            Box(modifier = Modifier.wrapContentSize()) {

                ProfileWallpaper(userData?.currentWallpaper)

                Row(
                    modifier = Modifier
                        .padding(top = 220.dp)
                        .height(65.dp)
                        .background(
                            BackBlue,
                            shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                        )
                        .fillMaxWidth()
                ) {

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "${posts.size}\nposts",
                        modifier = Modifier
                            .weight(0.7f)
                            .align(Alignment.CenterVertically),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "$followers\nfollowers",
                        modifier = Modifier
                            .weight(0.7f)
                            .align(Alignment.CenterVertically),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${userData?.following?.size ?: 0}\nfollowing",
                        modifier = Modifier
                            .weight(0.7f)
                            .align(Alignment.CenterVertically),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                }
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 24.dp),
                    colors = CardDefaults.cardColors(Color.Transparent)
                ) {
                    ProfileFigure(userData?.currentFigure)
                }
            }

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .background(BackBlue)
            ) {
                //ProfileImage(userData?.imageUrl)
                Row {
                    val usernameDisplay = if (userData?.username == null) "@" else "@${userData?.username}"
                    ProfileImage(userData?.imageUrl)
                    Column {
                        Text(
                            text = userData?.name ?: "",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .padding(top = 26.dp),
                            fontSize = 24.sp
                        )


                        Text(
                            text = usernameDisplay,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .padding(1.dp),
                            fontSize = 14.sp
                        )
                    }

                }
                Text(text = userData?.bio ?: "", color = Color.White, fontSize = 14.sp)

                //val usernameDisplay = if (userData?.username == null) "" else "@${userData?.username}"
                //Text(text = usernameDisplay)

            }

            Spacer(modifier = Modifier.size(4.dp))

            PostList(
                isContextLoading = isLoading,
                postsLoading = postsLoading,
                posts = posts,
                modifier = Modifier
                    .weight(1f)
                    .padding(1.dp)
                    .fillMaxSize()
            ) { post ->
                navigateTo(navController = navController, DestinationScreen.SinglePost, NavParam("post", post))

            }

        }

        BottomNavigationBar(
            selectedItem = BottomNavigationItem.PROFILE,
            navController = navController
        )
    }

    if (isLoading)
        CommonProgressSpinner()
}

@Composable
fun ProfileImage(imageUrl: String?) {
    Box(modifier = Modifier.padding(top = 16.dp), contentAlignment = Alignment.Center) {
        UserImageCard(
            userImage = imageUrl, modifier = Modifier
                .padding(8.dp)
                .size(64.dp)
        )
    }
}

@Composable
fun ProfileFigure(currentFigure: String?) {
    Box {
        UserFigureCard(
            userFigure = currentFigure
        )
    }
}

@Composable
fun ProfileWallpaper(currentWallpaper: String?) {
    Box {
        UserWallpaperCard(
            userWallpaper = currentWallpaper
        )
    }
}

@Composable
fun PostList(
    isContextLoading: Boolean,
    postsLoading: Boolean,
    posts: List<PostData>,
    modifier: Modifier,
    onPostClick: (PostData) -> Unit
) {
    if (postsLoading) {
        CommonProgressSpinner()
    } else if (posts.isEmpty()) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (!isContextLoading) Text(text = "No posts available")
        }
    } else {
        LazyColumn(modifier = modifier) {

            val rows = arrayListOf<PostRow>()
            var currentRow = PostRow()
            rows.add(currentRow)
            for (post in posts) {
                if (currentRow.isFull()) {
                    currentRow = PostRow()
                    rows.add(currentRow)
                }
                currentRow.add(post = post)
            }

            items(items = rows) { row ->
                PostsRow(item = row, onPostClick = onPostClick)
            }
        }
    }
}

@Composable
fun PostsRow(item: PostRow, onPostClick: (PostData) -> Unit) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .height(190.dp)
    ) {
        PostImage(imageUrl = item.post1?.postImage, modifier = Modifier
            .weight(1f)
            .clickable { item.post1?.let { post -> onPostClick(post) } }
        )
        PostImage(imageUrl = item.post2?.postImage, modifier = Modifier
            .weight(1f)
            .clickable { item.post2?.let { post -> onPostClick(post) } }
        )

    }
}


@Composable
fun PostImage(imageUrl: String?, modifier: Modifier) {
    Box(modifier = modifier) {
        var modifier = Modifier
            .padding(6.dp)
            .fillMaxSize()
        if (imageUrl == null) {
            modifier = modifier.clickable(enabled = false) {}
        }
        CommonImage(
            data = imageUrl,
            modifier = modifier.clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
    }
}
