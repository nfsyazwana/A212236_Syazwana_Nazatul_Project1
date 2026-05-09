package com.example.a212236_syazwana_nazatul_project1

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a212236_syazwana_nazatul_project1.data.Submission
import com.example.a212236_syazwana_nazatul_project1.data.LocationData
import com.example.a212236_syazwana_nazatul_project1.ui.theme.*

@Composable
fun PreviewScreen(
    submission: Submission,
    onConfirmButtonClicked: () -> Unit,
    onEditButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    
    // Calculate points
    val weightVal = submission.weight.toDoubleOrNull() ?: 1.0
    val basePoints = when (submission.itemCategory.lowercase()) {
        "plastic bottles", "plastic", "bottles" -> (10 * weightVal).toInt()
        "glass bottles", "glass" -> (15 * weightVal).toInt()
        "paper", "cardboard", "newspaper" -> (8 * weightVal).toInt()
        "electronic waste", "e-waste", "electronic" -> (25 * weightVal).toInt()
        "used cooking oil" -> (12 * weightVal).toInt()
        "inkjet cartridge" -> (20 * weightVal).toInt()
        else -> (10 * weightVal).toInt()
    }
    val bonusPoints = if (submission.deliveryMethod == "dropoff") 5 else 0
    val pointsEarned = basePoints + bonusPoints

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Points Summary Header
        Surface(
            modifier = Modifier.size(140.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
            shadowElevation = 4.dp
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = pointsEarned.toString(),
                    fontSize = 44.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = fixedPrimary
                )
                Text(
                    text = "EST. POINTS",
                    style = Typography.labelSmall,
                    color = fixedSecondary,
                    letterSpacing = 1.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Submission Summary",
            style = Typography.displayLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Please verify your details before confirming",
            style = Typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Main Details Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = SoftRoundShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                InfoRow(Icons.Default.Category, "Category", submission.itemCategory)
                InfoRow(Icons.Default.Scale, "Estimated Weight", "${submission.weight} kg")
                
                if (submission.itemRemark.isNotBlank()) {
                    InfoRow(Icons.AutoMirrored.Filled.Notes, "Additional Notes", submission.itemRemark)
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), thickness = 0.5.dp, color = fixedPrimary.copy(alpha = 0.2f))

                val (methodName, methodIcon) = if (submission.deliveryMethod == "pickup") {
                    "Home Pickup" to Icons.Default.LocalShipping
                } else {
                    "Self Drop-off" to Icons.Default.LocationOn
                }
                
                InfoRow(methodIcon, "Delivery Method", methodName)

                if (submission.deliveryMethod == "pickup") {
                    InfoRow(Icons.Default.Home, "Pickup Address", submission.deliveryAddress ?: "No address provided")
                } else {
                    submission.selectedLocation?.let { location ->
                        InfoRow(Icons.Default.Store, "Selected Center", location.name)
                        InfoRow(Icons.Default.Place, "Center Address", location.address)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Actions
        Button(
            onClick = onConfirmButtonClicked,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = buttonShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = fixedPrimary,
                contentColor = Color.White
            )
        ) {
            Text("Confirm & Submit", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onEditButtonClicked,
                modifier = Modifier.weight(1f).height(50.dp),
                shape = buttonShape,
                border = BorderStroke(1.dp, fixedPrimary),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = onPrimaryLight
                )
            ) {
                Text("Edit Info")
            }
            
            TextButton(
                onClick = onCancelButtonClicked,
                modifier = Modifier.weight(1f).height(50.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Cancel")
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = GentleShape,
            color = fixedPrimary.copy(alpha = 0.15f)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = fixedPrimary,
                modifier = Modifier.padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = label, style = Typography.labelMedium, color = MaterialTheme.colorScheme.onSurface)
            Text(text = value, style = Typography.bodyLarge, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewScreenCleanPreview() {
    EcoEarnTheme {
        PreviewScreen(
            submission = Submission(
                itemCategory = "Plastic Bottles",
                weight = "2.5",
                itemRemark = "Clean bottles",
                deliveryMethod = "dropoff",
                selectedLocation = LocationData("📍", "Green Center", "123 Eco Way", "8am-5pm")
            ),
            onConfirmButtonClicked = {},
            onEditButtonClicked = {},
            onCancelButtonClicked = {}
        )
    }
}
