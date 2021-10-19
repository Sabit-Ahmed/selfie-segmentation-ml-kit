package com.google.mlkit.vision.demo.kotlin

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import com.google.mlkit.vision.demo.R
import java.io.IOException


class AudioPlayer(
    private val context: Context,
    private var lastTimePlayed: Int = System.currentTimeMillis().toInt(),
) {
    private val player: MediaPlayer = MediaPlayer.create(this.context, R.raw.please_come_forward)

    fun play() {
        val timestamp = System.currentTimeMillis().toInt()
        if (timestamp - lastTimePlayed >= 4000) {
            Log.d("lastTimePlay", "${timestamp - lastTimePlayed}")
            player.start()
            player.setOnCompletionListener {this.context
                if (player != null && player.isPlaying) {
                    player.stop()
                    player.release()
                    try {
                        player.prepare()
                    }
                    catch (e: Exception) {
                        Log.d("error", "$e")
                    }

                }
            }
            lastTimePlayed = timestamp
        }

    }
}