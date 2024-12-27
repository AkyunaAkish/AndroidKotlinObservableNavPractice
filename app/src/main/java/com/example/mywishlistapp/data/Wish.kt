package com.example.mywishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "wish-title")
    val title: String = "",
    @ColumnInfo(name = "wish-description")
    val description: String = ""
)

object DummyWish {
    val wishList = listOf(
        Wish(title = "Lute", description = "Bard it up"),
        Wish(title = "Trip to Europe", description = "Explore the lands"),
        Wish(
            title = "Google Pixel 9 Pro", description = "It's fancy y'all"
        ),
        Wish(
            title = "Razer Hammerhead Earbuds", description = "The bass is powerful"
        )
    )
}