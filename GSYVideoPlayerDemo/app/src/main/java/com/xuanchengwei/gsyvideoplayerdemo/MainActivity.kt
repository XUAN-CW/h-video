package com.xuanchengwei.gsyvideoplayerdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.xuanchengwei.gsyvideoplayerdemo.ui.theme.GSYVideoPlayerDemoTheme

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import androidx.compose.ui.viewinterop.AndroidView
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
                    GSYVideoPlayerComposable(videoUrl)
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
fun GSYVideoPlayerComposable(url: String) {
    AndroidView(factory = { context ->
        StandardGSYVideoPlayer(context).apply {
            setUp(url, true, "Video Title")
            // Add any additional configuration here
        }
    })
}