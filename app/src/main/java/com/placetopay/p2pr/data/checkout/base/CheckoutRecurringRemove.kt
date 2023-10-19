package com.placetopay.p2pr.data.checkout.base

data class CheckoutRecurringRemove(
    val interval: Int,
    val maxPeriods: Int,
    val nextPayment: String,
    val notificationUrl: String,
    val periodicity: String
)