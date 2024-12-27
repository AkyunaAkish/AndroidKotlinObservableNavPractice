package com.example.mywishlistapp

import android.app.Application

class WishlistApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // this causes the graph to be initalized when the app is started up
        Graph.provide(this) // `this` is the context of the app
    }
}