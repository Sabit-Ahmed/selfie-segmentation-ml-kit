package com.google.mlkit.vision.demo.romExercise

import android.graphics.Bitmap

interface IROMExercise {
    fun getPixelDifference (inputData: Bitmap): Int
}