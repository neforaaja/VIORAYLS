package com.viorayls.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.viorayls.app.R

val Inter = Typography(
    titleLarge = TextStyle(fontFamily = FontFamily(Font(R.font.sans_premium)), fontSize = 20.sp, fontWeight = FontWeight.Bold),
    titleMedium = TextStyle(fontFamily = FontFamily(Font(R.font.sans_premium)), fontSize = 16.sp, fontWeight = FontWeight.Bold),
    bodyLarge = TextStyle(fontFamily = FontFamily(Font(R.font.sans_premium)), fontSize = 16.sp),
    bodyMedium = TextStyle(fontFamily = FontFamily(Font(R.font.sans_premium)), fontSize = 14.sp),
    labelLarge = TextStyle(fontFamily = FontFamily(Font(R.font.sans_premium)), fontSize = 14.sp, fontWeight = FontWeight.Medium),
    displayLarge = TextStyle(fontFamily = FontFamily(Font(R.font.sans_premium)), fontSize = 48.sp, fontWeight = FontWeight.Bold)
)
