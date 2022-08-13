package com.otb.githubtracker.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Mohit Rajput on 13/08/22.
 */
object DateUtils {
    fun getFormattedTime(date: Date) =
        SimpleDateFormat("mm-dd-yyyy", Locale.ENGLISH).format(date.time) ?: ""
}