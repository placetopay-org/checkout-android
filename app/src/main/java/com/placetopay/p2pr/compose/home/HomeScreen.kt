package com.placetopay.p2pr.compose.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.placetopay.p2pr.R
import com.placetopay.p2pr.compose.shoppingcart.ItemProductPackage
import com.placetopay.p2pr.compose.utils.TopAppBarContainer
import com.placetopay.p2pr.data.cart.WelcomePackage
import com.placetopay.p2pr.navigation.NavigationCallBack
import com.placetopay.p2pr.viewmodels.ShoppingCartViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onPackageClick: (WelcomePackage) -> Unit,
    viewModel: ShoppingCartViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val items = viewModel.welcomePackage.observeAsState().value

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarContainer(
                scrollBehavior = scrollBehavior,
                modifier = modifier,
                title = stringResource(id = R.string.app_name),
                callBack = NavigationCallBack()
            )
        }
    ) {
        Surface(
            modifier = modifier.padding(it),
            color = MaterialTheme.colorScheme.surface
        ) {
            HomePagerScreen(
                items = items,
                packageClick = onPackageClick,
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePagerScreen(
    items: List<WelcomePackage>?,
    packageClick: (WelcomePackage) -> Unit
) {
    Column {
        Text(
            text = stringResource(id = R.string.home_select_welcome_purchase),
            modifier = Modifier
                .padding(all = dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium
        )
        LazyVerticalGrid(
            state = rememberLazyGridState(),
            columns = GridCells.Adaptive(minSize = dimensionResource(id = R.dimen.size_grid_medium)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(
                count = items?.size ?: 0,
            ) {
                ItemProductPackage(
                    item = items!![it],
                    packageClick = packageClick
                )
            }
        }
    }
}



