package com.example.a212236_syazwana_nazatul_project1.data

data class RecyclingCenter(
    val id: Int,
    val name: String,
    val address: String,
    val distance: String,
    val icon: String = "📍",
    val openTime: String = "Open 24/7"
)

data class Submission(
    val itemCategory: String = "",
    val quantity: Double = 0.0,
    val weight: String = "",
    val actionType: String = "",
    val locationName: String = "",
    val locationAddress: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val pointsEarned: Int = 0,
    val itemRemark: String = "",
    val deliveryMethod: String = "",
    val deliveryAddress: String? = null,
    val selectedLocation: LocationData? = null
)

data class UserStats(
    val username: String = "User",
    val totalPoints: Int = 0,
    val recycledItems: Int = 0,
    val donatedItems: Int = 0,
    val submissions: List<Submission> = emptyList()
)