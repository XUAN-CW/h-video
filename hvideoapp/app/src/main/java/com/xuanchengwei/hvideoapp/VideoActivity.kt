package com.xuanchengwei.hvideoapp
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.ui.PlayerView
import com.xuanchengwei.hvideoapp.component.VideoInfo
import com.xuanchengwei.hvideoapp.constaint.IntentExtraKey

class VideoActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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

            VideoPlayer(exoPlayer = player)
        }
    }
}


@Composable
fun VideoPlayer(exoPlayer: ExoPlayer) {
    val ctx = LocalContext.current

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
            }
        },
        update = { view ->
            // Set media source
            // Example: val mediaSource = buildMediaSource(Uri.parse(url))
            // exoPlayer.setMediaSource(mediaSource)
            // Prepare the player
            exoPlayer.prepare()
            // Start playback
            exoPlayer.playWhenReady = true
        }
    )
}

@Composable
fun HPlayer(player: ExoPlayer) {
    player.addListener(object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            Log.i("onPlaybackStateChanged", playbackState.toString())
        }
    })
    val context = LocalContext.current


    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.4f)) {
        AndroidView(
            factory = { ctx ->
                LayoutInflater.from(ctx).inflate(R.layout.hplayer_layout, null, false).apply {
                    val playerView = this.findViewById<PlayerView>(R.id.hplayer_view)
                    playerView.setControllerVisibilityListener(
                        PlayerView.ControllerVisibilityListener { visibility ->
                            if (visibility == View.VISIBLE) {
                                Log.i("PlayerView.ControllerVisibilityListener","View.VISIBLE")
                            } else {
                                Log.i("PlayerView.ControllerVisibilityListener","not View.VISIBLE")
                            }
                        }
                    )
                    playerView.player = player
                    // Configure additional properties if needed
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
    
}