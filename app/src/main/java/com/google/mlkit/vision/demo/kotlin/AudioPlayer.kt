package com.google.mlkit.vision.demo.kotlin

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.annotation.RawRes


class AudioPlayer(
    private val context: Context,
    private var lastTimePlayed: Int = System.currentTimeMillis().toInt()
) {
    fun play(@RawRes filepath: Int) {
        val timestamp = System.currentTimeMillis().toInt()

        if (timestamp - lastTimePlayed >= 5000) {
            val player = MediaPlayer.create(context, filepath)
            player.start()
            player.setOnCompletionListener {
                player.release()
            }
            Log.d("lastTimePlay", "${timestamp - lastTimePlayed}")
            lastTimePlayed = timestamp
        }
    }

}