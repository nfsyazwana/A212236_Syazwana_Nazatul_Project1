package com.example.a212236_syazwana_nazatul_project1

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import com.example.a212236_syazwana_nazatul_project1.data.UserStats
import com.example.a212236_syazwana_nazatul_project1.ui.theme.*
import kotlinx.coroutines.delay

data class RewardItem(
    val id: Int,
    val name: String,
    val description: String,
    val pointsRequired: Int,
    val icon: String,
    val isAvailable: Boolean = true
)

val availableRewards = listOf(
    RewardItem(1, "RM5 Voucher", "GrabFood / Shopee voucher", 500, "🎫"),
    RewardItem(2, "RM10 Voucher", "GrabFood / Shopee voucher", 900, "🎫"),
    RewardItem(3, "Eco Tote Bag", "Reusable shopping bag", 300, "🛍️"),
    RewardItem(4, "Plant Kit", "Start your own garden", 450, "🌱"),
    RewardItem(5, "Reusable Water Bottle", "500ml stainless steel bottle", 600, "💧"),
    RewardItem(6, "RM20 Voucher", "GrabFood / Shopee voucher", 1700, "🎫"),
    RewardItem(7, "Compost Bin", "Small home composter", 800, "🗑️"),
    RewardItem(8, "Solar Charger", "Portable phone charger", 1500, "☀️")
)

@Composable
fun RewardScreen(
    userStats: UserStats,
    onRedeemReward: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }
    var selectedReward by remember { mutableStateOf<RewardItem?>(null) }
    var showConfirmation by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🎁 Rewards Marketplace",
                    style = Typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = SoftRoundShape,
                colors = CardDefaults.cardColors(
                    containerColor = fixedPrimary
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Your Points",
                            style = Typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                        Text(
                            text = "${userStats.totalPoints}",
                            style = Typography.displayLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "eco-points available",
                            style = Typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )
                    }
                    Text(
                        text = "💚",
                        fontSize = 48.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Available Rewards",
                style = Typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(availableRewards) { reward ->
                    RewardCard(
                        reward = reward,
                        userPoints = userStats.totalPoints,
                        onRedeemClick = {
                            selectedReward = reward
                            showConfirmation = true
                        }
                    )
                }
            }
        }

        if (showConfirmation && selectedReward != null) {
            AlertDialog(
                onDismissRequest = { showConfirmation = false },
                title = {
                    Text(
                        text = "Confirm Redemption",
                        style = Typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Column {
                        Text(
                            text = "${selectedReward!!.icon} ${selectedReward!!.name}",
                            style = Typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = selectedReward!!.description,
                            style = Typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Points required: ${selectedReward!!.pointsRequired}",
                            style = Typography.bodyMedium,
                            color = fixedPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Your balance: ${userStats.totalPoints} points",
                            style = Typography.bodySmall
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (userStats.totalPoints >= selectedReward!!.pointsRequired) {
                                onRedeemReward(selectedReward!!.pointsRequired)
                                snackbarMessage = "Successfully redeemed ${selectedReward!!.name}! 🎉"
                                showSnackbar = true
                                showConfirmation = false
                            } else {
                                snackbarMessage = "Insufficient points! You need ${selectedReward!!.pointsRequired - userStats.totalPoints} more points."
                                showSnackbar = true
                                showConfirmation = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = fixedPrimary,
                            contentColor = Color.White
                        ),
                        shape = buttonShape
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = { showConfirmation = false },
                        shape = buttonShape
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }

        if (showSnackbar) {
            LaunchedEffect(showSnackbar) {
                delay(3000)
                showSnackbar = false
            }
            Snackbar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                action = {
                    TextButton(onClick = { showSnackbar = false }) {
                        Text("Dismiss", color = MaterialTheme.colorScheme.inverseOnSurface)
                    }
                },
                containerColor = MaterialTheme.colorScheme.inverseSurface,
                contentColor = MaterialTheme.colorScheme.inverseOnSurface
            ) {
                Text(snackbarMessage)
            }
        }
    }
}

@Composable
fun RewardCard(
    reward: RewardItem,
    userPoints: Int,
    onRedeemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val canAfford = userPoints >= reward.pointsRequired

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(GentleShape),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            if (canAfford) fixedPrimary.copy(alpha = 0.1f)
                            else MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f),
                            RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = reward.icon, fontSize = 32.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = reward.name,
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = reward.description,
                        style = Typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(text = "⭐", fontSize = 14.sp)
                        Text(
                            text = " ${reward.pointsRequired} points",
                            style = Typography.labelSmall,
                            color = if (canAfford) fixedPrimary else MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Button(
                onClick = onRedeemClick,
                enabled = canAfford,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (canAfford) fixedPrimary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (canAfford) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                ),
                shape = buttonShape
            ) {
                Text(if (canAfford) "Redeem" else "Insufficient")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RewardScreenPreview() {
    EcoEarnTheme {
        RewardScreen(
            userStats = UserStats(totalPoints = 1250),
            onRedeemReward = {},
            onBackClick = {}
        )
    }
}
