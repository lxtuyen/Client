package com.example.heartsteel.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heartsteel.R
import com.example.heartsteel.tools.Ext.color
import com.example.heartsteel.tools.Ext.round
import com.example.heartsteel.ui.theme.Primary30

@Composable
fun PlayerSmall(modifier: Modifier = Modifier) {

    Box(modifier.fillMaxWidth().round(8.dp).color(Color.DarkGray)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 6.dp),
        verticalAlignment = Alignment.CenterVertically) {
            ImageCrop(modifier = Modifier.size( 40.dp).round(5.dp), data = R.drawable.album)
            Column(Modifier.weight(4f).padding(horizontal = 8.dp)) {
                Text(text = "text", color = Color.White,onTextLayout = {})
                Text(text = "text",color=Color.Gray, fontWeight = FontWeight.Light,onTextLayout = {})
            }
            IconBtn(resIcon = R.drawable.ic_sound)
            IconBtn(resIcon = R.drawable.ic_h_outline)
            IconBtn(resIcon = R.drawable.ic_baseline_play_arrow_24)
        }
        LinearProgressIndicator(progress = 0.8f,modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().height(2.dp).padding(horizontal = 8.dp), color = Color.White)
    }
}

@Preview
@Composable
fun PreviewSound(){
    PlayerSmall()
}