package com.example.mywishlistapp

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarView(
    title: String,
    onBackNavClicked: () -> Unit = {}
) {
    var navigationIcon: @Composable () -> Unit = {
        IconButton(onClick = { onBackNavClicked() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                tint = Color.White,
                contentDescription = null
            )
        }
    }

    if (title.contains("Wishlist")) {
        navigationIcon = {}
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                color = colorResource(id = R.color.white),
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp)
            )
        },
        colors = topAppBarColors(
            containerColor = colorResource(id = R.color.app_bar_color),
            scrolledContainerColor = colorResource(id = R.color.app_bar_color),
        ),
        navigationIcon = navigationIcon
    )
}