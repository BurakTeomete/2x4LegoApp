package com.example.a2x4legoapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a2x4legoapp.data.PostData
import com.example.a2x4legoapp.main.NotificationMessage
import com.example.a2x4legoapp.screens.auth.LoginScreen
import com.example.a2x4legoapp.screens.auth.SignupScreen
import com.example.a2x4legoapp.screens.comments.CommentsScreen
import com.example.a2x4legoapp.screens.create.CreateScreen
import com.example.a2x4legoapp.screens.create.NewPostScreen
import com.example.a2x4legoapp.screens.home.HomeScreen
import com.example.a2x4legoapp.screens.profile.EditProfileScreen
import com.example.a2x4legoapp.screens.profile.ProfileScreen
import com.example.a2x4legoapp.screens.profile.SinglePostScreen
import com.example.a2x4legoapp.screens.search.SearchScreen
import com.example.a2x4legoapp.screens.store.StoreScreen
import com.example.a2x4legoapp.ui.theme._2x4LegoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _2x4LegoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LegoApp()
                }
            }
        }
    }
}

sealed class DestinationScreen(val route: String) {
    object Signup: DestinationScreen("signup")
    object Login: DestinationScreen("login")
    object Home: DestinationScreen("home")
    object Search: DestinationScreen("search")
    object Create: DestinationScreen("create")
    object Store: DestinationScreen("store")
    object Profile: DestinationScreen("profile")
    object EditProfile: DestinationScreen("editrofile")
    object NewPost: DestinationScreen("newpost/{imageUri}") {
        fun createRoute(uri: String) = "newpost/$uri"
    }
    object SinglePost: DestinationScreen("singlepost/{postData}")
    object CommentsScreen: DestinationScreen("comments/{postId}") {
        fun createRoute(postId: String) = "comments/$postId"
    }


}


@Composable
fun LegoApp(){
    val vm = hiltViewModel<LegoViewModel>()
    val navController = rememberNavController()


    NotificationMessage(vm = vm)

    NavHost(navController = navController, startDestination = DestinationScreen.Login.route) {
        composable(DestinationScreen.Login.route) {
            LoginScreen(navController = navController, vm = vm)
        }
        composable(DestinationScreen.Signup.route) {
            SignupScreen(navController = navController, vm = vm)
        }
        composable(DestinationScreen.Home.route) {
            HomeScreen(navController = navController, vm = vm)
        }
        composable(DestinationScreen.Search.route) {
            SearchScreen(navController = navController, vm = vm)
        }
        composable(DestinationScreen.Create.route) {
            CreateScreen(navController = navController, vm = vm)
        }
        composable(DestinationScreen.Store.route) {
            StoreScreen(navController = navController, vm = vm)
        }
        composable(DestinationScreen.Profile.route) {
            ProfileScreen(navController = navController, vm = vm)
        }
        composable(DestinationScreen.EditProfile.route) {
            EditProfileScreen(navController = navController, vm = vm)
        }
        composable(DestinationScreen.NewPost.route) {navBackStachEntry ->
            val imageUri = navBackStachEntry.arguments?.getString("imageUri")
            imageUri?.let {
                NewPostScreen(navController = navController, vm = vm, encodedUri = it)
            }
        }
        composable(DestinationScreen.SinglePost.route) {
            val postData = navController.previousBackStackEntry?.arguments?.getParcelable<PostData>("post")
            postData?.let { SinglePostScreen(navController = navController, vm = vm, post = postData) }
        }
        composable(DestinationScreen.CommentsScreen.route) {navBackStachEntry ->
            val postId = navBackStachEntry.arguments?.getString("postId")
            postId?.let {
                CommentsScreen(navController = navController, vm = vm, postId = it)
            }
        }




    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    _2x4LegoAppTheme {
       LegoApp()
    }
}