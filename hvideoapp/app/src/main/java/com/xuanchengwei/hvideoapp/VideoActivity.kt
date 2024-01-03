package com.xuanchengwei.hvideoapp
import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.xuanchengwei.hvideoapp.component.VideoInfo
import com.xuanchengwei.hvideoapp.constaint.IntentExtraKey
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

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

            VideoPlayerCompose(videoInfo!!.imageUrl)
        }
    }
}


@Composable
fun VideoPlayerCompose(url: String) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            // Inflate your custom layout
            val view = LayoutInflater.from(context)
                .inflate(R.layout.activity_simple_detail_player, null, false)

            // Find your video player in the layout
            val detailPlayer = view.findViewById<StandardGSYVideoPlayer>(R.id.detail_player)
            // Configure the player as you like
            detailPlayer.getTitleTextView().visibility = View.GONE
            detailPlayer.getBackButton().visibility = View.GONE
            detailPlayer.setUp(url, true, "Video Title")

            // Assuming detailPlayer is your StandardGSYVideoPlayer instance
            // Assuming detailPlayer is your StandardGSYVideoPlayer instance
            detailPlayer.fullscreenButton.setOnClickListener {
                // This is where you handle the fullscreen button click
                if (!detailPlayer.isIfCurrentIsFullscreen) {
                    // Enter fullscreen mode
                    detailPlayer.startWindowFullscreen(context, false, true)
                } else {
                    // Exit fullscreen mode
                    detailPlayer.onBackFullscreen()
                }
            }
            val activity = context as? Activity

            detailPlayer.fullscreenButton.setOnClickListener {

                if (!detailPlayer.isIfCurrentIsFullscreen) {
                    // Enter full screen
                    detailPlayer.startWindowFullscreen(activity, true, true);
                    activity!!.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    // Exit full screen
                    // Implement the logic to restore the player to its original state
                    activity!!.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                }
            }


            // Return the view to be displayed
            view
        }
    )
}
