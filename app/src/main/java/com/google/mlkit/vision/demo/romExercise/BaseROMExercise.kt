package com.google.mlkit.vision.demo.kotlin.romExercise

import android.graphics.Bitmap
import com.google.mlkit.vision.demo.romExercise.IROMExercise

class BaseROMExercise : IROMExercise {
    override fun getPixelDifference(inputData: Bitmap): Int {
        return 569
    }

}