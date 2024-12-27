package com.example.mywishlistapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// abstract has methods that don't need to have execution logic
@Dao
abstract class WishDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addWish(wishEntity: Wish)

    @Query("SELECT * FROM `wish-table`")
    abstract fun getAllWishes(): Flow<List<Wish>> // Flow uses suspend, so we don't need to be explicit about it

    @Update
    abstract suspend fun updateWish(wishEntity: Wish)

    @Delete
    abstract suspend fun deleteWish(wishEntity: Wish)

    @Query("SELECT * FROM `wish-table` WHERE id = :id")
    abstract fun getWishById(id: Long): Flow<Wish> // Flow uses suspend, so we don't need to be explicit about it
}