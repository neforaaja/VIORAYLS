package com.viorayls.app.player

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.viorayls.app.R
import com.viorayls.app.ui.MainActivity

class MusicService : Service() {

    override fun onCreate() {
        super.onCreate()
        PlayerManager.init(applicationContext)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        when (action) {
            "ACTION_PLAY_PAUSE" -> togglePlayPause()
            "ACTION_NEXT" -> PlayerManager.instance.next()
            "ACTION_PREVIOUS" -> PlayerManager.instance.previous()
            "ACTION_STOP" -> {
                stopForeground(true)
                stopSelf()
            }
        }
        startForeground(MusicService.NOTIFICATION_ID, buildNotification())
        return START_STICKY
    }

    private fun togglePlayPause() {
        val player = PlayerManager.instance
        if (player.state.value.isPlaying) player.pause() else player.play()
    }

    private fun buildNotification(): Notification {
        val channelId = "viorayls_player_channel"
        val track = PlayerManager.instance.state.value.track
        val title = track?.title ?: getString(R.string.app_name)
        val artist = track?.artist ?: ""

        val playPauseIntent = Intent(this, MusicService::class.java).setAction("ACTION_PLAY_PAUSE")
        val nextIntent = Intent(this, MusicService::class.java).setAction("ACTION_NEXT")
        val prevIntent = Intent(this, MusicService::class.java).setAction("ACTION_PREVIOUS")
        val stopIntent = Intent(this, MusicService::class.java).setAction("ACTION_STOP")

        val pendingPlayPause = PendingIntent.getService(this, 1, playPauseIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val pendingNext = PendingIntent.getService(this, 2, nextIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val pendingPrev = PendingIntent.getService(this, 3, prevIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val pendingStop = PendingIntent.getService(this, 4, stopIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val contentIntent = PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_sound_wave)
            .setContentTitle(title)
            .setContentText(artist)
            .setContentIntent(contentIntent)
            .setOngoing(true)
            .addAction(R.drawable.ic_previous, "Previous", pendingPrev)
            .addAction(
                if (PlayerManager.instance.state.value.isPlaying) R.drawable.ic_pause else R.drawable.ic_play,
                if (PlayerManager.instance.state.value.isPlaying) "Pause" else "Play",
                pendingPlayPause
            )
            .addAction(R.drawable.ic_next, "Next", pendingNext)
            .build()
    }

    override fun onBind(intent: Intent?): android.os.IBinder? = null

    companion object {
        const val NOTIFICATION_ID = 1001
        const val CHANNEL_ID = "viorayls_player_channel"
    }
}
