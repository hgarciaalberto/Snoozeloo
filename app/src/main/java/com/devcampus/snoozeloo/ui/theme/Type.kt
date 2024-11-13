package com.devcampus.snoozeloo.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.devcampus.snoozeloo.R

val MontserratFontFamily = FontFamily(
    Font(R.font.montserrat_medium),
)

val Typography = Typography(
    displayLarge = Typography().displayLarge.copy(fontFamily = MontserratFontFamily),
    displayMedium = Typography().displayMedium.copy(fontFamily = MontserratFontFamily),
    displaySmall = Typography().displaySmall.copy(fontFamily = MontserratFontFamily),
    headlineLarge = Typography().headlineLarge.copy(fontFamily = MontserratFontFamily),
    headlineMedium = Typography().headlineMedium.copy(fontFamily = MontserratFontFamily),
    headlineSmall = Typography().headlineSmall.copy(fontFamily = MontserratFontFamily),
    titleLarge = Typography().titleLarge.copy(fontFamily = MontserratFontFamily),
    titleMedium = Typography().titleMedium.copy(fontFamily = MontserratFontFamily),
    titleSmall = Typography().titleSmall.copy(fontFamily = MontserratFontFamily),
    bodyLarge = Typography().bodyLarge.copy(fontFamily = MontserratFontFamily),
    bodyMedium = Typography().bodyMedium.copy(fontFamily = MontserratFontFamily),
    bodySmall = Typography().bodySmall.copy(fontFamily = MontserratFontFamily),
    labelLarge = Typography().labelLarge.copy(fontFamily = MontserratFontFamily),
    labelMedium = Typography().labelMedium.copy(fontFamily = MontserratFontFamily),
    labelSmall = Typography().labelSmall.copy(fontFamily = MontserratFontFamily),
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewDefaultMaterialThemeMyTypography() {
    SnoozelooTheme() {
        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
            Text(text = "displayLarge", style = MaterialTheme.typography.displayLarge)
            Text(text = "displayMedium", style = MaterialTheme.typography.displayMedium)
            Text(text = "displaySmall", style = MaterialTheme.typography.displaySmall)
            Text(text = "headlineLarge", style = MaterialTheme.typography.headlineLarge)
            Text(text = "headlineMedium", style = MaterialTheme.typography.headlineMedium)
            Text(text = "headlineSmall", style = MaterialTheme.typography.headlineSmall)
            Text(text = "titleLarge", style = MaterialTheme.typography.titleLarge)
            Text(text = "titleMedium", style = MaterialTheme.typography.titleMedium)
            Text(text = "titleSmall", style = MaterialTheme.typography.titleSmall)
            Text(text = "bodyLarge", style = MaterialTheme.typography.bodyLarge)
            Text(text = "bodyMedium", style = MaterialTheme.typography.bodyMedium)
            Text(text = "bodySmall", style = MaterialTheme.typography.bodySmall)
            Text(text = "labelLarge", style = MaterialTheme.typography.labelLarge)
            Text(text = "labelMedium", style = MaterialTheme.typography.labelMedium)
            Text(text = "labelSmall", style = MaterialTheme.typography.labelSmall)
        }
    }
}
