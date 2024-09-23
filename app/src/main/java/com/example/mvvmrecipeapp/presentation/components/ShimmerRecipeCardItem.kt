package com.example.mvvmrecipeapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mvvmrecipeapp.presentation.components.util.shimmerLoadingAnimation

@Composable
fun ShimmerRecipeCardItem() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(
                bottom = 6.dp,
                top = 6.dp
            )
            //.weight(weight = 1f, fill = false)
    ) {
        repeat(5){
            Box(
                modifier = Modifier
                    .padding(
                        bottom = 6.dp,
                        top = 6.dp
                    )
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = Color.LightGray)
                    .height(300.dp)
                    .fillMaxWidth()
                    .shimmerLoadingAnimation(), // <--- Here.
            )
        }
    }

}