package com.example.mywishlistapp.data

class WishRepository(private val wishDao: WishDao) {
    suspend fun addWish(wish: Wish) {
        wishDao.addWish(wish)
    }

    fun getWishes() = wishDao.getAllWishes()

    suspend fun updateWish(wish: Wish) {
        wishDao.updateWish(wish)
    }

    suspend fun deleteWish(wish: Wish) {
        wishDao.deleteWish(wish)
    }

    fun getWishById(id: Long) = wishDao.getWishById(id)
}