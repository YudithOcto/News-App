package com.news.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.news.app.feature.data.datasource.remote.StoryItemDto
import com.news.app.feature.ui.viewmodel.StoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val storyViewModel: StoryViewModel by viewModels()

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state = storyViewModel.stories.value
            when (state) {
                is StoryViewModel.State.Error -> ErrorMessage(title = state.title)
                is StoryViewModel.State.Loading -> Loading()
                is StoryViewModel.State.SuccessGetData -> {
                    val navController = rememberNavController()
                    NavHost(navController =navController, startDestination = Screen.ListScreen.route) {
                        composable(
                            route = Screen.ListScreen.route
                        ) {
                            RenderListView(state.data, storyViewModel, navController)
                        }
                        
                        composable(
                            route = Screen.DetailScreen.route + "/{itemId}"
                        ) {
                            DetailScreen()
                        }
                    }
                }
            }
        }

        storyViewModel.getStories()
    }
}

@Composable
fun Loading() = Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier.fillMaxSize()
) {
    CircularProgressIndicator()
}

@Composable
fun ErrorMessage(title: String) = Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier.fillMaxSize()
) {
    Text(title)
}

@ExperimentalMaterialApi
@Composable
fun RenderListView(items: List<StoryItemDto>, viewModel: StoryViewModel,
navController: NavController) {
    LazyColumn {
        itemsIndexed(items = items) { index, item ->
            if (index + 1 >= items.count()) {
                viewModel.getStories()
            }
            NewsCard(item = item) {
                navController.navigate(Screen.DetailScreen.route + "/${item.title}")
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun NewsCard(item: StoryItemDto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp,
        backgroundColor = Color.White,
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Stories by ${item.by}")
            Text(item.title, style = TextStyle(fontWeight = FontWeight.Bold))
            Text("Created at ${item.time}")
        }
    }
}

@Composable
fun DetailScreen() {
    Text("This supposed to be the detail screen title", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp))
}

sealed class Screen(val route: String) {
    object ListScreen: Screen("ListScreen")
    object DetailScreen: Screen("DetailScreen")
}