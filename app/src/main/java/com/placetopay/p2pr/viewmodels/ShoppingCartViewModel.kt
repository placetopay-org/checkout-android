package com.placetopay.p2pr.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.placetopay.p2pr.BuildConfig
import com.placetopay.p2pr.api.CheckoutService
import com.placetopay.p2pr.data.cart.PackageRepository
import com.placetopay.p2pr.data.cart.WelcomePackage
import com.placetopay.p2pr.data.checkout.base.CheckoutAmount
import com.placetopay.p2pr.data.checkout.base.CheckoutAuth
import com.placetopay.p2pr.data.checkout.base.CheckoutPayment
import com.placetopay.p2pr.data.checkout.payment.CheckoutPaymentRequest
import com.placetopay.p2pr.utilities.addHours
import com.placetopay.p2pr.utilities.getIpAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel @Inject internal constructor(
    private val packageRepository: PackageRepository,
) : ViewModel() {

    private val _welcomePackage = MutableLiveData<List<WelcomePackage>>(listOf())
    val welcomePackage: LiveData<List<WelcomePackage>>
        get() = _welcomePackage

    init {
        loadPackages()
    }

    // TODO: Simulate the call  to the data source of a use case or repository
    private fun loadPackages() {
        viewModelScope.launch {
            _welcomePackage.value = packageRepository.getWelcomePackages()
        }
    }
}