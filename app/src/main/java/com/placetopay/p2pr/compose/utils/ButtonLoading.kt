package com.placetopay.p2pr.compose.utils

import androidx.annotation.IntDef
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.placetopay.p2pr.R
import com.placetopay.p2pr.ui.Red600
import com.placetopay.p2pr.ui.Red700
import com.placetopay.p2pr.ui.White
import com.placetopay.p2pr.ui.Yellow500
import com.placetopay.p2pr.ui.Yellow600
import com.placetopay.p2pr.ui.Yellow700

class ButtonLoadingState(@LoadingState var state: Int = IDLE) {
    @Retention
    @IntDef(IDLE, LOADING, ERROR)
    annotation class LoadingState
    companion object {
        const val IDLE = 1
        const val LOADING = 2
        const val ERROR = 4
    }
}

@Composable
fun ButtonLoading(
    modifier: Modifier,
    onClick: () -> Unit,
    text: String,
    state: ButtonLoadingState,
    enabled: Boolean = true,
) {
    var backgroundColor by remember { mutableStateOf(Yellow600) }
    var disabledColor by remember { mutableStateOf(Yellow700) }
    Button(
        onClick = onClick, modifier = modifier.padding(vertical = 2.dp), colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor, disabledContainerColor = disabledColor
        ), enabled = enabled
    ) {
        when (state.state) {
            ButtonLoadingState.LOADING -> {
                LaunchedEffect(key1 = true) {
                    backgroundColor = Yellow500
                    disabledColor = Yellow700
                }
                CircularProgressIndicator(
                    color = White, strokeWidth = 3.dp
                )
            }

            ButtonLoadingState.ERROR -> {
                LaunchedEffect(key1 = true) {
                    backgroundColor = Red600
                    disabledColor = Red700
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 10.5.dp)
                )
            }

            ButtonLoadingState.IDLE -> {
                backgroundColor = Yellow500
                disabledColor = Yellow700
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 10.5.dp)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewButtonLoading() {
    ButtonLoading(
        onClick = { },
        text = "Confirm",
        state = ButtonLoadingState(ButtonLoadingState.IDLE),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_large),
                vertical = dimensionResource(id = R.dimen.padding_medium)
            )
    )
}