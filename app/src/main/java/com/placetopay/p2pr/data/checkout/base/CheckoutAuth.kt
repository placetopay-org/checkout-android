package com.placetopay.p2pr.data.checkout.base

import android.os.Build
import com.google.gson.annotations.Expose
import com.placetopay.p2pr.utilities.Constants.CHECKOUT_TIME_ZONE
import com.placetopay.p2pr.utilities.Constants.CHECKOUT_UTC_FORMAT
import java.math.BigInteger
import java.security.MessageDigest
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Calendar
import java.util.Locale

data class CheckoutAuth(
    val login: String,
    @Expose(serialize = false)
    private val secretKey: String
) {
    private var nonce: String
    private var seed: String
    private var tranKey: String

    init {
        requireNotNull(login.isNotEmpty()) { "No login provided on authentication" }
        requireNotNull(secretKey.isNotEmpty()) { "No tranKey provided on authentication" }
        val nonceTemp = secureRandom()
        seed = dateInISO()
        tranKey = toBase64(toSHA256(nonceTemp + seed + secretKey))
        nonce = toBase64(nonceTemp.encodeToByteArray())
    }

    private fun secureRandom(): String = BigInteger(130, SecureRandom()).toString()

    private fun dateInISO(): String =
        SimpleDateFormat(CHECKOUT_UTC_FORMAT, Locale.getDefault()).apply {
            timeZone = CHECKOUT_TIME_ZONE
        }.format(Calendar.getInstance().time)

    private fun toBase64(input: ByteArray): String {
        val encodedBytes: ByteArray =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                Base64.getEncoder().encode(input)
            else android.util.Base64.encode(input, android.util.Base64.NO_WRAP)

        return String(encodedBytes)
    }

    private fun toSHA256(input: String): ByteArray =
        MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
}