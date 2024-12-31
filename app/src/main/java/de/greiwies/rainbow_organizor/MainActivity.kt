package de.greiwies.rainbow_organizor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.greiwies.rainbow_organizor.components.MainFabWithGrayscaledBackgroundOverlay
import de.greiwies.rainbow_organizor.components.RainbowScaffold
import de.greiwies.rainbow_organizor.screens.PageHomepage
import de.greiwies.rainbow_organizor.screens.DetailsScreen
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
        //RainbowScaffold(viewModel, R.integer.TopBarCodeLandingPage){ paddingValues ->
        //    //Text("Hallo Welt", Modifier.padding(paddingValues))
        //    //LandingPageContent(Modifier.padding(paddingValues))
        //    PageHomepage()
        //}
        //EventComposablesDemoParentComposable(viewModel)
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

@Composable
fun AppNavigation(viewModel: RainbowViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            PageHomepage(navController, viewModel)
        }
        composable("details/{item}") { backStackEntry ->
            val item = backStackEntry.arguments?.getString("item")
            DetailsScreen(item)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RainbowOrganizorTheme {
        Greeting("Android")
    }
}