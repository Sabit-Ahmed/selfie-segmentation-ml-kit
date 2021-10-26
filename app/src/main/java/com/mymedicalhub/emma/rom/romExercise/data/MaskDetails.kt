package com.mymedicalhub.emma.rom.romExercise.data

data class MaskDetails(
    val pixelDifferenceX: Float,
    val pixelDifferenceY: Float,
    val topPoint: Point,
    val bottomPoint: Point,
    val leftPoint: Point,
    val rightPoint: Point
)