package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXShapes
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteContainer(
    onDelete: () -> Unit,
    animationDuration: Int = 500,
    content: @Composable () -> Unit,
) {

    var isRemoved by remember { mutableStateOf(false) }
    val state = rememberDismissState(
        confirmValueChange = { value ->
            if (value == DismissValue.DismissedToStart) {
                isRemoved = true
                true
            } else {
                isRemoved = false
                false
            }
        }
    )

    LaunchedEffect(isRemoved) {
       if (isRemoved) {
           delay(animationDuration.toLong())
           onDelete()
       } else {
           state.reset()
       }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        enter = expandVertically(animationSpec = tween(durationMillis = animationDuration), expandFrom = Alignment.Bottom) + fadeIn(),
        exit = shrinkVertically(animationSpec = tween(durationMillis = animationDuration), shrinkTowards = Alignment.Top) + fadeOut()
    ) {
        SwipeToDismiss(
            state = state,
            background = { DeleteBackground(state) },
            dismissContent = { content() },
            directions = setOf(DismissDirection.EndToStart)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(swipeDismissState: DismissState) {
    val color = if (swipeDismissState.dismissDirection == DismissDirection.EndToStart) {
        Color.Red
    } else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .clip(MXShapes.medium)
        ,
        contentAlignment = Alignment.CenterEnd
    ) {
       Icon(
           Icons.Default.Delete,
           contentDescription = null,
           tint = Color.White
       )
    }
}