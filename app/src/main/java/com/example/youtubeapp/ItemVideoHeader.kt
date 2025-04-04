package com.example.youtubeapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import java.nio.file.WatchEvent

@Composable
fun ItemVideoHeader(
    video: VideoEntity,
    onClick: (VideoEntity) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = video.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "조회수 100만회 · 3개월 전",
                maxLines = 1,
                color = Color(0xffd9d9d9)
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        HorizontalDivider(
            color = Color(0xffcccccc)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data(video.channelThumb)
                    .build(),
                contentDescription = "Channel Thumb",
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(Color.Gray),
                error = ColorPainter(Color.Gray),
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.size(12.dp))

            Text(
                modifier = Modifier,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                text = "채널이름채널이름채널이름채널이름채널이름채널이름채널이름채널이름채널이름채널이름채널이름채널이름채널이름채널이름채널이름채널이름채널이름채널이름"
            )
        }

        HorizontalDivider(
            color = Color(0xffcccccc),
        )

        HorizontalDivider(
            color = Color(0xffd9d9d9),
            thickness = 7.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemVideoHeaderPreview() {
    val sampleVideo = VideoEntity(
        id = "sample_id",
        title = "샘플 비디오 제목",
        videoUrl = "",
        channelName = "샘플 채널",
        viewCount = "조회수 1만회",
        dateText = "3일 전",
        channelThumb = "",
        videoThumb = "" // 실제 앱에서는 이미지 URL이 들어가지만 프리뷰에서는 보이지 않음
    )

    ItemVideoHeader(
        video = sampleVideo,
        onClick = {}
    )
}