package com.example.youtubeapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest

@Composable
fun ItemVideo(
    video: VideoEntity,
    onClick: (VideoEntity) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(video.videoThumb)
                .build(),
            contentDescription = "Video Thumbnail",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f/9f),
            placeholder = ColorPainter(Color.Gray),
            error = ColorPainter(Color.Gray),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 12.dp, end = 12.dp)
        ){
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(video.channelThumb)
                    .build(),
                contentDescription = "Channel Thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp),
                placeholder = ColorPainter(Color.Gray),
                error = ColorPainter(Color.Gray),
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
            ){
                Text(
                    text = "동영상 제목동영상 제목동영상 제목동영상 제목동영상 제목동영상 제목동영상 제목동영상 제목동영상 제목동영상 제목동영상동영상동영상",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth()  // Column 내에서 너비를 채우도록 명시
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "도소라 채널 · 조회수 100만회 · 3개월 전",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    fontSize = 11.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemVideoPreview() {
    // 샘플 데이터 생성
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

    ItemVideo(
        video = sampleVideo,
        onClick = {}
    )
}