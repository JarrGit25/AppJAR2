package com.curso.AppJAR.canciones

// PlayerManager.kt
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

object PlayerManager {
    private var player: ExoPlayer? = null

    fun initializePlayer(context: Context) {
        if (player == null) {
            player = ExoPlayer.Builder(context).build()
        }
    }

    fun playUrl(url: String) {
        player?.let {
            val mediaItem = MediaItem.fromUri(url)
            it.setMediaItem(mediaItem)
            it.prepare()
            it.playWhenReady = true
        }
    }

    fun stop() {
        player?.stop()
    }

    fun release() {
        player?.release()
        player = null
    }
}
