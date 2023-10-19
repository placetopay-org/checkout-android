package com.placetopay.p2pr.data.receipt

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class ReceiptStatus(
    val title: String,
    @DrawableRes val icon: Int,
    val color: Color,
)