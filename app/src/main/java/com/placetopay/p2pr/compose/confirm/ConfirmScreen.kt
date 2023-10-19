package com.placetopay.p2pr.compose.confirm

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.placetopay.p2pr.R
import com.placetopay.p2pr.compose.shoppingcart.ItemProduct
import com.placetopay.p2pr.compose.utils.ButtonLoading
import com.placetopay.p2pr.compose.utils.ButtonLoadingState
import com.placetopay.p2pr.compose.utils.IconTitleContainer
import com.placetopay.p2pr.compose.utils.TopAppBarContainer
import com.placetopay.p2pr.data.buyer.BuyerPackage
import com.placetopay.p2pr.data.cart.WelcomePackage
import com.placetopay.p2pr.data.checkout.base.CheckoutAddress
import com.placetopay.p2pr.data.checkout.base.CheckoutBuyer
import com.placetopay.p2pr.data.checkout.base.CheckoutItem
import com.placetopay.p2pr.navigation.NavigationCallBack
import com.placetopay.p2pr.utilities.showToastTop
import com.placetopay.p2pr.utilities.toBase64
import com.placetopay.p2pr.utilities.toMobileFormat
import com.placetopay.p2pr.utilities.toMoneyFormat
import com.placetopay.p2pr.viewmodels.ConfirmViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmScreen(
    modifier: Modifier = Modifier,
    viewModel: ConfirmViewModel = hiltViewModel(),
    onProcessUrl: (String, String) -> Unit,
    onBackClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val welcomePackage = viewModel.selectedPackage.observeAsState().value
    val buyer = viewModel.selectedBuyer.observeAsState().value
    val error = viewModel.errorResponse.observeAsState().value
    val payment = viewModel.paymentResponse.observeAsState().value
    val loadingState = viewModel.loadingState.observeAsState().value
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarContainer(
                scrollBehavior = scrollBehavior, modifier = modifier, title = stringResource(
                    id = R.string.confirm_purchase
                ),
                callBack = NavigationCallBack(onBackClick = onBackClick)
            )
        },
        bottomBar = {
            DetailBottomBar(
                welcomePackage = welcomePackage,
                state = loadingState ?: ButtonLoadingState(ButtonLoadingState.IDLE),
                onConfirmClick = {
                    if (loadingState?.state != ButtonLoadingState.LOADING)
                        viewModel.createSession(welcomePackage!!, buyer!!)
                })
        }
    ) {
        Surface(
            modifier = modifier.padding(it),
            color = MaterialTheme.colorScheme.surface
        ) {
            BackHandler {
                onBackClick()
            }
            error?.let {
                showToastTop(LocalContext.current, error)
                viewModel.clean()
            }
            if (payment?.processUrl.isNullOrEmpty().not()) {
                val id = payment?.requestId.orEmpty()
                val url = payment?.processUrl.orEmpty().toBase64()
                viewModel.clean()
                onProcessUrl(id, url)
            }
            ConfirmPagerScreen(buyer!!)
        }
    }
}

@Composable
fun ConfirmPagerScreen(buyer: BuyerPackage) {
    LazyVerticalGrid(
        state = rememberLazyGridState(),
        columns = GridCells.Adaptive(minSize = dimensionResource(id = R.dimen.size_grid_medium))
    ) {
        item {
            ElevatedCard(
                modifier = Modifier
                    .padding(all = dimensionResource(id = R.dimen.padding_medium)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(
                    modifier = Modifier
                        .padding(all = dimensionResource(id = R.dimen.padding_medium))
                ) {
                    Text(
                        text = stringResource(id = R.string.product_detail),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(
                            start = dimensionResource(id = R.dimen.padding_small),
                            bottom = dimensionResource(id = R.dimen.padding_large)
                        )
                    )
                    IconTitleContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = dimensionResource(id = R.dimen.padding_small),
                            ),
                        resourceId = R.drawable.ic_user_tag,
                        text = "${buyer.buyer.name} ${buyer.buyer.surname}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    IconTitleContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = dimensionResource(id = R.dimen.padding_small),
                                top = dimensionResource(id = R.dimen.padding_small)
                            ),
                        resourceId = R.drawable.ic_archive_tick,
                        text = "${buyer.buyer.documentType} ${buyer.buyer.document}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    IconTitleContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = dimensionResource(id = R.dimen.padding_small),
                                top = dimensionResource(id = R.dimen.padding_small)
                            ),
                        resourceId = R.drawable.ic_directbox_default,
                        text = buyer.buyer.email,
                        style = MaterialTheme.typography.bodySmall
                    )
                    IconTitleContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = dimensionResource(id = R.dimen.padding_small),
                                top = dimensionResource(id = R.dimen.padding_small)
                            ),
                        resourceId = R.drawable.ic_call,
                        text = buyer.buyer.mobile.toMobileFormat(),
                        style = MaterialTheme.typography.bodySmall
                    )

                    Text(
                        text = stringResource(id = R.string.confirm_purchase_address),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(id = R.dimen.padding_small),
                            vertical = dimensionResource(id = R.dimen.padding_large)
                        )
                    )
                    IconTitleContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = dimensionResource(id = R.dimen.padding_small),
                                bottom = dimensionResource(id = R.dimen.padding_large)
                            ),
                        resourceId = R.drawable.ic_location,
                        text = buyer.buyer.address.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }

}

