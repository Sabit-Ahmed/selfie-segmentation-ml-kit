package com.google.mlkit.vision.demo.kotlin

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes

class AudioPlayer(
    private val context: Context
) {
    fun play(@RawRes filepath: Int) {
        val player = MediaPlayer.create(context, filepath)
        player.start()
        player.setOnCompletionListener {
            player.release()
        }
    }
}