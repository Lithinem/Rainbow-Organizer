package de.greiwies.rainbow_organizor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import de.greiwies.rainbow_organizor.components.HideSystemUI
import de.greiwies.rainbow_organizor.components.MainFabWithGrayscaledBackgroundOverlay
import de.greiwies.rainbow_organizor.components.RainbowScaffold
import de.greiwies.rainbow_organizor.demos.DemoTextsWithDifferentStyles
import de.greiwies.rainbow_organizor.demos.EventComposablesDemoParentComposable
import de.greiwies.rainbow_organizor.demos.ScaffoldDemo
import de.greiwies.rainbow_organizor.screens.AlphabeticScrollBar
import de.greiwies.rainbow_organizor.screens.LandingPageContent
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
        MainFabWithGrayscaledBackgroundOverlay()
        RainbowScaffold(viewModel, R.integer.TopBarCodeLandingPage){ paddingValues ->
            //Text("Hallo Welt", Modifier.padding(paddingValues))
            //LandingPageContent(Modifier.padding(paddingValues))
            AlphabeticScrollBar()
        }
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RainbowOrganizorTheme {
        Greeting("Android")
    }
}