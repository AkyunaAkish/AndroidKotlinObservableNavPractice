package com.example.mywishlistapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywishlistapp.data.Wish
import com.example.mywishlistapp.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(
    private val wishRepository: WishRepository = Graph.wishRepository

) : ViewModel() {
    var wishTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")

    fun onWishTitleChanged(newString: String) {
        wishTitleState = newString
    }

    fun onWishDescriptionChanged(newString: String) {
        wishDescriptionState = newString
    }

    // Late init  because we don't want to initialize it until we need it (important for async variables)
    lateinit var getAllWishes: Flow<List<Wish>>

    init {
        // launch allows for async code to run in a coroutine
        viewModelScope.launch(Dispatchers.IO) {
            getAllWishes = wishRepository.getWishes()
        }
    }

    fun addWish(wish: Wish) {
        // Dispatchers.IO - input/output
        // this optimizes our operations for blocking the thread
        // this will be separate from the main thread so that we don't degrade performance
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.addWish(wish)
        }
    }

    fun updateWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.updateWish(wish)
        }
    }

    fun getWishById(id: Long): Flow<Wish> {
        return wishRepository.getWishById(id)
    }

    fun deleteWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.deleteWish(wish)
        }
    }

}