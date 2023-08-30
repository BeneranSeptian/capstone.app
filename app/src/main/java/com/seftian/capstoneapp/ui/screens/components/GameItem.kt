package com.seftian.capstoneapp.ui.screens.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GameItem(
    name: String,
    backgroundUrl: String,
    modifier: Modifier = Modifier,
    onClickItem: (String) -> Unit,
    id: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                shape = AbsoluteCutCornerShape(topRight = 16.dp, bottomLeft = 16.dp),
                color = Color.DarkGray
            )
            .requiredHeight(150.dp)
            .clickable {
                onClickItem(id)
            }
    ) {
        ImageWithTitle(
            id,
            name,
            backgroundUrl,
            modifier = Modifier.clip(AbsoluteCutCornerShape(topRight = 16.dp, bottomLeft = 16.dp)),
        )
    }
}