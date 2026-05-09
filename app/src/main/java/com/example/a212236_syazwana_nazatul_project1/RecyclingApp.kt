package com.example.a212236_syazwana_nazatul_project1

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.a212236_syazwana_nazatul_project1.ui.theme.fixedPrimary
import com.example.a212236_syazwana_nazatul_project1.ui.theme.EcoEarnTheme

enum class RecyclingScreen(@StringRes val title: Int) {
    Main(R.string.app_name),
    Form(R.string.submit_recycling),
    Preview(R.string.preview_submission),
    Reward(R.string.reward),
    CashOut(R.string.cash_out)
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecyclingAppBar(
    currentScreen: RecyclingScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(currentScreen.title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimaryContainer else fixedPrimary,
            navigationIconContentColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimaryContainer else fixedPrimary
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}


@Preview(showBackground = true, name = "Light Mode")
@Composable
fun RecyclingAppLightPreview() {
    EcoEarnTheme(darkTheme = false) {
        RecyclingApp()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun RecyclingAppDarkPreview() {
    EcoEarnTheme(darkTheme = true) {
        RecyclingApp()
    }
}