package com.google.mlkit.vision.demo.kotlin

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.annotation.RawRes
import com.google.mlkit.vision.demo.R


class AudioPlayer(
    private val context: Context,
    private var lastTimePlayed: Int = System.currentTimeMillis().toInt(),
    private var frameNum: Int = 0
) {
    fun play() {
        val timestamp = System.currentTimeMillis().toInt()
        if (timestamp - lastTimePlayed >= 4000) {
            Log.d("lastTimePlay", "${timestamp - lastTimePlayed} .... Frame: $frameNum")
            val player = MediaPlayer.create(context, R.raw.please_come_forward)
            player.start()
            player.setOnCompletionListener {
                player.release()
            }
            frameNum = 0
            lastTimePlayed = timestamp
        }

    }
}