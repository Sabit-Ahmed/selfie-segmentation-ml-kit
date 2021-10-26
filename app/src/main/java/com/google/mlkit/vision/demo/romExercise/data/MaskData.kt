package com.google.mlkit.vision.demo.romExercise.data

import java.nio.ByteBuffer

data class MaskData(
    val height: Int,
    val width: Int,
    val buffer: ByteBuffer
)
