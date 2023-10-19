package com.placetopay.p2pr.data.checkout.payment

import com.placetopay.p2pr.data.checkout.base.CheckoutAuth

data class CheckoutInformationRequest(
    val auth: CheckoutAuth,
)