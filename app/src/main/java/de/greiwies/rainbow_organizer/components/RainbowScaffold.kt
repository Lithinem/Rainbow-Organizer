package de.greiwies.rainbow_organizer.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import de.greiwies.rainbow_organizer.LocalNavController
import de.greiwies.rainbow_organizer.R
import de.greiwies.rainbow_organizer.RainbowViewModel
import de.greiwies.rainbow_organizer.ui.theme.MainColorMain
import de.greiwies.rainbow_organizer.ui.theme.RainbowOrganizerTopBarTheme


//Scaffold MUST be used in each Screen as not doing so will break the MaterialTheme-API.
@Composable
fun RainbowScaffold(viewModel: RainbowViewModel, topBarCode: Int, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        topBar = {
            when {
                (topBarCode == R.integer.TopBarCode_LandingPage) -> TopAppBarLandingPage()
                (topBarCode == R.integer.TopBarCode_SeriesPage) ->  TopAppBarSeriesPage(viewModel)
                (topBarCode == R.integer.TopBarCode_DetailsPage) -> TopAppBarDetailsPage(viewModel)
            }

        },
        content = { innerPadding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding))
            {
                content(innerPadding)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarLandingPage(){
    val context = LocalContext.current
    val errorText = stringResource(R.string.function_out_of_scope_error)

    RainbowOrganizerTopBarTheme {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarSeriesPage(viewModel: RainbowViewModel){
    val context = LocalContext.current
    val errorText = stringResource(R.string.function_out_of_scope_error)
    val navController = LocalNavController.current
        ?: throw IllegalStateException("NavController not found in the CompositionLocal")
    val series = viewModel.demoSeries.get(viewModel.selectedSeriesId).series


    RainbowOrganizerTopBarTheme {
        TopAppBar(
            title = { Text(series) },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { //* TODO: Implement menu (not Part of Project) */
                    Toast.makeText(context, errorText,Toast.LENGTH_LONG).show()
                }) {
                    Text("?", style = MaterialTheme.typography.labelLarge)
                }
                //TODO: Reimplement when Selection is implemented
                /*
                IconButton(onClick = { //* TODO: Implement menu (not Part of Project) */
                    Toast.makeText(context, errorText, Toast.LENGTH_LONG).show()
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.select_all),
                        contentDescription = "Select all Button",
                        modifier = Modifier
                            .size(25.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                */
                // TODO: Implement when necessary. For when '?' is needed rethink layout
                /* IconButton(onClick = { //* TODO: Implement menu (not Part of Project) */
                    Toast.makeText(context, errorText,Toast.LENGTH_LONG).show()
                }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More")
                } */
            },
            colors = TopAppBarDefaults.topAppBarColors(
                //containerColor = MaterialTheme.colorScheme.primaryContainer
                containerColor = MainColorMain
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarDetailsPage(viewModel: RainbowViewModel){
    val context = LocalContext.current
    val errorText = stringResource(R.string.function_out_of_scope_error)
    val navController = LocalNavController.current
        ?: throw IllegalStateException("NavController not found in the CompositionLocal")
    val title = viewModel.demoData.get(viewModel.selectedSeriesId).get(viewModel.selectedBookId).Title

    RainbowOrganizerTopBarTheme {
        TopAppBar(
            title = { Text(title) },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { //* TODO: Implement menu (not Part of Project) */
                    Toast.makeText(context, errorText,Toast.LENGTH_LONG).show()
                }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                //containerColor = MaterialTheme.colorScheme.primaryContainer
                containerColor = MainColorMain
            )
        )
    }
}

