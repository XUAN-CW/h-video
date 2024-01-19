package com.xuanchengwei.hvideoapp.component
import android.content.Intent
import android.os.Parcelable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.xuanchengwei.hvideoapp.VideoActivity
import com.xuanchengwei.hvideoapp.constaint.IntentExtraKey

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
            imageUrl = "http://192.168.0.106:8083/SHA1_WORK_DIR/SHA1/636/6361a32eeaff94e2503932c2d2766bdb8bd16230.mp4",
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
    val context = LocalContext.current
    Card(
        modifier = Modifier.padding(1.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            GlideImage(
                model = videoInfo.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp).clickable {

                        val intent = Intent(context, VideoActivity::class.java)
                        intent.putExtra(IntentExtraKey.VIDEO_ACTIVITY_KEY, videoInfo)
                        context.startActivity(intent)
                    }
            )
            Text(
                text = videoInfo.title,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

// You'll need to define a data class to represent the video data
@kotlinx.parcelize.Parcelize
data class VideoInfo (
    val imageUrl: String,
    val title: String,
    val views: String,
    val duration: String,
): Parcelable
