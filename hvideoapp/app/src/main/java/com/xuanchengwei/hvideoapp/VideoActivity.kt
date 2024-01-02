package com.xuanchengwei.hvideoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.xuanchengwei.hvideoapp.component.VideoInfo
import com.xuanchengwei.hvideoapp.constaint.IntentExtraKey

class VideoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val videoInfo = intent.getParcelableExtra(IntentExtraKey.VIDEO_ACTIVITY_KEY, VideoInfo::class.java)
        setContent {
            Text(text = videoInfo!!.imageUrl)
        }
    }
}

