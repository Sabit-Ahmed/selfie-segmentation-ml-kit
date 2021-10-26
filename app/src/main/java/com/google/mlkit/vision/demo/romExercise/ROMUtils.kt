package com.google.mlkit.vision.demo.romExercise

import com.google.mlkit.vision.demo.romExercise.data.Point
import kotlin.math.pow
import kotlin.math.sqrt

class ROMUtils {

    fun getDistance(startPoint: Point, endPoint: Point): Double {
        val squareDistance = ((startPoint.x - endPoint.x).toDouble()).pow(2.0) + ((startPoint.y - endPoint.y).toDouble()).pow(
            2.0
        )
        return sqrt(squareDistance)
    }

    fun getAngle() {

    }
}


