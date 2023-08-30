package com.seftian.capstoneapp.ui.screens.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seftian.capstoneapp.domain.model.Game

@Composable
fun Section(
    sectionName: String,
    gameList: List<Game>?,
    onClickItem: (String)->Unit
) {
    Text(
        text = sectionName,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        modifier = Modifier.padding(bottom = 12.dp)
    )
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (gameList.isNullOrEmpty()) {
            item {
                Text("datanya kosong :(")
            }
        } else {
            items(gameList) {
                GameItem(
                    id = it.id.toString(),
                    name = it.name,
                    backgroundUrl = it.backgroundImage,
                    modifier = Modifier.fillParentMaxWidth(),
                    onClickItem = {
                        onClickItem(it)
                    }
                )
            }
        }

    }
}