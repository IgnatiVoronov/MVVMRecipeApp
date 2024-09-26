package com.example.mvvmrecipeapp.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mvvmrecipeapp.R


@Composable
fun ExpandDemo() {
    val color = MaterialTheme.colorScheme.primary
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .background(color)
            .animateContentSize()
            .height(if (expanded) 400.dp else 200.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                expanded = !expanded
            }

    ) {
    }
}

@Composable
fun HeartPulseAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val heartbeatAnimation by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Image(
        modifier = Modifier
            .scale(heartbeatAnimation)
            .fillMaxWidth()
            .height(125.dp),
        painter = painterResource(id = R.drawable.heart),
        contentDescription = "",
        colorFilter = ColorFilter.tint(Color.Red)
    )
}

@Composable
fun CircleHeartPulseAnimation() {
    val color = MaterialTheme.colorScheme.primary
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val beatAnimation by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Image(
        modifier = Modifier
            .scale(beatAnimation)
            .fillMaxWidth()
            .height(55.dp),
        imageVector = Icons.Default.Favorite,
        contentDescription = "",
        colorFilter = ColorFilter.tint(Color.Red)
    )

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .scale(beatAnimation)
    ) {
        drawCircle(
            brush = SolidColor(color),
        )
    }
}