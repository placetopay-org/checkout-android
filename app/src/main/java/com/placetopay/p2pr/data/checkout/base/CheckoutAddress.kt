package com.placetopay.p2pr.data.checkout.base

data class CheckoutAddress(
    val city: String,
    val country: String,
    val phone: String,
    val postalCode: String,
    val state: String,
    val street: String
) {
    override fun toString(): String {
        return "$street - $state $postalCode - $city $country"
    }
}