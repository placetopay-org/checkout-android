package com.placetopay.p2pr.compose.checkout

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.placetopay.p2pr.R
import com.placetopay.p2pr.compose.utils.TopAppBarContainer
import com.placetopay.p2pr.navigation.NavigationCallBack
import com.placetopay.p2pr.utilities.Constants
import com.placetopay.p2pr.utilities.fromBase64
import com.placetopay.p2pr.viewmodels.CheckoutViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = hiltViewModel(),
    onFinished: (String) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var refreshWebView by remember { mutableStateOf(false) }
    val processUrl = viewModel.processUrl.fromBase64()
    val requestId = viewModel.requestId

    Scaffold(modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        TopAppBarContainer(
            scrollBehavior = scrollBehavior,
            modifier = modifier,
            title = stringResource(id = R.string.checkout_name),
            callBack = NavigationCallBack(onBackClick = { onFinished(requestId) },
                onActionClick = {
                    refreshWebView = true
                })
        )
    }) {
        Surface(
            modifier = modifier.padding(it), color = MaterialTheme.colorScheme.surface
        ) {
            BackHandler {
                onFinished(requestId)
            }

            LaunchedEffect(key1 = refreshWebView) {
                refreshWebView = false
            }

            CheckoutWebView(
                processUrl,
                refreshWebView,
                onFinished = {
                    onFinished(requestId)
                }
            )

        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CheckoutWebView(processUrl: String, refreshWebView: Boolean, onFinished: () -> Unit) {
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            clearCache(true)
            webChromeClient = WebChromeClient()
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?, request: WebResourceRequest?
                ): Boolean {
                    if (request?.url.toString() == Constants.RETURN_URL || request?.url.toString() == Constants.CANCEL_URL)
                        onFinished()
                    return super.shouldOverrideUrlLoading(view, request)
                }
            }
            loadUrl(processUrl)
        }
    }, update = {
        it.loadUrl(processUrl)
        if (refreshWebView) it.reload()
    })
}