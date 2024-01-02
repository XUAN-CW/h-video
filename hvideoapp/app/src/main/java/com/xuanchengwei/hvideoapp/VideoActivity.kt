package com.xuanchengwei.hvideoapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.xuanchengwei.hvideoapp.component.VideoInfo
import com.xuanchengwei.hvideoapp.constaint.IntentExtraKey
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class VideoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val videoInfo = intent.getParcelableExtra(IntentExtraKey.VIDEO_ACTIVITY_KEY, VideoInfo::class.java)
        setContent {

            val context = this
            val player = remember {
                ExoPlayer.Builder(context).build().apply {
                    val mediaItem = MediaItem.fromUri(videoInfo!!.imageUrl)
                    setMediaItem(mediaItem)
                    prepare()
                }
            }

            DisposableEffect(Lifecycle.Event.ON_DESTROY) {
                onDispose {
                    player.release()
                }
            }

            VideoPlayer(player = player)
        }
    }
}


@Composable
fun VideoPlayer(player: ExoPlayer) {
    var currentTime by remember { mutableStateOf("00:00") }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = formatTime(player.currentPosition )
            delay(1000) // Update time every second
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    this.player = player
                    // Remove the controller from the PlayerView, since we're adding our own
                    this.useController = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            // Progress bar (if you want to add a custom one)
            Spacer(modifier = Modifier.height(4.dp))
            // Current time text under the progress bar
            Text(text = currentTime, style = MaterialTheme.typography.bodySmall)
        }
    }
}

fun formatTime(millis: Long): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
    return String.format("%02d:%02d", minutes, seconds)
}
