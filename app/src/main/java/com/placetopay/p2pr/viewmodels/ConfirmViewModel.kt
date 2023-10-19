package com.placetopay.p2pr.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.placetopay.p2pr.BuildConfig
import com.placetopay.p2pr.compose.utils.ButtonLoadingState
import com.placetopay.p2pr.data.buyer.BuyerPackage
import com.placetopay.p2pr.data.buyer.BuyerRepository
import com.placetopay.p2pr.data.cart.PackageRepository
import com.placetopay.p2pr.data.cart.WelcomePackage
import com.placetopay.p2pr.data.checkout.CheckoutRepository
import com.placetopay.p2pr.data.checkout.CheckoutResult
import com.placetopay.p2pr.data.checkout.base.CheckoutAmount
import com.placetopay.p2pr.data.checkout.base.CheckoutAuth
import com.placetopay.p2pr.data.checkout.base.CheckoutDetail
import com.placetopay.p2pr.data.checkout.base.CheckoutPayment
import com.placetopay.p2pr.data.checkout.base.CheckoutTaxes
import com.placetopay.p2pr.data.checkout.payment.CheckoutPaymentRequest
import com.placetopay.p2pr.data.checkout.payment.CheckoutPaymentResponse
import com.placetopay.p2pr.utilities.addHours
import com.placetopay.p2pr.utilities.getIpAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val buyerRepository: BuyerRepository,
    private val packageRepository: PackageRepository,
    private val checkoutRepository: CheckoutRepository,
) : ViewModel() {
    private val packageId: Int = savedStateHandle.get<Int>(PACKAGE_ID_SAVED_STATE_KEY)!!
    private val buyerId: Int = savedStateHandle.get<Int>(BUYER_ID_SAVED_STATE_KEY)!!

    private val _selectedPackage = MutableLiveData<WelcomePackage>(null)
    val selectedPackage: LiveData<WelcomePackage>
        get() = _selectedPackage

    private val _selectedBuyer = MutableLiveData<BuyerPackage>(null)
    val selectedBuyer: LiveData<BuyerPackage>
        get() = _selectedBuyer

    private val _loadingState = MutableLiveData(ButtonLoadingState(ButtonLoadingState.IDLE))
    val loadingState: LiveData<ButtonLoadingState>
        get() = _loadingState

    private val _paymentResponse = MutableLiveData<CheckoutPaymentResponse>(null)
    val paymentResponse: LiveData<CheckoutPaymentResponse>
        get() = _paymentResponse

    private val _errorResponse = MutableLiveData<String>(null)
    val errorResponse: LiveData<String>
        get() = _errorResponse

    init {
        loadSelectedPackage()
        loadSelectedBuyer()
    }

    // TODO: Simulate the call  to the data source of a use case or repository
    private fun loadSelectedPackage() {
        viewModelScope.launch {
            _selectedPackage.value = packageRepository.getWelcomePackage(packageId = packageId)
        }
    }

    // TODO: Simulate the call  to the data source of a use case or repository
    private fun loadSelectedBuyer() {
        viewModelScope.launch {
            _selectedBuyer.value = buyerRepository.getBuyer(buyerId = buyerId)
        }
    }

    // TODO: Simulate the call  to the data source of a use case or repository
    fun createSession(welcomePackage: WelcomePackage, buyer: BuyerPackage) {
        _loadingState.value = ButtonLoadingState(ButtonLoadingState.LOADING)
        viewModelScope.launch {
            when(val response = checkoutRepository.createPayment(welcomePackage, buyer)) {
                is CheckoutResult.Error -> {
                    _errorResponse.value = response.status?.reason
                    _loadingState.value = ButtonLoadingState(ButtonLoadingState.ERROR)
                }
                is CheckoutResult.Success ->{
                    _paymentResponse.value = response.data
                    _loadingState.value = ButtonLoadingState(ButtonLoadingState.IDLE)
                }
            }
        }
    }

    fun clean() {
        _errorResponse.value = null
        _paymentResponse.value = null
    }


    companion object {
        private const val PACKAGE_ID_SAVED_STATE_KEY = "packageId"
        private const val BUYER_ID_SAVED_STATE_KEY = "buyerId"
    }
}