package com.placetopay.p2pr.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.placetopay.p2pr.data.checkout.CheckoutRepository
import com.placetopay.p2pr.data.checkout.CheckoutResult
import com.placetopay.p2pr.data.checkout.payment.CheckoutInformationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val checkoutRepository: CheckoutRepository,
) : ViewModel() {

    private val requestId = savedStateHandle.get<String>(REQUEST_URL_SAVED_STATE_KEY)!!

    private val _informationResponse = MutableLiveData<CheckoutInformationResponse>(null)
    val informationResponse: LiveData<CheckoutInformationResponse>
        get() = _informationResponse

    private val _errorResponse = MutableLiveData<String>(null)
    val errorResponse: LiveData<String>
        get() = _errorResponse

    init {
        sessionInformation()
    }

    // TODO: Simulate the call  to the data source of a use case or repository
    fun sessionInformation() {
        viewModelScope.launch {
            clean()
            when (val response = checkoutRepository.getInformation(requestId)) {
                is CheckoutResult.Error -> {
                    _errorResponse.value = response.status?.reason
                }

                is CheckoutResult.Success -> _informationResponse.value = response.data
            }
        }
    }

    fun clean() {
        _errorResponse.value = null
        _informationResponse.value = null
    }

    companion object {
        private const val REQUEST_URL_SAVED_STATE_KEY = "requestId"
    }
}