package com.placetopay.p2pr.data.checkout.base

data class CheckoutStatus(
    val status: String,
    val reason: String,
    val message: String,
    val date: String,
)