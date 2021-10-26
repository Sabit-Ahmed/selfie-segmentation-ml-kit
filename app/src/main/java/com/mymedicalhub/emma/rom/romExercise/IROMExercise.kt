package com.mymedicalhub.emma.rom.romExercise

import android.graphics.Bitmap

interface IROMExercise {
    fun getPixelDifference (inputData: Bitmap): Int
}