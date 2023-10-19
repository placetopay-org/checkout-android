package com.placetopay.p2pr.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val requestId = savedStateHandle.get<String>(REQUEST_SAVED_STATE_KEY)!!
    val processUrl = savedStateHandle.get<String>(PROCESS_URL_SAVED_STATE_KEY)!!

    companion object {
        private const val REQUEST_SAVED_STATE_KEY = "requestId"
        private const val PROCESS_URL_SAVED_STATE_KEY = "processUrl"
    }
}