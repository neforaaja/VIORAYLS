package com.viorayls.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.viorayls.app.R

val Inter = Typography(
    titleLarge = TextStyle(fontFamily = FontFamily(Font(R.font.inter_bold)), fontSize = 20.sp, fontWeight = FontWeight.Bold),
    titleMedium = TextStyle(fontFamily = FontFamily(Font(R.font.inter_bold)), fontSize = 16.sp, fontWeight = FontWeight.Bold),
    bodyLarge = TextStyle(fontFamily = FontFamily(Font(R.font.inter_regular)), fontSize = 16.sp),
    bodyMedium = TextStyle(fontFamily = FontFamily(Font(R.font.inter_regular)), fontSize = 14.sp),
    labelLarge = TextStyle(fontFamily = FontFamily(Font(R.font.inter_medium)), fontSize = 14.sp, fontWeight = FontWeight.Medium),
    displayLarge = TextStyle(fontFamily = FontFamily(Font(R.font.inter_bold)), fontSize = 48.sp, fontWeight = FontWeight.Bold)
)
