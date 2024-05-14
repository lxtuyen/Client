package com.example.heartsteel.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.media3.exoplayer.ExoPlayer
import com.example.heartsteel.R
import com.example.heartsteel.tools.Ext.color
import com.example.heartsteel.tools.Ext.round

@Composable
fun PlayerFull(
    onPlayPause: () -> Unit,
    onSkipBack: () -> Unit,
    onSkipForward: () -> Unit,
    onPlayStart: () -> Unit,
    exoplayer: ExoPlayer
) {

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        IconBtn(resIcon = R.drawable.ic_h_outline)
        IconBtn(
            resIcon = R.drawable.ic_player_back,
            modifier = Modifier.weight(1f),
            onClick = onSkipBack
        )
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(65.dp)
                .round(100)
                .color(Color.White),
            contentAlignment = Alignment.Center
        ) { if (exoplayer.isPlaying){
            IconBtn(resIcon = R.drawable.ic_player_pause, tint = Color.Black, onClick = onPlayPause)
        } else {
            IconBtn(resIcon = R.drawable.ic_player_play, tint = Color.Black, onClick = onPlayStart)
        }
        }
        IconBtn(
            resIcon = R.drawable.ic_player_skip,
            modifier = Modifier.weight(1f),
            onClick = onSkipForward
        )
        IconBtn(resIcon = R.drawable.ic_remove)
    }
}
/*
@Composable
@Preview
fun PlayerFullPreview() {
    PlayerFull(onPlayPause = {}, onSkipBack = {}, onSkipForward = {}, onPlayStart = {},exoPlayer = ExoPlayer )
}*/