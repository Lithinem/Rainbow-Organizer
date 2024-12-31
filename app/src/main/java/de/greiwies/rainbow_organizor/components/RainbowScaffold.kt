package de.greiwies.rainbow_organizor.components

import android.content.res.Resources.Theme
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import de.greiwies.rainbow_organizor.R
import de.greiwies.rainbow_organizor.RainbowViewModel
import de.greiwies.rainbow_organizor.ui.theme.MainColorMain
import de.greiwies.rainbow_organizor.ui.theme.RainbowOrganizorTopBarTheme


@Composable
fun RainbowScaffold(viewModel: RainbowViewModel, topBarCode: Int, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        topBar = {
            if (topBarCode == R.integer.TopBarCodeLandingPage){
                TopAppBarLandingPage()
            }
        },
        content = { innerPadding ->
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding))
            {
                content(innerPadding)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarLandingPage(){
    val context = LocalContext.current
    val errorText = stringResource(R.string.function_out_of_scope_error)

    RainbowOrganizorTopBarTheme {
        TopAppBar(
            title = { Text(stringResource(id = R.string.app_name)) },
            navigationIcon = {
                IconButton(onClick = { /* TODO: Implement menu (not Part of Project) */
                    Toast.makeText(context, errorText,Toast.LENGTH_LONG).show()
                }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            },
            actions = {
                IconButton(onClick = { //* TODO: Implement menu (not Part of Project) */
                    Toast.makeText(context, errorText,Toast.LENGTH_LONG).show()
                }) {
                    Text("?", style = MaterialTheme.typography.labelLarge)
                }
                IconButton(onClick = { //* TODO: Implement menu (not Part of Project) */
                    Toast.makeText(context, errorText,Toast.LENGTH_LONG).show()
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                //containerColor = MaterialTheme.colorScheme.primaryContainer
                containerColor = MainColorMain
            )
        )
    }
}

