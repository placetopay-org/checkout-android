package com.placetopay.p2pr.data.checkout.base

data class CheckoutItem(
    val category: String,
    val name: String,
    val price: Int,
    val qty: Int,
    val sku: Int,
    val tax: Double
) {
    fun total(): Double = (price * qty).toDouble()
}