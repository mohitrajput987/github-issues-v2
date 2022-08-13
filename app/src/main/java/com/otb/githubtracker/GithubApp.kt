package com.otb.githubtracker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Mohit Rajput on 13/08/22.
 */

@HiltAndroidApp
class GithubApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}