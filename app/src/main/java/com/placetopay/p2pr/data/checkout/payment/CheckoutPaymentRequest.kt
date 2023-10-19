package com.placetopay.p2pr.data.checkout.payment

import com.placetopay.p2pr.data.checkout.base.CheckoutAuth
import com.placetopay.p2pr.data.checkout.base.CheckoutBuyer
import com.placetopay.p2pr.data.checkout.base.CheckoutField
import com.placetopay.p2pr.data.checkout.base.CheckoutPayment

data class CheckoutPaymentRequest(
    val auth: CheckoutAuth,
    val buyer: CheckoutBuyer? = null,
    val cancelUrl: String,
    val captureAddress: Boolean = false,
    val expiration: String,
    val fields: List<CheckoutField>? = null,
    val locale: String,
    val noBuyerFill: Boolean = false,
    val payment: CheckoutPayment,
    val returnUrl: String,
    val ipAddress: String,
    val skipResult: Boolean = false,
    val userAgent: String
)