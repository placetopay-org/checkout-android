package com.placetopay.p2pr.data.checkout.payment

import com.placetopay.p2pr.data.checkout.base.CheckoutStatus

data class CheckoutPaymentResponse(
    val status: CheckoutStatus,
    val requestId: String,
    val processUrl: String,
)