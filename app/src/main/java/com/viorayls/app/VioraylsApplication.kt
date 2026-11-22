package com.viorayls.app

import android.app.Application
import com.viorayls.app.player.PlayerManager

class VioraylsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PlayerManager.init(applicationContext)
    }
}
