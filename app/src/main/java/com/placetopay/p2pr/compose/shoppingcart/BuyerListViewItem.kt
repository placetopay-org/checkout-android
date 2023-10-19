package com.placetopay.p2pr.compose.shoppingcart

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.placetopay.p2pr.R
import com.placetopay.p2pr.compose.utils.IconTitleContainer
import com.placetopay.p2pr.data.buyer.BuyerPackage
import com.placetopay.p2pr.data.checkout.base.CheckoutAddress
import com.placetopay.p2pr.data.checkout.base.CheckoutBuyer
import com.placetopay.p2pr.utilities.toMobileFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemBuyer(item: BuyerPackage, onBuyerClick: (BuyerPackage) -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .padding(all = dimensionResource(id = R.dimen.padding_medium)),
        onClick = { onBuyerClick(item) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        IconTitleContainer(
            modifier = Modifier.padding(all = dimensionResource(id = R.dimen.padding_medium)),
            resourceId = R.drawable.ic_user,
            text = item.title,
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = "${item.buyer.name} ${item.buyer.surname}",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium),
            )
        )
        Text(
            text = item.buyer.email,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium),
            )
        )
        Text(
            text = item.buyer.mobile.toMobileFormat(),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium),
            )
        )
        Text(
            text = item.buyer.address.toString(),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.padding_medium),
                bottom = dimensionResource(id = R.dimen.padding_medium)
            )
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewItemBuyer() {
    ItemBuyer(
        onBuyerClick = {},
        item = BuyerPackage(
            id = 3,
            title = "Buyer from Cali",
            buyer = CheckoutBuyer(
                CheckoutAddress(
                    "Cali",
                    "Colombia",
                    "+5722222222",
                    "660066",
                    "Valle del Cauca",
                    "Avenida 789"
                ),
                "5555555555",
                "CC",
                "comprador3@placetopay.com",
                "+573005555555",
                "Pedro",
                "López"
            )
        )
    )
}