package com.example.gael.poc_map_tracking_and_gallery.Utils

import android.app.Application
import android.util.Log
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.TwitterConfig



/**
 * Created on 16.10.17.
 */

class ProjectApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val config = TwitterConfig.Builder(this)
                .logger(DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(TwitterAuthConfig("5aUkACYdZezxal9bqKBuYBGBu", "3JchgpkzxxdfTSG0w0AMSYniBzDj6WNBmDbRdZpgBz1zMuC7h2"))
                .debug(true)
                .build()
        Twitter.initialize(config)
    }
}