package com.example.youtubeapp

import androidx.compose.foundation.layout.Column
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

@Composable
fun ItemVideoHeader(
    video: VideoEntity,
    onClick: (VideoEntity) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ){
        Text(
            text="동영상제목동영상제목동영상제목동영상제목동영상제목동영상제목동영상제목동영상제목동영상제목동영상제목동영상제목동영상제목",
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            fontSize = 16.sp,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text="조회수 100만회 · 3개월 전",
            maxLines = 1,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemVideoHeaderPreview(){
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