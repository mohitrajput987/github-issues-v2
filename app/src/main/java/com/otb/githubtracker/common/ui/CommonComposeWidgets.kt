package com.otb.githubtracker.common.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.otb.githubtracker.R
import com.otb.githubtracker.theme.Black
import com.otb.githubtracker.theme.Typography
import com.otb.githubtracker.theme.headerHeight
import com.otb.githubtracker.theme.horizontalMargin

/**
 * Created by Mohit Rajput on 26/11/22.
 */

@Composable
fun Header(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary)
            .padding(horizontal = horizontalMargin)
            .height(headerHeight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, color = Color.White)
    }
}

@Composable
fun ErrorScreen(errorMessage: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(modifier = Modifier.fillMaxSize(), text = errorMessage, style = Typography.body2)
    }
}

@Composable
fun CircleImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    @DrawableRes placeholderResId: Int
) {
    AsyncImage(
        modifier = modifier
            .size(24.dp)
            .clip(CircleShape)
            .border(shape = CircleShape, width = 1.dp, color = Color.Gray),
        contentScale = ContentScale.Crop,
        model = imageUrl,
        contentDescription = stringResource(id = R.string.app_name),
        placeholder = painterResource(id = placeholderResId)
    )
}