package com.placetopay.p2pr.data.checkout.base

data class CheckoutBuyer(
    val address: CheckoutAddress,
    val document: String,
    val documentType: String,
    val email: String,
    val mobile: String,
    val name: String,
    val surname: String
)