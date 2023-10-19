package com.placetopay.p2pr.utilities

import android.content.Context
import android.util.Base64
import android.view.Gravity
import android.widget.Toast
import com.placetopay.p2pr.data.checkout.CheckoutResult
import com.placetopay.p2pr.data.checkout.base.CheckoutStatus
import com.placetopay.p2pr.utilities.Constants.CHECKOUT_UTC_FORMAT_RESPONSE
import com.placetopay.p2pr.utilities.Constants.STANDARD_DATE_FORMAT
import com.placetopay.p2pr.utilities.Constants.UTF8_FORMAT
import java.io.UnsupportedEncodingException
import java.net.NetworkInterface
import java.net.URLDecoder
import java.net.URLEncoder
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Collections
import java.util.Locale
import java.util.TimeZone

fun addHours(hours: Int): String {
    val currentDate = Calendar.getInstance().apply {
        add(Calendar.HOUR, hours)
    }
    return SimpleDateFormat(Constants.CHECKOUT_UTC_FORMAT, Locale.getDefault()).apply {
        timeZone = Constants.CHECKOUT_TIME_ZONE
    }.format(currentDate.time)
}

fun getIpAddress(): String = try {
    val ipRegex =
        Regex("""^(25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)$""")
    val networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
    var address = ""

    for (networkInterface in networkInterfaces) {
        val inetAddresses = Collections.list(networkInterface.inetAddresses)

        for (inetAddress in inetAddresses) {
            if (!inetAddress.isLoopbackAddress) {
                val hostAddress = inetAddress.hostAddress

                // Check if it's an IPv4
                if (hostAddress != null) {
                    if (hostAddress.matches(ipRegex)) address = hostAddress
                }
            }
        }
    }
    address
} catch (e: Exception) {
    e.printStackTrace()
    ""
}

suspend fun <T> executeSafely(block: suspend () -> CheckoutResult<T>): CheckoutResult<T> = try {
    block()
} catch (_: Exception) {
    CheckoutResult.Error(
        exception = Exception("Server Error, please try again or contact support"),
        status = CheckoutStatus(
            status = Constants.STATUS_FAILED,
            reason = "Error connection",
            message = "Server Error, please try again or contact support",
            date = addHours(0)
        )
    )
}

fun showToastTop(context: Context, text: String) {
    val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
    toast.show()
}

fun String.toMobileFormat(): String =
    this.replace(""""\+(\d{2})(\d{3})(\d{3})(\d{2})(\d{2})""".toRegex(), "+$1 $2-$3 $4 $5")

fun String.toBase64(): String = try {
    val encodedUrl = URLEncoder.encode(this, UTF8_FORMAT)
    Base64.encodeToString(encodedUrl.toByteArray(), Base64.NO_WRAP)
} catch (e: UnsupportedEncodingException) {
    e.printStackTrace()
    ""
}

fun String.fromBase64(): String = try {
    val decodedUrlBytes = Base64.decode(this, Base64.NO_WRAP)
    URLDecoder.decode(String(decodedUrlBytes), UTF8_FORMAT)
} catch (e: UnsupportedEncodingException) {
    e.printStackTrace()
    ""
}


fun Double.toMoneyFormat(): String = DecimalFormat("$ #,##0.00").format(this)

fun String.toDateFormat(): String {
    val originalFormat = SimpleDateFormat(CHECKOUT_UTC_FORMAT_RESPONSE, Locale.getDefault()).apply {
        timeZone = Constants.CHECKOUT_TIME_ZONE
    }
    val date = originalFormat.parse(this)

    val desiredFormat = SimpleDateFormat(STANDARD_DATE_FORMAT, Locale.getDefault())
    desiredFormat.timeZone = TimeZone.getDefault()
    return if (date != null) desiredFormat.format(date) else this
}