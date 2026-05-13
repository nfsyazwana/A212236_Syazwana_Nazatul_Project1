package com.example.a212236_syazwana_nazatul_project1

import androidx.lifecycle.ViewModel
import com.example.a212236_syazwana_nazatul_project1.data.Submission
import com.example.a212236_syazwana_nazatul_project1.data.UserStats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RecyclingViewModel : ViewModel() {

    private val _currentSubmission = MutableStateFlow(Submission())
    val currentSubmission: StateFlow<Submission> = _currentSubmission.asStateFlow()

    private val _userStats = MutableStateFlow(UserStats())
    val userStats: StateFlow<UserStats> = _userStats.asStateFlow()

    fun updateCurrentSubmission(submission: Submission) {
        _currentSubmission.value = submission
    }

    fun saveSubmission(submission: Submission) {
        val weightVal = submission.weight.toDoubleOrNull() ?: 1.0
        val basePoints = when (submission.itemCategory.lowercase()) { //calculation
            "plastic bottles", "plastic", "bottles" -> (10 * weightVal).toInt()
            "glass bottles", "glass" -> (15 * weightVal).toInt()
            "paper", "cardboard", "newspaper" -> (8 * weightVal).toInt()
            "electronic waste", "e-waste", "electronic" -> (25 * weightVal).toInt()
            "used cooking oil" -> (12 * weightVal).toInt()
            "inkjet cartridge" -> (20 * weightVal).toInt()
            else -> (10 * weightVal).toInt()
        }

        val bonusPoints = if (submission.deliveryMethod == "dropoff") 5 else 0
        val points = basePoints + bonusPoints

        val submissionWithPoints = submission.copy(pointsEarned = points)

        _userStats.update { currentStats ->
            currentStats.copy(
                totalPoints = currentStats.totalPoints + points,
                recycledItems = currentStats.recycledItems + 1,
                submissions = currentStats.submissions + submissionWithPoints
            )
        }
        _currentSubmission.value = Submission()
    }

    fun resetSubmission() {
        _currentSubmission.value = Submission()
    }

    fun redeemReward(pointsToDeduct: Int) {
        _userStats.update { currentStats ->
            if (currentStats.totalPoints >= pointsToDeduct) {
                currentStats.copy(
                    totalPoints = currentStats.totalPoints - pointsToDeduct
                )
            } else {
                currentStats
            }
        }
    }
}