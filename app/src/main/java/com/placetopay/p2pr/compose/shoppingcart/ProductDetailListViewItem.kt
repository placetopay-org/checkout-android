package com.placetopay.p2pr.compose.shoppingcart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import com.placetopay.p2pr.R
import com.placetopay.p2pr.compose.utils.IconTitleContainer
import com.placetopay.p2pr.data.checkout.base.CheckoutItem
import com.placetopay.p2pr.utilities.toMoneyFormat

@Composable
fun ItemProduct(
    item: CheckoutItem
) {
    ElevatedCard(
        modifier = Modifier
            .padding(all = dimensionResource(id = R.dimen.padding_medium)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(all = dimensionResource(id = R.dimen.padding_medium))
        ) {
            IconTitleContainer(
                modifier= Modifier
                    .fillMaxWidth(),
                resourceId = R.drawable.ic_tag,
                text = item.name,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = item.total().toMoneyFormat(),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
            Text(
                text = stringResource(R.string.product_item_category, item.category),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
            Text(
                text = stringResource(R.string.product_item_qty, item.qty),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItemProduct() {
    ItemProduct(
        item = CheckoutItem("Books", "Pergamino antiguo", 80_000, 1, 202, 0.0),
    )
}