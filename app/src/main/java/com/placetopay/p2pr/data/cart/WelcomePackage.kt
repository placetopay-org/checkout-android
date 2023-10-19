package com.placetopay.p2pr.data.cart

import com.placetopay.p2pr.data.checkout.base.CheckoutDetail
import com.placetopay.p2pr.data.checkout.base.CheckoutItem
import com.placetopay.p2pr.data.checkout.base.CheckoutTaxes

data class WelcomePackage(
    val id: Int,
    val title: String,
    val description: String,
    val items: MutableList<CheckoutItem>? = null,
    var details: MutableList<CheckoutDetail>? = null,
    var taxes: MutableList<CheckoutTaxes>? = null,
) {
    val standardShipping = 15_000.0
    fun totalItems(): Int = items?.sumOf { it.qty } ?: 0
    fun subTotalAmount(): Double = items?.sumOf { it.total() } ?: .0
    fun totalAmount(): Double = details?.sumOf { it.amount } ?: .0
}
