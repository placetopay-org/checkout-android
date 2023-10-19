package com.placetopay.p2pr.data.checkout.base

import com.google.gson.annotations.SerializedName

data class CheckoutPayment(
    val buyer: CheckoutBuyer? = null,
    val allowPartial: Boolean = false,
    val amount: CheckoutAmount,
    val description: String,
    val items: List<CheckoutItem>? = null,
    @SerializedName("recurring_remove")
    val recurringRemove: CheckoutRecurringRemove? = null,
    val reference: String,
    val shipping: CheckoutShipping? = null
)