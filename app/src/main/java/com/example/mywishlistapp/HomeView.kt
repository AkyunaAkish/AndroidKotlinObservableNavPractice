package com.example.mywishlistapp

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DismissDirection
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FractionalThreshold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mywishlistapp.data.Wish

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: WishViewModel,
    countObservable: CountObservable
) {
    // Create and remember Observer instances
    val displayObserver = remember { CountDisplayObserver() }
    var displayCount by remember { mutableIntStateOf(0) }

    // Register the observer when the composable is created
    DisposableEffect(countObservable) {
        countObservable.registerObserver(displayObserver)

        // Update the display count whenever the observer receives an update
        val observer = object : CounterObserver {
            override fun onCountUpdated(count: Int) {
                displayCount = count
            }
        }

        countObservable.registerObserver(observer)

        onDispose {
            countObservable.removeObserver(displayObserver)
            countObservable.removeObserver(observer)
        }
    }

    Scaffold(
        topBar = {
            Column {
                Button(
                    onClick = {
                        navController.navigate(Screen.CountScreen.route)
                    }) {
                    Text("Count")
                }
                AppBarView(
                    title = "Wishlist (observable count: $displayCount)"
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddScreen.route + "/0L") // 0L means we're adding something new rather than updating
                },
                contentColor = Color.White,
                containerColor = colorResource(id = R.color.app_bar_color),
                modifier = Modifier.padding(all = 20.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        val wishlist = viewModel.getAllWishes.collectAsState(initial = listOf())

        @Composable
        fun WishItem(wish: Wish, onClick: () -> Unit) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                    .clickable {
                        onClick()
                    },
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                colors = CardColors(
                    contentColor = colorResource(id = R.color.black),
                    containerColor = colorResource(id = R.color.white),
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black
                )

            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
                    Text(text = wish.description)
                }
            }
        }

        Column {
            Button(
                onClick = {
                    navController.navigate(Screen.CountScreen.route)
                }) {
                Text("Count")
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                items(wishlist.value, key = { wish -> wish.id }) { wish ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                viewModel.deleteWish(wish)
                            }

                            true
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            val color by animateColorAsState(
                                if (dismissState.dismissDirection == DismissDirection.EndToStart) {
                                    Color.Red
                                } else {
                                    Color.Transparent
                                },
                                label = ""
                            )

                            val alignment = Alignment.CenterEnd

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(
                                        top = 8.dp,
                                        start = 8.dp,
                                        end = 8.dp
                                    )
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(color),
                                contentAlignment = alignment
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .padding(
                                            end = 8.dp
                                        )
                                )
                            }
                        },
                        directions = setOf(DismissDirection.EndToStart),
                        dismissThresholds = { FractionalThreshold(0.50f) },
                        dismissContent = {
                            WishItem(wish = wish) {
                                navController.navigate(Screen.AddScreen.route + "/${wish.id}")
                            }
                        }
                    )
                }
            }
        }
    }
}
