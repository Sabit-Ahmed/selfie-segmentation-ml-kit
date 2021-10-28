package com.mymedicalhub.emma.rom.poseModel.core

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import com.mymedicalhub.emma.rom.romExercise.data.Point

object VisualizationUtils {
    private const val LINE_WIDTH = 3f
    private const val BORDER_WIDTH = 6f
    private const val borderColor = Color.GREEN

    fun drawBodyKeyPoints(
        input: Bitmap
    ): Bitmap {
        val output = input.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(output)

        val draw = Draw(canvas, Color.WHITE, LINE_WIDTH)
        val width = draw.canvas.width
        val height = draw.canvas.height

        draw.writeText(
            "count",
            Point(width * 1 / 7f, 60f),
            Color.rgb(19, 93, 148),//blue
            65f
        )
        if (borderColor != -1) {
            draw.rectangle(
                Point(width * 2f / 20f, height * 2.5f / 20f),
                Point(width * 18.5f / 20f, height * 2.5f / 20f),
                Point(width * 18.5f / 20f, height * 18.5f / 20f),
                Point(width * 2f / 20f, height * 18.5f / 20f),
                _color = borderColor,
                _thickness = BORDER_WIDTH
            )
        }
        return output
    }
}


