package de.greiwies.rainbow_organizor.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// TODO: Overwrite colors
private val DarkColorScheme = darkColorScheme(
    primary = MainColorDark,
    primaryContainer = MainColorDark,

    secondary = SecondaryColorDark,
    secondaryContainer = SecondaryColorDark,

    tertiary = Secondary2ColorDark,
    tertiaryContainer = Secondary2ColorDark,

    error = ContrastColorDark,
    errorContainer = ContrastColorDark,
)

private val LightColorScheme = lightColorScheme(
    primary = MainColorLight,
    primaryContainer = MainColorLight,

    secondary = SecondaryColorLight,
    secondaryContainer = SecondaryColorLight,

    tertiary = Secondary2ColorLight,
    tertiaryContainer = Secondary2ColorLight,

    error = ContrastColorLight,
    errorContainer = ContrastColorLight,

    background = Color.White

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

// For all values refere to https://developer.android.com/reference/kotlin/androidx/compose/material3/Typography
// Use .sp for scaleable pixels. These will refere to user specific setup regarding font size in the OS settings.
private val RainbowTypography = Typography(
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    titleSmall = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal
    ),

    headlineLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    headlineMedium = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal
    ),
    headlineSmall = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),


    bodyLarge = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    bodySmall = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal
    ),

    labelLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
)

@Composable
fun RainbowOrganizorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    //dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        //dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        //    val context = LocalContext.current
        //    if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        //}

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = RainbowTypography,
        content = content
    )
}

@Composable
fun RainbowOrganizorTopBarTheme(
    // Dynamic color is available on Android 12+
    //dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme =  LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = RainbowTypography,
        content = content
    )
}