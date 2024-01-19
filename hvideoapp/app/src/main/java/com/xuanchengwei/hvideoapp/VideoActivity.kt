package com.xuanchengwei.hvideoapp
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.xuanchengwei.hvideoapp.component.VideoInfo
import com.xuanchengwei.hvideoapp.constaint.IntentExtraKey


class VideoActivity : ComponentActivity() {
    @OptIn(UnstableApi::class) @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val videoInfo = intent.getParcelableExtra(IntentExtraKey.VIDEO_ACTIVITY_KEY, VideoInfo::class.java)
        setContent {

            val context = this
            val player = remember {
                val loadControl: DefaultLoadControl = DefaultLoadControl.Builder()
                    .setBufferDurationsMs(5000, 100000, 500, 500)
                    .build()
                ExoPlayer.Builder(context)
//                    .setLoadControl(loadControl)
                    .build().apply {
                    val mediaItem = MediaItem.fromUri(videoInfo!!.imageUrl)
                    setMediaItem(mediaItem)
                    prepare()
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ){
//                Box(modifier = Modifier.fillMaxWidth()
//                    .fillMaxHeight(0.4f)){
//                    VideoPlayer(exoPlayer = player)
//                }
                HPlayer(player = player)
            }
        }
    }
}


@Composable
fun HPlayer(player: ExoPlayer) {
    var currentPosition by remember { mutableStateOf(0L) }

    player.addListener(object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            Log.i("onPlaybackStateChanged", playbackState.toString())
        }
    })

    val context = LocalContext.current

    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ){
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        Box(modifier = Modifier
            .fillMaxWidth()

            .height((screenWidth * 9 / 16))) {
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



        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
            ) {
                Text(text = "<<10s", fontSize = 15.sp, modifier = Modifier.clickable {
                    val newPosition = player.currentPosition - 10000L
                    player.seekTo(newPosition)
                    currentPosition = newPosition
                })

                Text(text = "<<30s", fontSize = 15.sp, modifier = Modifier.clickable {
                    val newPosition = player.currentPosition - 30000L
                    player.seekTo(newPosition)
                    currentPosition = newPosition
                })

                Text(text = "<<5m", fontSize = 15.sp, modifier = Modifier.clickable {
                    val newPosition = player.currentPosition - 300000L
                    player.seekTo(newPosition)
                    currentPosition = newPosition
                })
            }

            Row(
                modifier = Modifier
            ) {
                // This Row will be aligned to the right
                Text(text = ">>10s", fontSize = 15.sp, modifier = Modifier.clickable {
                    val newPosition = player.currentPosition + 10000L
                    player.seekTo(newPosition)
                    currentPosition = newPosition
                })

                Text(text = ">>30s", fontSize = 15.sp, modifier = Modifier.clickable {
                    val newPosition = player.currentPosition + 30000L
                    player.seekTo(newPosition)
                    currentPosition = newPosition
                })

                Text(text = ">>5m", fontSize = 15.sp, modifier = Modifier.clickable {
                    val newPosition = player.currentPosition + 300000L
                    player.seekTo(newPosition)
                    currentPosition = newPosition
                })
            }
        }


    }

}
