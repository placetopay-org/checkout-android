package com.placetopay.p2pr.compose.buyer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.placetopay.p2pr.R
import com.placetopay.p2pr.compose.shoppingcart.ItemBuyer
import com.placetopay.p2pr.compose.utils.IconTitleContainer
import com.placetopay.p2pr.compose.utils.TopAppBarContainer
import com.placetopay.p2pr.data.buyer.BuyerPackage
import com.placetopay.p2pr.data.cart.WelcomePackage
import com.placetopay.p2pr.data.checkout.base.CheckoutAddress
import com.placetopay.p2pr.data.checkout.base.CheckoutBuyer
import com.placetopay.p2pr.navigation.NavigationCallBack
import com.placetopay.p2pr.viewmodels.BuyerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerScreen(
    modifier: Modifier = Modifier,
    viewModel: BuyerViewModel = hiltViewModel(),
    onConfirmClick: (WelcomePackage, BuyerPackage) -> Unit,
    onBackClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val welcomePackage = viewModel.selectedPackage.observeAsState().value
    val buyers = viewModel.buyers.observeAsState().value
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarContainer(
                scrollBehavior = scrollBehavior,
                modifier = modifier,
                title = stringResource(id = R.string.product_detail),
                callBack = NavigationCallBack(onBackClick = onBackClick)
            )
        }
    ) {
        Surface(
            modifier = modifier.padding(it),
            color = MaterialTheme.colorScheme.surface
        ) {
            BackHandler {
                onBackClick()
            }
            BuyerPagerScreen(
                buyers = buyers,
                onBuyerClick = { buyer -> onConfirmClick(welcomePackage!!, buyer) }
            )
        }
    }
}

@Composable
fun BuyerPagerScreen(
    buyers: List<BuyerPackage>?,
    onBuyerClick: (BuyerPackage) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        IconTitleContainer(
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.padding_large),
                    start = dimensionResource(id = R.dimen.padding_medium),
                    bottom = dimensionResource(id = R.dimen.padding_medium),
                )
                .fillMaxWidth(),
            resourceId = R.drawable.ic_user_tag,
            text = stringResource(id = R.string.detail_select_buyer),
            style = MaterialTheme.typography.titleMedium
        )

        LazyVerticalGrid(
            state = rememberLazyGridState(),
            columns = GridCells.Adaptive(minSize = dimensionResource(id = R.dimen.size_grid_medium))
        ) {
            items(count = buyers?.size ?: 0) {
                ItemBuyer(
                    item = buyers!![it],
                    onBuyerClick = onBuyerClick
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewBuyerPagerScreen() {
    BuyerPagerScreen(
        buyers = listOf(
            BuyerPackage(
                id = 1,
                title = "Buyer from Bogotá",
                buyer = CheckoutBuyer(
                    CheckoutAddress(
                        "Bogotá",
                        "Colombia",
                        "+5712345678",
                        "110111",
                        "Cundinamarca",
                        "Carrera 123"
                    ),
                    "1234567890",
                    "CC",
                    "comprador1@placetopay.com",
                    "+573001234567",
                    "Juan",
                    "Pérez"
                )
            )
        ),
        onBuyerClick = {}
    )
}
