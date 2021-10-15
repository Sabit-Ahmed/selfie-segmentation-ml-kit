package com.google.mlkit.vision.demo.kotlin

import android.content.Context
import android.util.Log
import com.google.mlkit.vision.demo.R

abstract class IExercise(
    context: Context
) {
    private val audioPlayer = AudioPlayer(context)
    private var lastTimePlayed: Int = System.currentTimeMillis().toInt()

    fun comeForward() {
        val timestamp = System.currentTimeMillis().toInt()
        if (timestamp - lastTimePlayed >= 0) {
            Log.d("TimeCheck","Difference:: ${timestamp - lastTimePlayed}")
            lastTimePlayed = timestamp
            audioPlayer.play(R.raw.please_come_forward)
        }
    }
}