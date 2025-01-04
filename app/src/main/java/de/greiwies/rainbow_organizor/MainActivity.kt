package de.greiwies.rainbow_organizor

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
import de.greiwies.rainbow_organizor.components.MainFabWithGrayscaledBackgroundOverlay
import de.greiwies.rainbow_organizor.components.RainbowScaffold
import de.greiwies.rainbow_organizor.screens.SeriesScreen
import de.greiwies.rainbow_organizor.screens.LandingPage
import de.greiwies.rainbow_organizor.ui.theme.RainbowOrganizorTheme

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

//TODO: Migrate into Joshuas Project after finish
@Composable
fun LocalDemoArea(viewModel: RainbowViewModel){
    RainbowOrganizorTheme {
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
    RainbowOrganizorTheme {
        Greeting("Android")
    }
}