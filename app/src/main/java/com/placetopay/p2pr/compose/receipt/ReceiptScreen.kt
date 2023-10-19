package com.placetopay.p2pr.compose.receipt

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.placetopay.p2pr.R
import com.placetopay.p2pr.compose.shoppingcart.ItemProduct
import com.placetopay.p2pr.compose.utils.IconTitleContainer
import com.placetopay.p2pr.compose.utils.TopAppBarContainer
import com.placetopay.p2pr.data.checkout.base.CheckoutAmount
import com.placetopay.p2pr.data.checkout.base.CheckoutAuth
import com.placetopay.p2pr.data.checkout.base.CheckoutItem
import com.placetopay.p2pr.data.checkout.base.CheckoutPayment
import com.placetopay.p2pr.data.checkout.base.CheckoutStatus
import com.placetopay.p2pr.data.checkout.payment.CheckoutPaymentRequest
import com.placetopay.p2pr.data.receipt.ReceiptStatus
import com.placetopay.p2pr.navigation.NavigationCallBack
import com.placetopay.p2pr.ui.Green500
import com.placetopay.p2pr.ui.Red500
import com.placetopay.p2pr.ui.White
import com.placetopay.p2pr.ui.Yellow500
import com.placetopay.p2pr.utilities.Constants
import com.placetopay.p2pr.utilities.showToastTop
import com.placetopay.p2pr.utilities.toDateFormat
import com.placetopay.p2pr.utilities.toMoneyFormat
import com.placetopay.p2pr.viewmodels.ReceiptViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptScreen(
    modifier: Modifier = Modifier,
    viewModel: ReceiptViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val informationResponse = viewModel.informationResponse.observeAsState().value
    val error = viewModel.errorResponse.observeAsState().value

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarContainer(
                scrollBehavior = scrollBehavior,
                modifier = modifier,
                title = stringResource(id = R.string.receipt_purchase),
                callBack = NavigationCallBack(
                    onBackClick = onBackClick,
                    onActionClick = {
                        viewModel.sessionInformation()
                    }
                )
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
            error?.let {
                showToastTop(LocalContext.current, error)
                viewModel.clean()
            }
            if (informationResponse != null)
                ReceiptPagerScreen(informationResponse.status, informationResponse.request)
            else
                LoadingReceiptPagerScreen()
        }
    }
}

@Composable
fun LoadingReceiptPagerScreen() {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = dimensionResource(id = R.dimen.padding_medium)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_timer),
                    contentDescription = stringResource(
                        id = R.string.app_name
                    ),
                    modifier = Modifier
                        .size(ButtonDefaults.IconSize)
                        .align(Alignment.Center),
                    colorFilter = ColorFilter.tint(color = White)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = dimensionResource(id = R.dimen.padding_small)),
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.product_order_loading),
                    modifier = Modifier
                        .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        color = White, strokeWidth = 3.dp
                    )
                }
            }
        }
    }
}

