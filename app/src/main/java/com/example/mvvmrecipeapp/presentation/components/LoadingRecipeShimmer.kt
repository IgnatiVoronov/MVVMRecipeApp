package com.example.mvvmrecipeapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mvvmrecipeapp.presentation.components.util.shimmerLoadingAnimation

@Composable
fun LoadingRecipeShimmer() {
    Column(
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp
            )
    ) {
        Surface(shape = MaterialTheme.shapes.small) {
            Box(
                modifier = Modifier
                    .padding(
                        bottom = 6.dp,
                        top = 6.dp
                    )
                    .background(color = Color.LightGray)
                    .height(250.dp)
                    .fillMaxWidth()
                    .shimmerLoadingAnimation(),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Surface(shape = MaterialTheme.shapes.small) {
            Box(
                modifier = Modifier
                    .padding(
                        bottom = 6.dp,
                        top = 6.dp
                    )
                    .background(color = Color.LightGray)
                    .height(30.dp)
                    .fillMaxWidth()
                    .shimmerLoadingAnimation(),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        repeat(10) {
            Surface(shape = MaterialTheme.shapes.small) {
                Box(
                    modifier = Modifier
                        .padding(
                            bottom = 6.dp,
                            top = 6.dp
                        )
                        .background(color = Color.LightGray)
                        .height(10.dp)
                        .width(200.dp)
                        .shimmerLoadingAnimation(),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}