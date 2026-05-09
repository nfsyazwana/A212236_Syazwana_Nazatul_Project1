package com.example.a212236_syazwana_nazatul_project1

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.unit.dp
import com.example.a212236_syazwana_nazatul_project1.data.locations
import com.example.a212236_syazwana_nazatul_project1.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmissionFormScreen(
    onNextButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    viewModel: RecyclingViewModel,
    modifier: Modifier = Modifier
) {
    val submission by viewModel.currentSubmission.collectAsState()
    val scrollState = rememberScrollState()
    var expanded by remember { mutableStateOf(false) }

    val isFormValid = submission.itemCategory.isNotBlank() &&
            submission.weight.isNotBlank() &&
            submission.weight.toDoubleOrNull() != null &&
            (if (submission.deliveryMethod == "pickup") !submission.deliveryAddress.isNullOrBlank()
            else submission.selectedLocation != null)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium))
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Recycling Submission",
            style = Typography.displayLarge,
            color = MaterialTheme.colorScheme.primary
        )

        // Item Details Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = GentleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Item Details", style = Typography.titleLarge, fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = submission.itemCategory,
                    onValueChange = { /* Fixed category */ },
                    readOnly = true,
                    enabled = false,
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = SubtleShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledContainerColor = Color.Transparent
                    )
                )

                OutlinedTextField(
                    value = submission.weight,
                    onValueChange = { viewModel.updateCurrentSubmission(submission.copy(weight = it)) },
                    label = { Text("Weight (kg)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = SubtleShape,
                    trailingIcon = { Text("kg", modifier = Modifier.padding(end = 8.dp)) }
                )

                OutlinedTextField(
                    value = submission.itemRemark,
                    onValueChange = { viewModel.updateCurrentSubmission(submission.copy(itemRemark = it)) },
                    label = { Text("Remarks") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = SubtleShape
                )
            }
        }

        // Delivery Method Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = GentleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Delivery Method", style = Typography.titleLarge, fontWeight = FontWeight.Bold)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    DeliveryOptionCard(
                        title = "Pick Up",
                        icon = Icons.Default.LocalShipping,
                        isSelected = submission.deliveryMethod == "pickup",
                        onClick = { viewModel.updateCurrentSubmission(submission.copy(deliveryMethod = "pickup")) },
                        modifier = Modifier.weight(1f)
                    )

                    DeliveryOptionCard(
                        title = "Drop Off",
                        icon = Icons.Default.LocationOn,
                        isSelected = submission.deliveryMethod == "dropoff",
                        onClick = { viewModel.updateCurrentSubmission(submission.copy(deliveryMethod = "dropoff")) },
                        modifier = Modifier.weight(1f)
                    )
                }

                if (submission.deliveryMethod == "pickup") {
                    OutlinedTextField(
                        value = submission.deliveryAddress ?: "",
                        onValueChange = { viewModel.updateCurrentSubmission(submission.copy(deliveryAddress = it)) },
                        label = { Text("Pickup Address") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = SubtleShape
                    )
                } else if (submission.deliveryMethod == "dropoff") {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = submission.selectedLocation?.name ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Recycling Center") },
                            modifier = Modifier.fillMaxWidth().menuAnchor(),
                            shape = SubtleShape,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            locations.forEach { location ->
                                DropdownMenuItem(
                                    text = { Text(location.name) },
                                    onClick = {
                                        viewModel.updateCurrentSubmission(submission.copy(selectedLocation = location))
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onCancelButtonClicked,
                modifier = Modifier.weight(1f).height(50.dp),
                shape = buttonShape,
                border = BorderStroke(1.dp, fixedPrimary),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = onPrimaryLight)
            ) {
                Text("Cancel")
            }

            Button(
                onClick = onNextButtonClicked,
                enabled = isFormValid,
                modifier = Modifier.weight(1f).height(50.dp),
                shape = buttonShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = fixedPrimary,
                    disabledContainerColor = onPrimaryLight.copy(alpha = 0.25f)
                )
            ) {
                Text("Next")
            }
        }
    }
}

@Composable
fun DeliveryOptionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .height(80.dp),
        shape = GentleShape,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) fixedPrimary.copy(alpha = 0.15f) else surfaceLight
        ),
        border = if (isSelected) {
            androidx.compose.foundation.BorderStroke(2.dp, fixedPrimary)
        } else {
            androidx.compose.foundation.BorderStroke(1.dp, outlineLight.copy(alpha = 0.5f))
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (isSelected) fixedPrimary else onSurfaceVariantLight,
                modifier = Modifier.size(28.dp)
            )
            Text(
                title,
                style = Typography.bodyMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) fixedPrimary else onSurfaceVariantLight
            )
        }
    }
}
