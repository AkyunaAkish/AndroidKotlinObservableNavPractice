package com.example.mywishlistapp

import android.content.Context
import androidx.room.Room
import com.example.mywishlistapp.data.WishDatabase
import com.example.mywishlistapp.data.WishRepository


// dependency injection set up
// used to create a singleton in an application
// graph object is a service locator to provide dependencies to the app
object Graph {
    private lateinit var database: WishDatabase

    // by lazy makes sure this var is initialized once it's needed
    // rather than loading the repo no matter what. It will only initialize once and is thread safe
    val wishRepository by lazy {
        WishRepository(database.wishDao())
    }

    // room db builder creates an instance of our wish DB to be used throughout the codebase
    fun provide(context: Context) {
        database = Room.databaseBuilder(context, WishDatabase::class.java, "wishlist.db").build()
    }
}