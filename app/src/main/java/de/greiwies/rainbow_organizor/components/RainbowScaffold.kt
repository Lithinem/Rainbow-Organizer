package de.greiwies.rainbow_organizor.components

import android.widget.Toast
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


@Composable
fun RainbowScaffold(viewModel: RainbowViewModel, topBarCode: Int) {
    Scaffold(
        topBar = {
            if (topBarCode == R.integer.TopBarCodeLandingPage){
                TopAppBarLandingPage()
            }
        },
        content = { paddingValues ->
            Text(
                text = "Inhalt der App",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarLandingPage(){
    val context = LocalContext.current
    val errorText = stringResource(R.string.function_out_of_scope_error)

    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name), color = Color.Black) },
        navigationIcon = {
            IconButton(onClick = { /* TODO: Implement menu (not Part of Project) */
                Toast.makeText(context, errorText,Toast.LENGTH_LONG).show()
            }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.Black)
            }
        },
        actions = {
            IconButton(onClick = { //* TODO: Implement menu (not Part of Project) */
                Toast.makeText(context, errorText,Toast.LENGTH_LONG).show()
            }) {
                Text("?", style = MaterialTheme.typography.labelLarge, color = Color.Black)
            }
            IconButton(onClick = { //* TODO: Implement menu (not Part of Project) */
                Toast.makeText(context, errorText,Toast.LENGTH_LONG).show()
            }) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Black)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            //containerColor = MaterialTheme.colorScheme.primaryContainer
            containerColor = MainColorMain
        )
    )
}