package com.example.youtubeapp

import android.content.Context
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.DefaultTimeBar
import androidx.media3.ui.TimeBar

@Composable
fun LayoutPlayerControl(
    player: Player? = null,
    onScrubStart: () -> Unit = {},
    onScrubMove: (Long) -> Unit = {},
    onScrubStop: (Long) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0x99000000))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Spacer(modifier = Modifier.weight(2f))

            Button(
                onClick = { },
                enabled = true,
                contentPadding = PaddingValues(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_fast_rewind_24),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.weight(3f))

            Button(
                onClick = { },
                enabled = true,
                contentPadding = PaddingValues(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pause),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.weight(3f))


            Button(
                onClick = { },
                enabled = true,
                contentPadding = PaddingValues(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_fast_forward_24),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.weight(2f))

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.size(12.dp))
                Text(text = "00:00", color = Color.White, fontSize = 12.sp)
                Spacer(modifier = Modifier.size(12.dp))
                Text(text = "/", color = Color.White, fontSize = 12.sp)
                Spacer(modifier = Modifier.size(12.dp))
                Text(text = "00:00", color = Color(0xff858585), fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.size(12.dp))

            player?.let {
                ExoPlayerTimebar(
                    player = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    onScrubStart = onScrubStart,
                    onScrubMove = onScrubMove,
                    onScrubStop = onScrubStop,
                )
            } ?: Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ) {
                Text(
                    text = "Timebar",
                    color = Color.White
                )
            }
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun ExoPlayerTimebar(
    player: Player?,
    modifier: Modifier,
    onScrubStart: () -> Unit,
    onScrubMove: (Long) -> Unit,
    onScrubStop: (Long) -> Unit
) {
    val context = LocalContext.current

    val scrubListener = remember {
        object : TimeBar.OnScrubListener {
            override fun onScrubStart(timeBar: TimeBar, position: Long) {
                onScrubStart()
            }

            override fun onScrubMove(timeBar: TimeBar, position: Long) {
                onScrubMove(position)
            }

            override fun onScrubStop(
                timeBar: TimeBar,
                position: Long,
                canceled: Boolean
            ) {
                onScrubStop(position)
            }

        }
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            createDefaultTimeBar(context)
        }
    )
}

@OptIn(UnstableApi::class)
fun createDefaultTimeBar(context: Context): DefaultTimeBar {
    return DefaultTimeBar(context).apply{
        setPlayedColor(0xFFFFFFFF.toInt())
        setScrubberColor(0xFF8A8A8A.toInt())
    }
}

@OptIn(UnstableApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultTimeBarPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.Black)
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .align(Alignment.Center),
            factory = { context ->
                DefaultTimeBar(context).apply {
                    setPlayedColor(0xFFFFFFFF.toInt())  // 흰색 (ARGB 형식)
                    setScrubberColor(0xFF8A8A8A.toInt())  // 회색 (ARGB 형식)
                    // 더미 데이터로 타임바 채우기
                    setDuration(60000)
                    setPosition(30000)
                    setBufferedPosition(35000) // 버퍼링
                }
            }
        )
    }
}

@Preview
@Composable
fun LayoutPlayerControlPreview() {
    LayoutPlayerControl()
}