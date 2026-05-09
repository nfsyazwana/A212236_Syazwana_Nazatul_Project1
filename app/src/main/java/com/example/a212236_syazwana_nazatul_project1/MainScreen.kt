package com.example.a212236_syazwana_nazatul_project1

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a212236_syazwana_nazatul_project1.data.locations
import com.example.a212236_syazwana_nazatul_project1.data.LocationData
import com.example.a212236_syazwana_nazatul_project1.data.UserStats
import com.example.a212236_syazwana_nazatul_project1.ui.theme.*

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    EcoEarnTheme {
        Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0F5238).copy(alpha = 0.7f))) {
            MainScreen(
                userStats = UserStats(
                    totalPoints = 1250,
                    recycledItems = 15,
                    donatedItems = 8
                ),
                onNavigateToForm = {},
                onRewardClick = {},
                onCashOutClick = {}
            )
        }
    }
}

@Composable
fun MainScreen(
    userStats: UserStats,
    onNavigateToForm: (String) -> Unit,
    onRewardClick: () -> Unit = {},
    onCashOutClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current
    var locationInput by remember { mutableStateOf("") }
    var searchedLocation by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        WelcomeSection()

        PointBalance(
            totalPoints = userStats.totalPoints,
            onRewardClick = onRewardClick,
            onCashOutClick = onCashOutClick
        )

        PointsBreakdown(
            recycledCount = userStats.recycledItems,
            donatedCount = userStats.donatedItems
        )

        Spacer(modifier = Modifier.height(16.dp))

        RecycleCategories(onCategoryClick = onNavigateToForm)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "View Nearby Recycling Centre",
                style = Typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(R.dimen.padding_medium)),
                shape = SoftRoundShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.9f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                ) {
                    Text(
                        text = "Find recycling centers near you",
                        style = Typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
                    )

                    OutlinedTextField(
                        value = locationInput,
                        onValueChange = { locationInput = it },
                        label = { Text("Enter location", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)) },
                        placeholder = { Text("e.g., Kuala Lumpur, Selangor, 43650...", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = Typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = SubtleShape
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                    Button(
                        onClick = {
                            if (locationInput.isNotBlank()) {
                                searchedLocation = locationInput
                                isSubmitted = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = fixedPrimary
                        ),
                        shape = buttonShape
                    ) {
                        Text(
                            text = "Search Locations",
                            style = Typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    if (isSubmitted && searchedLocation.isNotBlank()) {
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.9f)
                            ),
                            shape = GentleShape
                        ) {
                            Column(
                                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                            ) {
                                Text(
                                    text = "📍 Searching for recycling centers near:",
                                    style = Typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                                Text(
                                    text = searchedLocation,
                                    style = Typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(R.dimen.padding_small))
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                locations.forEach { location ->
                    LocationCard(location = location)
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun WelcomeSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "👤", fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))

        Column {
            Text(
                text = "Welcome Back",
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Eco Warrior",
                style = Typography.displayLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun PointBalance(
    totalPoints: Int,
    onRewardClick: () -> Unit = {},
    onCashOutClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium), vertical = dimensionResource(R.dimen.padding_small)),
        shape = SoftRoundShape,
        colors = CardDefaults.cardColors(
            containerColor = fixedPrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Point Balance",
                    style = Typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
                Text(
                    text = totalPoints.toString(),
                    style = Typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f), buttonShape)
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium), vertical = dimensionResource(R.dimen.padding_small))
                        .clickable { onRewardClick() },
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "🎁", fontSize = 14.sp)
                    Text(
                        text = "Reward",
                        style = Typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f), buttonShape)
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium), vertical = dimensionResource(R.dimen.padding_small))
                        .clickable { onCashOutClick() },
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "💰", fontSize = 14.sp)
                    Text(
                        text = "Cash-out",
                        style = Typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

@Composable
fun PointsBreakdown(recycledCount: Int, donatedCount: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.recycle),
                contentDescription = "Recycled",
                modifier = Modifier.size(50.dp),
                tint = Color.Unspecified
            )
            Text(
                text = recycledCount.toString(),
                style = Typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Item",
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = "RECYCLED",
                style = Typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.gift),
                contentDescription = "Donated",
                modifier = Modifier.size(50.dp),
                tint = Color.Unspecified
            )
            Text(
                text = donatedCount.toString(),
                style = Typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Item",
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = "DONATED",
                style = Typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun RecycleCategories(onCategoryClick: (String) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium), vertical = dimensionResource(R.dimen.padding_small))
    ) {
        Text(
            text = "Select Item Type",
            style = Typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val categories = listOf("Clothes", "Glass", "Paper", "Bottles", "Newspaper", "Electronic", "Books", "Used Cooking Oil", "Inkjet Cartridge")
            categories.forEach { category ->
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(75.dp)
                        .background(fixedPrimary, GentleShape)
                        .clickable { onCategoryClick(category) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = category,
                        style = Typography.labelSmall,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun LocationCard(location: LocationData, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .width(220.dp)
            .animateContentSize()
            .clickable { expanded = !expanded },
        shape = GentleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.9f)
        )
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = location.icon, fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = location.name,
                    style = Typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Address:",
                    style = Typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = location.address,
                    style = Typography.labelSmall,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Status:",
                    style = Typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = location.openTime,
                    style = Typography.labelSmall,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}