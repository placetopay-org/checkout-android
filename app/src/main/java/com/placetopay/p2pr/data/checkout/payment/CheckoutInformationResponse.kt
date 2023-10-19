package com.placetopay.p2pr.data.checkout.payment

import com.placetopay.p2pr.data.checkout.base.CheckoutStatus

data class CheckoutInformationResponse(
    val status: CheckoutStatus,
    val requestId: String,
    val request: CheckoutPaymentRequest,
)