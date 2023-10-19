package com.placetopay.p2pr.compose.shoppingcart

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.placetopay.p2pr.R
import com.placetopay.p2pr.data.cart.WelcomePackage
import com.placetopay.p2pr.data.checkout.base.CheckoutItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ItemProductPackage(
    item: WelcomePackage,
    packageClick: (WelcomePackage) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = dimensionResource(id = R.dimen.padding_medium)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = dimensionResource(id = R.dimen.padding_medium))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_shopping_cart),
                contentDescription = stringResource(R.string.product_detail_content_description_shop),
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.size_image_medium))
                    .padding(end = dimensionResource(id = R.dimen.padding_medium))
                    .size(ButtonDefaults.IconSize)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_placetopay),
                contentDescription = stringResource(id = R.string.placetopay_name),
                modifier = Modifier.height(dimensionResource(id = R.dimen.size_image_medium))
            )

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge
            )

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = dimensionResource(id = R.dimen.padding_medium))
        ) {
            Column {
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = stringResource(R.string.product_item_total_items, item.totalItems()),
                    Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(id = R.dimen.padding_medium)),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.End
                )
            }

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { packageClick(item) },
                modifier = Modifier.padding(all = dimensionResource(id = R.dimen.padding_small))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_shopping_cart),
                    contentDescription = stringResource(R.string.product_detail_content_description_shop),
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    text = stringResource(R.string.btn_buy_now),
                    modifier = Modifier
                        .padding(vertical = 2.dp),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductPackage() {
    ItemProductPackage(
        packageClick = {},
        item = WelcomePackage(
            id = 3,
            title = "Sample purchase with multiple products",
            description = "Listing of products with values from 35k to 80k for a single unit and nineteen percent tax.",
            items = mutableListOf(
                CheckoutItem("Books", "Libro antiguo", 80_000, 1, 302, 0.19),
                CheckoutItem("Clothes", "Camisa desconocida", 35_000, 2, 102, 0.19),
            )
        )
    )
}