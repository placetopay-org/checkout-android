package com.placetopay.p2pr.api

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.placetopay.p2pr.data.checkout.payment.CheckoutInformationRequest
import com.placetopay.p2pr.data.checkout.payment.CheckoutInformationResponse
import com.placetopay.p2pr.data.checkout.payment.CheckoutPaymentRequest
import com.placetopay.p2pr.data.checkout.payment.CheckoutPaymentResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface CheckoutService {

    @POST("/api/session")
    suspend fun createSession(
        @Body payment: CheckoutPaymentRequest,
    ): Response<CheckoutPaymentResponse>

    @POST("/api/session/{requestId}")
    suspend fun informationSession(
        @Path("requestId") requestId: String,
        @Body payment: CheckoutInformationRequest,
    ): Response<CheckoutInformationResponse>

    companion object {
        fun create(context: Context, baseUrl: String): CheckoutService {
            val collector = ChuckerCollector(
                context = context,
                showNotification = true,
                retentionPeriod = RetentionManager.Period.ONE_HOUR
            )
            val logger = ChuckerInterceptor.Builder(context)
                .collector(collector)
                .createShortcut(true)
                .build()

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CheckoutService::class.java)
        }
    }


}