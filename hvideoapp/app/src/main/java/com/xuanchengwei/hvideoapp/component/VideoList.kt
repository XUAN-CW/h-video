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
            title = "MP4_oceans",
            views = "1M views",
            duration = "12:35"
        ),
        VideoInfo(
            imageUrl = "http://192.168.0.104:8083/SHA1_WORK_DIR/SHA512/214/2140ba87f823fba3ae33500985f82884fafb8067c8c9d3fff9e6bc40891610c13a5b2a4581f5ebc0d91af7d69902f8ad0270fb9ed1187ba636a4391281c5375c.mp4",
            title = "mp4_4K",
            views = "500K views",
            duration = "08:20"
        ),
        VideoInfo(
            imageUrl = "http://192.168.0.104:8083/SHA1_WORK_DIR/deletable/mp4_h265_OK.mp4",
            title = "mp4_h265_OK",
            views = "500K views",
            duration = "08:20"
        ),
        VideoInfo(
            imageUrl = "http://192.168.0.104:8083/SHA1_WORK_DIR/deletable/Play_the_Tifan_FPS60.mkv",
            title = "mkv",
            views = "500K views",
            duration = "08:20"
        ),
        VideoInfo(
            imageUrl = "http://192.168.0.104:8083/SHA1_WORK_DIR/deletable/Tifa_Mako_Loop_4-1.m4v",
            title = "m4v",
            views = "500K views",
            duration = "08:20"
        ),
        VideoInfo(
            imageUrl = "http://192.168.0.104:8083/SHA1_WORK_DIR/deletable/exoplayer_not_work.mkv",
            title = "exoplayer_not_work.mkv",
            views = "500K views",
            duration = "08:20"
        ),
        VideoInfo(
            imageUrl = "http://192.168.0.104:8083/SHA1_WORK_DIR/deletable/RBD-787.avi",
            title = "avi",
            views = "500K views",
            duration = "08:20"
        ),
        VideoInfo(
            imageUrl = "http://192.168.0.104:8083/null",
            title = "null",
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
