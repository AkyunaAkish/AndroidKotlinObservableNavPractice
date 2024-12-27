package com.example.mywishlistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mywishlistapp.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
) {
    val snackMessage = remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()

    val title =
        if (id != 0L) stringResource(R.string.update_wish) else stringResource(R.string.add_wish)

    if (id != 0L) {
        // prepopulate fields if ID is populated when visiting this page
        val wish = viewModel.getWishById(id).collectAsState(initial = Wish(0L, "", ""))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
    } else {
        // reset fields if ID is not populated when visiting this page
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarView(
                title = title,
                onBackNavClicked = { navController.navigateUp() })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            WishTextField(
                label = "Title",
                value = viewModel.wishTitleState,
                onValueChanged = { viewModel.onWishTitleChanged(it) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            WishTextField(
                label = "Description",
                value = viewModel.wishDescriptionState,
                onValueChanged = { viewModel.onWishDescriptionChanged(it) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    if (
                        viewModel.wishTitleState.isNotEmpty() &&
                        viewModel.wishDescriptionState.isNotEmpty()
                    ) {
                        if (id != 0L) {
                            viewModel.updateWish(
                                Wish(
                                    id = id,
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim()
                                )
                            )

                            snackMessage.value = "Wish has been updated"
                        } else {
                            viewModel.addWish(
                                Wish(
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim()
                                )
                            )

                            snackMessage.value = "Wish has been created"
                        }
                    } else {
                        snackMessage.value = "Enter values to create a wish"
                    }

                    // return to home screen
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                        navController.navigateUp()
                    }
                }
            ) {
                val buttonText = if (id != 0L) R.string.update_wish else R.string.add_wish
                Text(
                    text = stringResource(id = buttonText),
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
            }
        }
    }
}

@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label, color = Color.Black) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            focusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black,
            cursorColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            unfocusedTextColor = Color.Black
        )
    )
}

@Preview(showBackground = true)
@Composable
fun WishTextFieldPreview() {
    WishTextField(label = "Title", value = "Hello", onValueChanged = {})
}