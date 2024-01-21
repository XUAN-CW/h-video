package com.xuanchengwei.hvideoapp.component

import android.content.Context

class HPlayerView(context: Context) : androidx.media3.ui.PlayerView(context) {

    init {
        // Disable default controller
        useController = false
    }
}