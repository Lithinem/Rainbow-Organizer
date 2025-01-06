package de.greiwies.rainbow_organizor.components

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.greiwies.rainbow_organizor.R
import de.greiwies.rainbow_organizor.ui.theme.OverlayBackgroundGrayHalfTransparent
import de.greiwies.rainbow_organizor.ui.theme.OverlayTextBlock

@Composable
fun TextBlock(content: String, modifier: Modifier = Modifier){
    val context = LocalContext.current
    val borderThickness = with(LocalDensity.current) {
        context.resources.getInteger(R.integer.BorderThickness).dp
    }
    val outerCornerClipInDp = with(LocalDensity.current) {
        context.resources.getInteger(R.integer.OuterCornerClip).dp
    }


    Box(
        modifier = modifier
            .background(
                color = OverlayTextBlock,
                shape = RoundedCornerShape(outerCornerClipInDp)
            )
            .border(
                width = borderThickness, color = Color.Black,
                shape = RoundedCornerShape(outerCornerClipInDp)
            )
    ) {
        Text(
            text = content,
            modifier = Modifier.padding(outerCornerClipInDp + 5.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun DataField(key: String, value: String? = null, valueList: List<String>? = null, finalSpacerSize: Dp = 2.dp) {
    Column {
        Row {
            Text(
                text = key,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1F),
                textAlign = TextAlign.Start
            )
            Text(
                text = when {
                    value != null -> value
                    valueList != null -> valueList.joinToString(", ")
                    else -> "N/A"
                },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1F),
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.size(2.dp))
        VisibleSeparator()
        Spacer(modifier = Modifier.size(finalSpacerSize))
    }
}