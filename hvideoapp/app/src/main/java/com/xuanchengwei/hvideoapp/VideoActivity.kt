package com.xuanchengwei.hvideoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.xuanchengwei.hvideoapp.constaint.IntentExtraKey

class VideoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val videoInfo = intent.getParcelableExtra(IntentExtraKey.VIDEO_ACTIVITY_KEY,VideoInfo::class.java)
        Log.i("aa", videoInfo.toString())
        setContent {
            Text(text = videoInfo!!.imageUrl)
        }
    }
}

