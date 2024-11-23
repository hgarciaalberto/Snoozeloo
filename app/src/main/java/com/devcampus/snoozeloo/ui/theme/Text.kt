package com.devcampus.snoozeloo.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.devcampus.snoozeloo.R

val fontStyle52Medium by lazy {
    TextStyle(
        fontSize = 52.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 22.sp,
        fontFamily = FontFamily(Font(R.font.montserrat_medium))
    )
}

val fontStyle32Medium by lazy {
    TextStyle(
        fontSize = 32.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily(Font(R.font.montserrat_medium))
    )
}

val fontStyle14Medium by lazy {
    TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 22.sp,
        fontFamily = FontFamily(Font(R.font.montserrat_medium))
    )
}

val fontStyle16SemiBold by lazy {
    TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 22.sp,
        fontFamily = FontFamily(Font(R.font.montserrat_medium))
    )
}