package com.example.a2x4legoapp.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.a2x4legoapp.DestinationScreen
import com.example.a2x4legoapp.LegoViewModel
import com.example.a2x4legoapp.R
import com.example.a2x4legoapp.data.PostData
import com.example.a2x4legoapp.main.BottomNavigationBar
import com.example.a2x4legoapp.main.BottomNavigationItem
import com.example.a2x4legoapp.main.CommonImage
import com.example.a2x4legoapp.main.CommonProgressSpinner
import com.example.a2x4legoapp.main.LikeAnimation
import com.example.a2x4legoapp.main.UserImageCard
import com.example.a2x4legoapp.main.navigateTo
import com.example.a2x4legoapp.main.toolbars.LegoToolBar
import com.example.a2x4legoapp.ui.theme.BackBlue
import com.example.a2x4legoapp.ui.theme.LegoYellow
import com.example.a2x4legoapp.ui.theme.fontMedium
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController, vm: LegoViewModel) {

    val userDataLoading = vm.inProgress.value
    val userData = vm.userData.value
    val personalizedFeed = vm.postsFeed.value
    val personalizedFeedLoading = vm.postsFeedProgress.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackBlue)
    ) {
        LegoToolBar()

        //Story section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(BackBlue)
        ) {
            UserImageCard(userImage = userData?.imageUrl)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(BackBlue)
        ) {
            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(LegoYellow),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 6.dp, bottom = 6.dp),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color.Black),
            ) {
                Text(
                    text = "Your daily trivia is here!",
                    color = Color.Black,
                    modifier = Modifier.padding(top = 6.dp, bottom = 6.dp),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 28.sp
                )
            }
        }


        PostsList(
            posts = personalizedFeed,
            modifier = Modifier.weight(1f),
            loading = personalizedFeedLoading or userDataLoading,
            navController = navController,
            vm = vm,
            currentUserId = userData?.userId ?: ""
        )

        BottomNavigationBar(
            selectedItem = BottomNavigationItem.HOME,
            navController = navController
        )
    }

}


@Composable
fun PostsList(
    posts: List<PostData>,
    modifier: Modifier,
    loading: Boolean,
    navController: NavController,
    vm: LegoViewModel,
    currentUserId: String
) {
    Box(modifier = modifier) {
        LazyColumn {
            items(items = posts) {
                Post(navController, post = it, currentUserId = currentUserId, vm) {

                }
            }
        }
        if (loading)
            CommonProgressSpinner()
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Post(navController: NavController,post: PostData, currentUserId: String, vm: LegoViewModel, onPostClick: () -> Unit) {

    val likeAnimation = remember { mutableStateOf(false) }
    val dislikeAnimation = remember { mutableStateOf(false) }

    val comments = vm.comments.value

    LaunchedEffect(key1 = Unit) {
        vm.getComments(post.postId)
    }

    Card(
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 24.dp, end = 24.dp, top = 4.dp, bottom = 4.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = CircleShape, modifier = Modifier
                        .padding(start = 24.dp, 4.dp)
                        .size(32.dp)
                ) {
                    CommonImage(data = post.userImage, contentScale = ContentScale.Crop)
                }
                Text(
                    text = post.username ?: "",
                    modifier = Modifier.padding(4.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(R.drawable.feed_dots),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 24.dp)
                        .size(26.dp)
                        .clickable {},
                )
            }

            //Description
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (post.postDescription != "") {
                    Text(
                        text = post.postDescription.toString(),
                        modifier = Modifier.padding(start = 24.dp, bottom = 4.dp),
                        color = Color.Black,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        fontSize = fontMedium
                    )
                }
            }
            //Post
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                val modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 150.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                if (post.likes?.contains(currentUserId) == true) {
                                    dislikeAnimation.value = true
                                } else {
                                    likeAnimation.value = true
                                }
                                vm.onLikePost(post)

                            },
                            onTap = {

                            }
                        )
                    }

                    CommonImage(
                        data = post.postImage,
                        modifier = modifier.padding(start= 24.dp, end = 24.dp),
                        contentScale = ContentScale.FillWidth
                    )

                    if (likeAnimation.value) {
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(1000L)
                            likeAnimation.value = false
                        }
                        LikeAnimation()
                    }
                    if (dislikeAnimation.value) {
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(1000L)
                            dislikeAnimation.value = false
                        }
                        LikeAnimation(false)
                    }

                }

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(Color.White),
                verticalAlignment = Alignment.CenterVertically) {
                if (post.likes?.contains(currentUserId) == true){
                    Image(
                        painter = painterResource(id = R.drawable.ic_like),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 18.dp, top = 4.dp),
                        colorFilter = ColorFilter.tint(Color.Red)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_dislike),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 18.dp, top = 4.dp),
                        colorFilter = ColorFilter.tint(Color.Black)
                    )
                }

                Text(text = " ${post.likes?.size ?: 0} likes", modifier = Modifier.padding(start = 0.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_comment),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { post.postId?.let {
                            navController.navigate(DestinationScreen.CommentsScreen.createRoute(it))
                        }  }
                        .padding(start = 18.dp, top = 4.dp),
                    colorFilter = ColorFilter.tint(Color.Black)
                )

            }

            }
        }

}