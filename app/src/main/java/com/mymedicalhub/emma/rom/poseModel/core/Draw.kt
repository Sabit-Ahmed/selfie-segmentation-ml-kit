package com.mymedicalhub.emma.rom.poseModel.core

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.mymedicalhub.emma.rom.romExercise.core.ROMUtils
import com.mymedicalhub.emma.rom.romExercise.data.Point
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class Draw(
    val canvas: Canvas,
    private val color: Int,
    private val thickness: Float,
    private val clockWise: Boolean = false
) {
    fun line(
        startPoint: Point,
        endPoint: Point,
        lineType: Paint.Style? = Paint.Style.FILL,
        _color: Int = color,
        _thickness: Float = thickness
    ) {
        val lineStyle = Paint().apply {
            strokeWidth = _thickness
            color = _color
            style = lineType
        }
        canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, lineStyle)
        circle(startPoint, 4f, startPoint, 360f, _color = _color)
        circle(endPoint, 4f, endPoint, 360f, _color = _color)
    }

    private fun circle(
        center: Point,
        radius: Float,
        vectorBc: Point,
        angleValue: Float,
        _color: Int = color,
        _clockWise: Boolean = clockWise
    ) {
        val circleStyle = Paint().apply {
            strokeWidth = thickness
            color = _color
            style = Paint.Style.STROKE
        }
        var startAngle = ROMUtils.getAngle(Point(vectorBc.x, -vectorBc.y))
        if (_clockWise) {
            startAngle -= angleValue
        }
        val oval = RectF()
        oval.set(center.x - radius, center.y - radius, center.x + radius, center.y + radius)
        canvas.drawArc(oval, -startAngle, -angleValue, true, circleStyle)
    }

    fun writeText(
        text: String,
        position: Point,
        textColor: Int = Color.WHITE,
        fontSize: Float = 30f
    ) {
        val textStyle = Paint().apply {
            color = textColor
            textSize = fontSize
            style = Paint.Style.FILL
        }
        val xPosition = position.x
        val yPosition = position.y
        canvas.drawText(text, xPosition, yPosition, textStyle)
    }

    fun angle(
        startPoint: Point,
        middlePoint: Point,
        endPoint: Point,
        lineType: Paint.Style? = Paint.Style.FILL,
        radius: Float = 50F,
        _clockWise: Boolean = clockWise
    ) {
        val pointA = Point(startPoint.x, -startPoint.y)
        val pointB = Point(middlePoint.x, -middlePoint.y)
        val pointC = Point(endPoint.x, -endPoint.y)
        val angleValue = ROMUtils.getAngle(pointA, pointB, pointC, _clockWise).toInt()
        val vectorBc = Point(pointC.x - pointB.x, pointC.y - pointB.y)
        val startAngle = ROMUtils.getAngle(vectorBc)
        val endAngle = if (_clockWise) {
            startAngle - angleValue
        } else {
            startAngle + angleValue
        }
        var midAngle = if (endAngle > startAngle) {
            endAngle - angleValue / 2
        } else {
            startAngle - angleValue / 2
        }
        midAngle = (midAngle * PI.toFloat()) / 180f
        val textPositionRadius = radius + 70
        val textPosition = Point(
            middlePoint.x + textPositionRadius * cos(midAngle),
            middlePoint.y - textPositionRadius * sin(midAngle)
        )
        val referenceVector = Point(endPoint.x - middlePoint.x, endPoint.y - middlePoint.y)

        line(startPoint, middlePoint, lineType)
        line(middlePoint, endPoint, lineType)

        circle(startPoint, 4f, startPoint, 360f, _clockWise = _clockWise)
        circle(middlePoint, 4f, middlePoint, 360f, _clockWise = _clockWise)
        circle(endPoint, 4f, endPoint, 360f, _clockWise = _clockWise)

        circle(middlePoint, radius, referenceVector, angleValue.toFloat(), _clockWise = _clockWise)
        writeText("$angleValue", textPosition)
    }

    fun rectangle(
        firstPoint: Point,
        secondPoint: Point,
        thirdPoint: Point,
        forthPoint: Point,
        _color: Int = color,
        lineType: Paint.Style? = Paint.Style.FILL,
        _thickness: Float = thickness
    ) {
        line(firstPoint, secondPoint, lineType, _color, _thickness)
        line(secondPoint, thirdPoint, lineType, _color, _thickness)
        line(thirdPoint, forthPoint, lineType, _color, _thickness)
        line(forthPoint, firstPoint, lineType, _color, _thickness)
    }
}
