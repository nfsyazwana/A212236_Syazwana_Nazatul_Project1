package com.example.a212236_syazwana_nazatul_project1.data

import java.io.Serializable

class RecyclingCenter(
    val id: Int,
    val name: String,
    val address: String,
    val distance: String,
    val icon: String = "📍",
    val openTime: String = "Open 24/7"
) : Serializable {
    fun copy(
        id: Int = this.id,
        name: String = this.name,
        address: String = this.address,
        distance: String = this.distance,
        icon: String = this.icon,
        openTime: String = this.openTime
    ) = RecyclingCenter(id, name, address, distance, icon, openTime)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RecyclingCenter) return false
        return id == other.id && name == other.name && address == other.address && distance == other.distance && icon == other.icon && openTime == other.openTime
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + distance.hashCode()
        result = 31 * result + icon.hashCode()
        result = 31 * result + openTime.hashCode()
        return result
    }
}

class Submission(
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
) : Serializable {
    fun copy(
        itemCategory: String = this.itemCategory,
        quantity: Double = this.quantity,
        weight: String = this.weight,
        actionType: String = this.actionType,
        locationName: String = this.locationName,
        locationAddress: String = this.locationAddress,
        timestamp: Long = this.timestamp,
        pointsEarned: Int = this.pointsEarned,
        itemRemark: String = this.itemRemark,
        deliveryMethod: String = this.deliveryMethod,
        deliveryAddress: String? = this.deliveryAddress,
        selectedLocation: LocationData? = this.selectedLocation
    ) = Submission(
        itemCategory,
        quantity,
        weight,
        actionType,
        locationName,
        locationAddress,
        timestamp,
        pointsEarned,
        itemRemark,
        deliveryMethod,
        deliveryAddress,
        selectedLocation
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Submission) return false
        return itemCategory == other.itemCategory &&
                quantity == other.quantity &&
                weight == other.weight &&
                actionType == other.actionType &&
                locationName == other.locationName &&
                locationAddress == other.locationAddress &&
                timestamp == other.timestamp &&
                pointsEarned == other.pointsEarned &&
                itemRemark == other.itemRemark &&
                deliveryMethod == other.deliveryMethod &&
                deliveryAddress == other.deliveryAddress &&
                selectedLocation == other.selectedLocation
    }

    override fun hashCode(): Int {
        var result = itemCategory.hashCode()
        result = 31 * result + quantity.hashCode()
        result = 31 * result + weight.hashCode()
        result = 31 * result + actionType.hashCode()
        result = 31 * result + locationName.hashCode()
        result = 31 * result + locationAddress.hashCode()
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + pointsEarned
        result = 31 * result + itemRemark.hashCode()
        result = 31 * result + deliveryMethod.hashCode()
        result = 31 * result + (deliveryAddress?.hashCode() ?: 0)
        result = 31 * result + (selectedLocation?.hashCode() ?: 0)
        return result
    }
}

class UserStats(
    val username: String = "User",
    val totalPoints: Int = 0,
    val recycledItems: Int = 0,
    val donatedItems: Int = 0,
    val submissions: List<Submission> = emptyList()
) : Serializable {
    fun copy(
        username: String = this.username,
        totalPoints: Int = this.totalPoints,
        recycledItems: Int = this.recycledItems,
        donatedItems: Int = this.donatedItems,
        submissions: List<Submission> = this.submissions
    ) = UserStats(
        username,
        totalPoints,
        recycledItems,
        donatedItems,
        submissions
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserStats) return false
        return username == other.username &&
                totalPoints == other.totalPoints &&
                recycledItems == other.recycledItems &&
                donatedItems == other.donatedItems &&
                submissions == other.submissions
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + totalPoints
        result = 31 * result + recycledItems
        result = 31 * result + donatedItems
        result = 31 * result + submissions.hashCode()
        return result
    }
}
