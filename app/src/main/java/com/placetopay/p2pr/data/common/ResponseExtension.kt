package com.placetopay.p2pr.data.common

import com.placetopay.p2pr.data.checkout.CheckoutResult
import com.placetopay.p2pr.data.checkout.base.CheckoutStatus
import com.placetopay.p2pr.utilities.Constants
import com.placetopay.p2pr.utilities.addHours
import org.json.JSONObject
import retrofit2.Response

fun <T> Response<T>.validateResponse(): CheckoutResult<T> =
    when {
        isSuccessful -> {
            CheckoutResult.Success(body()!!)
        }

        else -> {
            val error = try {
                val status = JSONObject(errorBody()!!.string()).getJSONObject("status")
                CheckoutStatus(
                    status = status.getString("status"),
                    reason = status.getString("reason"),
                    message = status.getString("message"),
                    date = status.getString("date")
                )
            } catch (_: Exception) {
                CheckoutStatus(
                    status = Constants.STATUS_FAILED,
                    reason = code().toString(),
                    message = "Server Error, please try again or contact support",
                    date = addHours(0)
                )

            }
            CheckoutResult.Error(
                exception = Exception(error.message),
                status = error
            )
        }
    }