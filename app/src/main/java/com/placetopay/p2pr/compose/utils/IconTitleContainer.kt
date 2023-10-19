package com.placetopay.p2pr.compose.utils

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.placetopay.p2pr.R

@Composable
fun IconTitleContainer(
    modifier: Modifier,
    @DrawableRes resourceId: Int,
    text: String,
    style: androidx.compose.ui.text.TextStyle = LocalTextStyle.current,
) {
    Row(
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = resourceId),
            contentDescription = text,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.size_image_small))
                .padding(end = dimensionResource(id = R.dimen.padding_small))
                .size(ButtonDefaults.IconSize)
        )
        Text(text = text, style = style)
    }
}

@Preview
@Composable
fun PreviewIconTitleContainer() {
    IconTitleContainer(
        modifier = Modifier.fillMaxWidth(),
        resourceId = R.drawable.ic_user_tag,
        text = stringResource(id = R.string.app_name),
        style = MaterialTheme.typography.titleSmall
    )
}