@Composable
fun DetailBottomBar(
    welcomePackage: WelcomePackage?,
    state: ButtonLoadingState,
    onConfirmClick: () -> Unit,
) {

    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .shadow(
                elevation = 2.dp,
                clip = true,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
    ) {
        Text(
            text = stringResource(id = R.string.product_order),
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.padding_large),
                    bottom = dimensionResource(id = R.dimen.padding_medium)
                )
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        LazyRow(state = rememberLazyListState()) {
            items(count = welcomePackage?.items?.size ?: 0) {
                ItemProduct(
                    welcomePackage?.items!![it]
                )
            }
        }
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(
                        id = R.string.product_item_total_items,
                        welcomePackage!!.totalItems()
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = dimensionResource(id = R.dimen.padding_small),
                            horizontal = dimensionResource(id = R.dimen.padding_medium)
                        ),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = stringResource(id = R.string.product_item_shipping),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = dimensionResource(id = R.dimen.padding_small),
                            horizontal = dimensionResource(id = R.dimen.padding_medium)
                        ),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = stringResource(id = R.string.product_item_total),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = dimensionResource(id = R.dimen.padding_small),
                            horizontal = dimensionResource(id = R.dimen.padding_medium)
                        ),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = welcomePackage!!.subTotalAmount().toMoneyFormat(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = dimensionResource(id = R.dimen.padding_small),
                            horizontal = dimensionResource(id = R.dimen.padding_medium)
                        ),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = welcomePackage.standardShipping.toMoneyFormat(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = dimensionResource(id = R.dimen.padding_small),
                            horizontal = dimensionResource(id = R.dimen.padding_medium)
                        ),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = (welcomePackage.standardShipping + welcomePackage.subTotalAmount()).toMoneyFormat(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = dimensionResource(id = R.dimen.padding_small),
                            horizontal = dimensionResource(id = R.dimen.padding_medium)
                        ),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        Row(
            modifier = Modifier.padding(
                bottom = dimensionResource(id = R.dimen.padding_extra_large)
            ),
        ) {
            ButtonLoading(
                onClick = {
                    onConfirmClick()
                },
                text = stringResource(id = R.string.btn_buy_now),
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_large),
                        end = dimensionResource(id = R.dimen.padding_large),
                        top = dimensionResource(id = R.dimen.padding_medium),
                        bottom = dimensionResource(id = R.dimen.padding_large)
                    ),
            )
        }
    }
}

@Preview
@Composable
fun PreviewConfirmPagerScreen() {
    ConfirmPagerScreen(
        buyer = BuyerPackage(
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
    )
}

@Preview
@Composable
fun PreviewDetailBottomBar() {
    val loadingState by remember { mutableStateOf(ButtonLoadingState(ButtonLoadingState.IDLE)) }
    DetailBottomBar(
        state = loadingState,
        onConfirmClick = {},
        welcomePackage = WelcomePackage(
            id = 1,
            title = "Sample purchase with multiple products",
            description = "Listing of products with values from 25k to 100k for a single unit and zero tax.",
            items = mutableListOf(
                CheckoutItem("Mystery", "Objeto desconocido", 100_000, 1, 201, 0.0),
                CheckoutItem("Books", "Pergamino antiguo", 80_000, 1, 202, 0.0),
                CheckoutItem("Mystery", "Gema brillante", 50_000, 1, 203, 0.0),
                CheckoutItem("Home", "Llave dorada", 25_000, 1, 204, 0.0),
                CheckoutItem("Mystery", "Reliquia antigua", 30_000, 1, 205, 0.0),
                CheckoutItem("Home", "Estatuilla enigmática", 75_000, 1, 206, 0.0)
            )
        ),
    )
}