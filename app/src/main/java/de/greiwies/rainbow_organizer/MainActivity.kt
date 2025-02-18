package de.greiwies.rainbow_organizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.greiwies.rainbow_organizer.components.MainFabWithGrayscaledBackgroundOverlay
import de.greiwies.rainbow_organizer.components.RainbowScaffold
import de.greiwies.rainbow_organizer.screens.DetailsScreen
import de.greiwies.rainbow_organizer.screens.SeriesScreen
import de.greiwies.rainbow_organizer.screens.LandingPage
import de.greiwies.rainbow_organizer.ui.theme.RainbowOrganizerTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: RainbowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RainbowViewModel::class.java)

        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            LocalDemoArea(viewModel)
            //RainbowOrganizorTheme {
            //    //HideSystemUI()
            //    MainFabWithGrayscaledBackgroundOverlay()
            //    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            //        Greeting(
            //            name = "Android",
            //            modifier = Modifier.padding(innerPadding)
            //        )
            //    }
            //}
        }
    }
}

//TODO: Migrate into Rainbow-Organizer Project after finish
@Composable
fun LocalDemoArea(viewModel: RainbowViewModel){
    RainbowOrganizerTheme {
        AppNavigation(viewModel)
        //EventComposablesDemoParentComposable(viewModel)
    }
}

@Composable
fun AppNavigation(viewModel: RainbowViewModel) {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = "landing_page") {
            composable("landing_page") {
                MainFabWithGrayscaledBackgroundOverlay()
                RainbowScaffold(viewModel, R.integer.TopBarCode_LandingPage){ _ ->
                    //Text("Hallo Welt", Modifier.padding(paddingValues))
                    //LandingPageContent(Modifier.padding(paddingValues))
                    LandingPage(viewModel)
                }
            }
            composable("series") {
                RainbowScaffold(viewModel, R.integer.TopBarCode_SeriesPage) {
                    SeriesScreen(viewModel)
                }
            }
            composable("bookDetails") {
                RainbowScaffold(viewModel, R.integer.TopBarCode_DetailsPage) {
                    DetailsScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RainbowOrganizerTheme {
        Greeting("Android")
    }
}