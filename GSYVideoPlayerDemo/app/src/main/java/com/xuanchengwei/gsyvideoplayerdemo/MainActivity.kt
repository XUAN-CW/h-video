package com.xuanchengwei.gsyvideoplayerdemo

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.xuanchengwei.gsyvideoplayerdemo.ui.theme.GSYVideoPlayerDemoTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GSYVideoPlayerDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val videoUrl = "http://vjs.zencdn.net/v/oceans.mp4"
                    VideoPlayerCompose(videoUrl)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GSYVideoPlayerDemoTheme {
        Greeting("Android")
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
                    activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    detailPlayer.startWindowFullscreen(activity, true, true)
                } else {
                    activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                    detailPlayer.fullscreenButton.performClick()
                }
            }


            // Return the view to be displayed
            view
        }
    )
}

