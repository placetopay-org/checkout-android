package com.placetopay.p2pr.navigation

data class NavigationCallBack(
    val onBackClick: (() -> Unit)? = null,
    val onActionClick: (() -> Unit)? = null,
)
