package com.xuanchengwei.hvideoapp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun VideoList() {

    val videoInfoLists = listOf(
        VideoInfo(
            imageUrl = "http://vjs.zencdn.net/v/oceans.mp4",
            title = "First VideoInfo Title",
            views = "1M views",
            duration = "12:35"
        ),
        VideoInfo(
            imageUrl = "https://via.placeholder.com/150.jpg",
            title = "Second VideoInfo Title",
            views = "500K views",
            duration = "08:20"
        ),
        VideoInfo(
            imageUrl = "https://via.placeholder.com/150.jpg",
            title = "3 VideoInfo Title",
            views = "500K views",
            duration = "08:20"
        ),
    )


    LazyColumn {
        items(items = videoInfoLists.chunked(2)) { rowItems ->
            Row(Modifier.fillMaxWidth()) {
                rowItems.forEach { video ->
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        VideoCard(video)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun VideoCard(videoInfo: VideoInfo) {
    Card(
        modifier = Modifier.padding(1.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            GlideImage(
                model = videoInfo.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
            )
//            Spacer(modifier = Modifier.height(0.dp))
            Text(
                text = videoInfo.title,
                modifier = Modifier.padding(8.dp)
            )
            // Add other composables for additional details like views, comments, etc.
        }
    }
}

// You'll need to define a data class to represent the video data
data class VideoInfo(
    val imageUrl: String,
    val title: String,
    val views: String,
    val duration: String,

    // ... other fields like views, comments, etc.
)

// Sample preview of your composable
@Preview(showBackground = true)
@Composable
fun VideoListPreview() {
    VideoList()
}