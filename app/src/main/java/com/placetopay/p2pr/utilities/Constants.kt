package com.placetopay.p2pr.utilities

import java.util.TimeZone

object Constants {
    const val STATUS_APPROVED = "APPROVED"
    const val STATUS_FAILED = "FAILED"
    const val STATUS_REJECTED = "REJECTED"
    const val STATUS_CANCEL = "CANCEL"
    const val STATUS_PENDING = "PENDING"

    val CHECKOUT_TIME_ZONE: TimeZone = TimeZone.getTimeZone("America/Bogota")
    const val CHECKOUT_UTC_FORMAT = "yyyy-MM-dd'T'HH:mmZ"
    const val CHECKOUT_UTC_FORMAT_RESPONSE = "yyyy-MM-dd'T'HH:mm:ssXXX"
    const val STANDARD_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val UTF8_FORMAT = "UTF-8"

    const val CANCEL_URL = "https://docs.placetopay.dev/?cancel"
    const val RETURN_URL = "https://docs.placetopay.dev/?return"
}