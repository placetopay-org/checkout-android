package com.placetopay.p2pr.data.checkout

import com.placetopay.p2pr.BuildConfig
import com.placetopay.p2pr.api.CheckoutService
import com.placetopay.p2pr.data.buyer.BuyerPackage
import com.placetopay.p2pr.data.cart.WelcomePackage
import com.placetopay.p2pr.data.checkout.base.CheckoutAmount
import com.placetopay.p2pr.data.checkout.base.CheckoutAuth
import com.placetopay.p2pr.data.checkout.base.CheckoutDetail
import com.placetopay.p2pr.data.checkout.base.CheckoutPayment
import com.placetopay.p2pr.data.checkout.base.CheckoutTaxes
import com.placetopay.p2pr.data.checkout.payment.CheckoutInformationRequest
import com.placetopay.p2pr.data.checkout.payment.CheckoutInformationResponse
import com.placetopay.p2pr.data.checkout.payment.CheckoutPaymentRequest
import com.placetopay.p2pr.data.checkout.payment.CheckoutPaymentResponse
import com.placetopay.p2pr.data.common.validateResponse
import com.placetopay.p2pr.utilities.Constants
import com.placetopay.p2pr.utilities.addHours
import com.placetopay.p2pr.utilities.executeSafely
import com.placetopay.p2pr.utilities.getIpAddress
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckoutRepository @Inject constructor(
    private val service: CheckoutService,
) {

    suspend fun createPayment(
        welcomePackage: WelcomePackage,
        buyer: BuyerPackage
    ): CheckoutResult<CheckoutPaymentResponse> {
        welcomePackage.apply {
            details = mutableListOf(
                CheckoutDetail(
                    amount = welcomePackage.standardShipping,
                    kind = "shipping"
                ),
                CheckoutDetail(
                    amount = welcomePackage.subTotalAmount(),
                    kind = "subtotal"
                )
            )
            taxes = mutableListOf(
                CheckoutTaxes(
                    amount = .0,
                    kind = "tax"
                )
            )
        }
        val payment = CheckoutPaymentRequest(
            auth = CheckoutAuth(BuildConfig.loginId, BuildConfig.secretKey),
            expiration = addHours(6),
            locale = "es_CO",
            payment = CheckoutPayment(
                buyer = buyer.buyer,
                items = welcomePackage.items,
                amount = CheckoutAmount(
                    currency = "COP",
                    details = welcomePackage.details,
                    taxes = welcomePackage.taxes,
                    total = welcomePackage.totalAmount(),
                ),
                description = welcomePackage.description,
                reference = welcomePackage.id.toString()
            ),
            ipAddress = getIpAddress(),
            userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36",
            returnUrl = Constants.RETURN_URL,
            cancelUrl = Constants.CANCEL_URL,
        )
        return executeSafely { service.createSession(payment).validateResponse() }
    }

    suspend fun getInformation(requestId: String): CheckoutResult<CheckoutInformationResponse> {
        return executeSafely {
            service.informationSession(
                requestId,
                CheckoutInformationRequest(CheckoutAuth(BuildConfig.loginId, BuildConfig.secretKey))
            ).validateResponse()
        }
    }
}
