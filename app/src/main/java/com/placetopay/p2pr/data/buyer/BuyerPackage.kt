package com.placetopay.p2pr.data.buyer

import com.placetopay.p2pr.data.checkout.base.CheckoutBuyer

data class BuyerPackage(
    val id: Int,
    val title: String,
    val buyer: CheckoutBuyer,
)