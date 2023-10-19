package com.placetopay.p2pr.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.placetopay.p2pr.data.buyer.BuyerPackage
import com.placetopay.p2pr.data.buyer.BuyerRepository
import com.placetopay.p2pr.data.cart.PackageRepository
import com.placetopay.p2pr.data.cart.WelcomePackage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuyerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val packageRepository: PackageRepository,
    private val buyerRepository: BuyerRepository,
): ViewModel() {
    private val packageId: Int = savedStateHandle.get<Int>(PACKAGE_ID_SAVED_STATE_KEY)!!

    private val _buyers = MutableLiveData<List<BuyerPackage>>(listOf())
    val buyers : LiveData<List<BuyerPackage>>
        get() = _buyers

    private val _selectedPackage = MutableLiveData<WelcomePackage>(null)
    val selectedPackage: LiveData<WelcomePackage>
        get() = _selectedPackage

    init {
        loadSelectedPackage()
        loadBuyerList()
    }

    // TODO: Simulate the call  to the data source of a use case or repository
    private fun loadSelectedPackage() {
        viewModelScope.launch {
            _selectedPackage.value = packageRepository.getWelcomePackage(packageId)
        }
    }

    // TODO: Simulate the call  to the data source of a use case or repository
    private fun loadBuyerList() {
        viewModelScope.launch {
            _buyers.value = buyerRepository.getBuyerList()
        }
    }

    companion object {
        private const val PACKAGE_ID_SAVED_STATE_KEY = "packageId"
    }
}