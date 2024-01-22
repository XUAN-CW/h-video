package com.xuanchengwei.baizizhan.constaint

import java.time.format.DateTimeFormatter

object Constants {
    const val wordDetailListKey = "wordDetailListKey"
    const val wordDetailListInitialPageKey = "wordDetailListInitialPageKey"

    const val timeFormat = "yyyy-MM-dd HH:mm:ss.SSS"
    val dateTimeFormatter = DateTimeFormatter.ofPattern(timeFormat)

    const val doubleClickThreshold = 200
}