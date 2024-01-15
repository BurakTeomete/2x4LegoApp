package com.example.a2x4legoapp.data

data class UserData(
    var userId: String? = null,
    var name: String? = null,
    var username: String? = null,
    var imageUrl: String? = null,
    var bio: String? = null,
    var following: List<String>? = null,
    var followers: List<String>? = null,
    var coinBalance: Int = 0,
    var currentFigure: String? = null,
    var currentWallpaper: String? = null,
    var purchasedFigures: List<String>? = null,
    var purchasedWallpapers: List<String>? = null
) {
    fun toMap() = mapOf(
        "userId" to userId,
        "name" to name,
        "username" to username,
        "imageUrl" to imageUrl,
        "bio" to bio,
        "following" to following,
        "followers" to followers,
        "coinBalance" to coinBalance,
        "currentFigure" to currentFigure,
        "currentWallpaper" to currentWallpaper,
        "purchasedFigures" to purchasedFigures,
        "purchasedWallpapers" to purchasedWallpapers
    )

    // Coin ekleme
    fun addCoins(amount: Int) {
        coinBalance += amount
    }

    // Coin çıkarma
    fun subtractCoins(amount: Int) {
        if (coinBalance >= amount) {
            coinBalance -= amount
        } else {
            println("Yetersiz bakiye")
        }
    }
}
