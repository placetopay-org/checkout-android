package com.placetopay.p2pr.data.checkout.base

data class CheckoutAmount(
    val currency: String,
    val details: List<CheckoutDetail>? = null,
    val taxes: List<CheckoutTaxes>? = null,
    val total: Double
)