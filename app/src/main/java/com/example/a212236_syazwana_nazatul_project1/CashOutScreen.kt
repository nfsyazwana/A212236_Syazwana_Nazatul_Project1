package com.example.a212236_syazwana_nazatul_project1

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a212236_syazwana_nazatul_project1.data.UserStats
import com.example.a212236_syazwana_nazatul_project1.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class CashOutRequest(
    val amount: Int = 0,
    val pointsToUse: Int = 0,
    val recipientName: String = "",
    val bankName: String = "",
    val bankAccount: String = "",
    val email: String = ""
)

val supportedBanks = listOf(
    "Maybank",
    "CIMB Bank",
    "Public Bank",
    "RHB Bank",
    "Hong Leong Bank",
    "AmBank",
    "Bank Islam",
    "Bank Rakyat",
    "Bank Muamalat",
    "OCBC Bank",
    "UOB Bank",
    "Standard Chartered"
)

val cashOutAmounts = listOf(1, 5, 10, 20, 50)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashOutScreen(
    userStats: UserStats,
    onCashOutComplete: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var cashOutRequest by remember { mutableStateOf(CashOutRequest()) }
    var selectedAmount by remember { mutableStateOf<Int?>(null) }
    var expandedBank by remember { mutableStateOf(false) }
    var showConfirmation by remember { mutableStateOf(false) }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val isDarkTheme = isSystemInDarkTheme()

    // Calculate points needed based on selected amount
    val pointsNeeded = (selectedAmount ?: 0) * 100
    val canCashOut = selectedAmount != null &&
            pointsNeeded <= userStats.totalPoints &&
            cashOutRequest.recipientName.isNotBlank() &&
            cashOutRequest.bankName.isNotBlank() &&
            cashOutRequest.bankAccount.isNotBlank() &&
            cashOutRequest.email.isNotBlank() &&
            cashOutRequest.email.contains("@") &&
            cashOutRequest.email.contains(".")

    // Update points when amount changes
    LaunchedEffect(selectedAmount) {
        cashOutRequest = cashOutRequest.copy(
            amount = selectedAmount ?: 0,
            pointsToUse = pointsNeeded
        )
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(
                text = "💰 Cash Out Points",
                style = Typography.displayLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Convert your eco-points to real money",
                style = Typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Points Balance Card
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
                            text = "Your Points Balance",
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
                            text = "points available",
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

            // Conversion Rate Info
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = GentleShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = fixedPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Conversion Rate: 100 points = RM 1",
                        style = Typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = fixedPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Cash Out Form Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = GentleShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = "Cash Out Details",
                        style = Typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = fixedPrimary
                    )

                    // Amount Selection
                    Column {
                        Text(
                            text = "Select Amount (RM)",
                            style = Typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = if (isDarkTheme) Color.White else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            cashOutAmounts.forEach { amount ->
                                AmountChip(
                                    amount = amount,
                                    isSelected = selectedAmount == amount,
                                    onClick = { selectedAmount = amount }
                                )
                            }
                        }
                    }

                    // Selected Amount Preview
                    if (selectedAmount != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = SubtleShape,
                            colors = CardDefaults.cardColors(
                                containerColor = if (pointsNeeded <= userStats.totalPoints)
                                    fixedPrimary.copy(alpha = 0.1f)
                                else
                                    MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = "Amount: RM $selectedAmount",
                                        style = Typography.bodyLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = if (pointsNeeded <= userStats.totalPoints) fixedPrimary else MaterialTheme.colorScheme.error
                                    )
                                    Text(
                                        text = "Points needed: $pointsNeeded",
                                        style = Typography.bodySmall,
                                        color = if (pointsNeeded <= userStats.totalPoints) fixedPrimary else MaterialTheme.colorScheme.error
                                    )
                                }
                                if (pointsNeeded <= userStats.totalPoints) {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = fixedPrimary
                                    )
                                } else {
                                    Icon(
                                        Icons.Default.Warning,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        thickness = 0.5.dp,
                        color = fixedPrimary.copy(alpha = 0.2f)
                    )

                    // Recipient Name
                    OutlinedTextField(
                        value = cashOutRequest.recipientName,
                        onValueChange = { cashOutRequest = cashOutRequest.copy(recipientName = it) },
                        label = { Text("Recipient Full Name") },
                        placeholder = { Text("As per bank account") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = SubtleShape,
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = fixedPrimary,
                            unfocusedBorderColor = outlineLight.copy(alpha = 0.5f)
                        )
                    )

                    // Bank Name Dropdown
                    ExposedDropdownMenuBox(
                        expanded = expandedBank,
                        onExpandedChange = { expandedBank = !expandedBank }
                    ) {
                        OutlinedTextField(
                            value = cashOutRequest.bankName,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Bank Name") },
                            placeholder = { Text("Select your bank") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            shape = SubtleShape,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedBank) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = fixedPrimary,
                                unfocusedBorderColor = outlineLight.copy(alpha = 0.5f)
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = expandedBank,
                            onDismissRequest = { expandedBank = false }
                        ) {
                            supportedBanks.forEach { bank ->
                                DropdownMenuItem(
                                    text = { Text(bank) },
                                    onClick = {
                                        cashOutRequest = cashOutRequest.copy(bankName = bank)
                                        expandedBank = false
                                    }
                                )
                            }
                        }
                    }

                    // Bank Account Number
                    OutlinedTextField(
                        value = cashOutRequest.bankAccount,
                        onValueChange = { cashOutRequest = cashOutRequest.copy(bankAccount = it) },
                        label = { Text("Bank Account Number") },
                        placeholder = { Text("Enter your account number") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = SubtleShape,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = fixedPrimary,
                            unfocusedBorderColor = outlineLight.copy(alpha = 0.5f)
                        )
                    )

                    // Email
                    OutlinedTextField(
                        value = cashOutRequest.email,
                        onValueChange = { cashOutRequest = cashOutRequest.copy(email = it) },
                        label = { Text("Email Address") },
                        placeholder = { Text("For payment confirmation") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = SubtleShape,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = fixedPrimary,
                            unfocusedBorderColor = outlineLight.copy(alpha = 0.5f)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onBackClick,
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = buttonShape,
                    border = BorderStroke(1.dp, fixedPrimary),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = if (isDarkTheme) Color.White else Color.Black
                    )
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        if (canCashOut) {
                            showConfirmation = true
                        }
                    },
                    enabled = canCashOut && !isSubmitting,
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = buttonShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = fixedPrimary,
                        disabledContainerColor = onPrimaryLight.copy(alpha = 0.25f),
                        contentColor = Color.White
                    )
                ) {
                    Text(if (isSubmitting) "Processing..." else "Cash Out")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Confirmation Dialog
        if (showConfirmation && !isSubmitting) {
            AlertDialog(
                onDismissRequest = { showConfirmation = false },
                title = {
                    Text(
                        text = "Confirm Cash Out",
                        style = Typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Column {
                        Text(
                            text = "💰 RM ${selectedAmount}",
                            style = Typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = fixedPrimary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Points to deduct: $pointsNeeded",
                            style = Typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Recipient: ${cashOutRequest.recipientName}",
                            style = Typography.bodySmall
                        )
                        Text(
                            text = "Bank: ${cashOutRequest.bankName}",
                            style = Typography.bodySmall
                        )
                        Text(
                            text = "Account: ${cashOutRequest.bankAccount}",
                            style = Typography.bodySmall
                        )
                        Text(
                            text = "Email: ${cashOutRequest.email}",
                            style = Typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "The amount will be transferred to your bank account within 3-5 business days.",
                            style = Typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showConfirmation = false
                            isSubmitting = true

                            // Deduct points and process cash out
                            onCashOutComplete(pointsNeeded)

                            snackbarMessage = "Cash out request submitted! RM $selectedAmount will be transferred to your account."
                            showSnackbar = true

                            // Reset after 2 seconds and go back
                            scope.launch {
                                delay(2000)
                                isSubmitting = false
                                onBackClick()
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

        // Snackbar for success/error messages
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.AmountChip(
    amount: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()

    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            Text(
                text = "RM $amount",
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color.White else if (isDarkTheme) Color.White else MaterialTheme.colorScheme.onSurface
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = fixedPrimary,
            selectedLabelColor = Color.White,
            containerColor = if (isSelected) fixedPrimary else if (isDarkTheme) Color(0xFF2D2D2D) else MaterialTheme.colorScheme.surface,
            labelColor = if (isDarkTheme) Color.White else MaterialTheme.colorScheme.onSurface
        ),
        border = if (isSelected) null else BorderStroke(1.dp, if (isDarkTheme) Color.Gray else outlineLight.copy(alpha = 0.5f)),
        modifier = Modifier.weight(1f)
    )
}

@Preview(showBackground = true)
@Composable
fun CashOutScreenPreview() {
    EcoEarnTheme {
        CashOutScreen(
            userStats = UserStats(totalPoints = 1250),
            onCashOutComplete = {},
            onBackClick = {}
        )
    }
}