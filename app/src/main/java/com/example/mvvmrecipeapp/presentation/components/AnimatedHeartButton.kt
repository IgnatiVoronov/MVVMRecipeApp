package com.example.mvvmrecipeapp.presentation.components

import android.graphics.drawable.ShapeDrawable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Shapes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mvvmrecipeapp.R
import com.google.android.material.imageview.ShapeableImageView


var imagePressed = mutableStateOf(false)
fun Modifier.bounceClickable(
    minScale: Float = 0.5f,
    onAnimationFinished: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) = composed {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) minScale else 1f,
        label = ""
    ) {
        if(isPressed) {
            isPressed = false
            onAnimationFinished?.invoke()
        }
    }

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable {
            isPressed = true
            onClick?.invoke()
            imagePressed.value = !imagePressed.value
        }
}

@Composable
fun HeartRateAnimation() {

    Image(
        modifier = Modifier
            .clip(CircleShape)
            .bounceClickable()
            .width(125.dp)
            .height(125.dp),
        painter = painterResource(id = R.drawable.heart),
        contentDescription = "",
        contentScale = ContentScale.Inside,
        colorFilter = if(imagePressed.value) ColorFilter.tint(Color.Red) else ColorFilter.tint(Color.Gray)
    )

}