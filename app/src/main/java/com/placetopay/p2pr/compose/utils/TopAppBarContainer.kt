package com.placetopay.p2pr.compose.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.placetopay.p2pr.R
import com.placetopay.p2pr.navigation.NavigationCallBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarContainer(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier,
    title: String,
    callBack: NavigationCallBack,
    @DrawableRes actionResource: Int = R.drawable.ic_rotate_right,
    @StringRes actionDescription: Int = R.string.btn_reload,
) {
    Surface {
        TopAppBar(
            windowInsets = WindowInsets(0, 0, 20, 0),
            title = {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        if (callBack.onBackClick != null)
                            Box(
                                modifier = Modifier
                                    .size(dimensionResource(id = R.dimen.size_navigation))
                                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.size_navigation)))
                                    .background(MaterialTheme.colorScheme.secondaryContainer)
                                    .clickable {
                                        callBack.onBackClick!!()
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_arrow_left),
                                    contentDescription = stringResource(id = R.string.btn_back),
                                    modifier = Modifier.fillMaxHeight(),
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                                )
                            }
                        else
                            Image(
                                painter = painterResource(id = R.drawable.ic_placetopay),
                                contentDescription = stringResource(id = R.string.app_name),
                                modifier = Modifier.fillMaxHeight()
                            )
                    }
                    Box(
                        modifier = Modifier
                            .weight(3f)
                            .fillMaxSize(),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        Text(
                            text = title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 32.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(end = dimensionResource(id = R.dimen.padding_medium)),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        if (callBack.onActionClick != null)
                            Box(
                                modifier = Modifier
                                    .size(dimensionResource(id = R.dimen.size_navigation))
                                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.size_navigation)))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .clickable {
                                        callBack.onActionClick!!()
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Image(
                                    painter = painterResource(id = actionResource),
                                    contentDescription = stringResource(id = actionDescription),
                                    modifier = Modifier.fillMaxHeight(),
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                                )
                            }
                    }
                }
            },
            modifier = modifier.statusBarsPadding(),
            scrollBehavior = scrollBehavior
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewTopAppBarContainer() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    TopAppBarContainer(
        scrollBehavior = scrollBehavior,
        modifier = Modifier,
        stringResource(id = R.string.app_name),
        callBack = NavigationCallBack(onBackClick = {}, onActionClick = {})
    )
}