@Composable
fun ReceiptPagerScreen(status: CheckoutStatus, request: CheckoutPaymentRequest) {
    val receiptStatus = convertStatus(status = status)
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = dimensionResource(id = R.dimen.padding_medium)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(receiptStatus.color)
            ) {
                Image(
                    painter = painterResource(id = receiptStatus.icon),
                    contentDescription = stringResource(
                        id = R.string.app_name
                    ),
                    modifier = Modifier
                        .size(ButtonDefaults.IconSize)
                        .align(Alignment.Center),
                    colorFilter = ColorFilter.tint(color = White)
                )
            }
        }
        Row {
            Column(
                modifier = Modifier.padding(
                    dimensionResource(id = R.dimen.padding_medium)
                )
            ) {
                Text(
                    text = receiptStatus.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimensionResource(id = R.dimen.padding_small)),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = receiptStatus.color
                )

                Text(
                    text = stringResource(R.string.receipt_reference, request.payment.reference),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimensionResource(id = R.dimen.padding_small)),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall
                )

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

                IconTitleContainer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = dimensionResource(id = R.dimen.padding_small),
                            horizontal = dimensionResource(id = R.dimen.padding_large)
                        ),
                    resourceId = R.drawable.ic_message_notif,
                    text = stringResource(
                        R.string.receipt_description,
                        request.payment.description
                    ),
                    style = MaterialTheme.typography.titleSmall,
                )

                LazyRow(state = rememberLazyListState()) {
                    items(count = request.payment.items?.size ?: 0) {
                        ItemProduct(
                            request.payment.items!![it]
                        )
                    }
                }

                IconTitleContainer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = dimensionResource(id = R.dimen.padding_small),
                            horizontal = dimensionResource(id = R.dimen.padding_large)
                        ),
                    resourceId = R.drawable.ic_money_send,
                    text = stringResource(
                        R.string.receipt_amount,
                        request.payment.amount.total.toMoneyFormat(),
                        request.payment.amount.currency,
                    ),
                    style = MaterialTheme.typography.titleMedium,
                )
                IconTitleContainer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_large),
                            end = dimensionResource(id = R.dimen.padding_large),
                            top = dimensionResource(id = R.dimen.padding_small),
                            bottom = dimensionResource(id = R.dimen.padding_large)
                        ),
                    resourceId = R.drawable.ic_calendar_tick,
                    text = stringResource(R.string.receipt_date, status.date.toDateFormat()),
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLoadingReceiptPagerScreen() {
    LoadingReceiptPagerScreen()
}

@Preview(showBackground = true)
@Composable
fun PreviewReceiptScreen() {
    ReceiptPagerScreen(
        status = CheckoutStatus(
            status = "PENDING",
            reason = "PC",
            message = "La petición se encuentra activa",
            date = "2023-09-25T10:25:57-05:00"
        ),
        request = CheckoutPaymentRequest(
            auth = CheckoutAuth("login", "secret"),
            expiration = "2023-09-26T10:25:51-05:00",
            locale = "es_CO",
            payment = CheckoutPayment(
                description = "Et et sit dolores ut",
                reference = "TEST_20230925_102551",
                amount = CheckoutAmount(
                    currency = "COP",
                    total = 1_000.00
                ),
                items = mutableListOf(
                    CheckoutItem("Mystery", "Objeto desconocido", 100_000, 1, 201, 0.0),
                    CheckoutItem("Books", "Pergamino antiguo", 80_000, 1, 202, 0.0),
                    CheckoutItem("Mystery", "Gema brillante", 50_000, 1, 203, 0.0),
                    CheckoutItem("Home", "Llave dorada", 25_000, 1, 204, 0.0),
                    CheckoutItem("Mystery", "Reliquia antigua", 30_000, 1, 205, 0.0),
                    CheckoutItem("Home", "Estatuilla enigmática", 75_000, 1, 206, 0.0)
                )
            ),
            returnUrl = "https://dnetix.co/p2p/client",
            cancelUrl = "https://dnetix.co/p2p/client",
            userAgent = "Mozilla/5.0 (X11; Linux x86_64)..",
            ipAddress = "192.168.0.1"
        )
    )
}

@Composable
fun convertStatus(status: CheckoutStatus): ReceiptStatus {
    return when (status.status) {
        Constants.STATUS_APPROVED -> ReceiptStatus(
            title = stringResource(id = R.string.transaction_successful),
            icon = R.drawable.ic_tick_circle,
            color = Green500
        )

        Constants.STATUS_REJECTED,
        Constants.STATUS_FAILED,
        Constants.STATUS_CANCEL -> ReceiptStatus(
            title = stringResource(id = R.string.transaction_rejected),
            icon = R.drawable.ic_slash,
            color = Red500
        )

        else -> ReceiptStatus(
            title = stringResource(id = R.string.transaction_pending),
            icon = R.drawable.ic_timer,
            color = Yellow500
        )
    }
}