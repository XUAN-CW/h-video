package com.xuanchengwei.hvideoapp
import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.xuanchengwei.hvideoapp.component.VideoInfo
import com.xuanchengwei.hvideoapp.constaint.IntentExtraKey

class VideoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val videoInfo = intent.getParcelableExtra(IntentExtraKey.VIDEO_ACTIVITY_KEY, VideoInfo::class.java)
        setContent {
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
