package com.placetopay.p2pr.data.checkout

import com.placetopay.p2pr.data.checkout.base.CheckoutStatus

sealed class CheckoutResult<out R> {
    data class Success<out T>(val data: T) : CheckoutResult<T>()
    data class Error(val exception: Exception,val status: CheckoutStatus?) : CheckoutResult<Nothing>()
}
