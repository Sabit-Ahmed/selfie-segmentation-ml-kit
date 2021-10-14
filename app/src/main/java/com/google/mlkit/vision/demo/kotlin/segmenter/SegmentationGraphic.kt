/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.mlkit.vision.demo.kotlin.segmenter

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.util.Log
import androidx.annotation.ColorInt
import com.google.mlkit.vision.demo.GraphicOverlay
import com.google.mlkit.vision.segmentation.SegmentationMask
import java.nio.BufferUnderflowException
import java.nio.ByteBuffer

/** Draw the mask from SegmentationResult in preview.  */
class SegmentationGraphic(overlay: GraphicOverlay, segmentationMask: SegmentationMask) :
    GraphicOverlay.Graphic(overlay) {
    private val mask: ByteBuffer
    private val maskWidth: Int
    private val maskHeight: Int
    private val isRawSizeMaskEnabled: Boolean
    private val scaleX: Float
    private val scaleY: Float

    /** Draws the segmented background on the supplied canvas.  */
    override fun draw(canvas: Canvas) {
        val bitmap = Bitmap.createBitmap(
            maskColorsFromByteBuffer(mask), maskWidth, maskHeight, Bitmap.Config.ARGB_8888
        )
//        Log.d("bitmap", "bitmap:: ${checkBgByAlphaValue(bitmap)}")
//        Log.d("bitmap", "bitmap:: ${bitmap.getColor(0, 0).alpha()}")
        if (isRawSizeMaskEnabled) {
            val matrix = Matrix(getTransformationMatrix())
            matrix.preScale(scaleX, scaleY)
            canvas.drawBitmap(bitmap, matrix, null)
        } else {
            canvas.drawBitmap(bitmap, getTransformationMatrix(), null)
        }
        bitmap.recycle()
        // Reset byteBuffer pointer to beginning, so that the mask can be redrawn if screen is refreshed
        mask.rewind()
    }

    /** Converts byteBuffer floats to ColorInt array that can be used as a mask.  */
    @ColorInt
    private fun maskColorsFromByteBuffer(byteBuffer: ByteBuffer): IntArray {
        @ColorInt val colors =
            IntArray(maskWidth * maskHeight)
        var totalConfidence: Float = 0f
        for (i in 0 until maskWidth * maskHeight) {
            val byteBufferValue = byteBuffer.float
            totalConfidence += byteBufferValue
            val backgroundLikelihood = 1 - byteBufferValue
            if (backgroundLikelihood > 0.9) {
                colors[i] = Color.argb(255, 64, 0, 255)
            } else if (backgroundLikelihood > 0.2) {
                // Linear interpolation to make sure when backgroundLikelihood is 0.2, the alpha is 0 and
                // when backgroundLikelihood is 0.9, the alpha is 128.
                // +0.5 to round the float value to the nearest int.
                val alpha = (182.9 * backgroundLikelihood - 36.6 + 0.5).toInt()
                colors[i] = Color.argb(alpha, 64, 0, 255)
            }
        }
//        Log.d("confidence", "confidence:: $totalConfidence")
        if (totalConfidence < 15000) {
            Log.d("confidence", "$totalConfidence:: Please come forward")
        }

        return colors
    }

//    fun checkBackground(): Float {
//        var totalConfidence: Float = 0f
//
//        for (y in 0..maskHeight) {
//            for (x in 0..maskWidth) {
//                // Gets the confidence of the (x,y) pixel in the mask being in the foreground
//                try {
//                    val foregroundConfidence = mask.float
//                    totalConfidence += foregroundConfidence
////                    Log.d("Confidence", "${mask.get(y * maskWidth + x)}")
//                } catch (e: BufferUnderflowException) {
//                    Log.d("UnderFlowException", "$e")
//                }
//            }
//        }
//        Log.d("ConfidenceRewind", "=================== Rewind ======================")
//        mask.rewind()
////        Log.d("maskSize", "$maskWidth ,  $maskHeight")
//        return totalConfidence
//    }
//
//    fun checkBgByAlphaValue(bitmap: Bitmap): Float {
//        var sumOfAlpha: Float = 0f
//
//        for (y in 0..maskHeight-1) {
//            for (x in 0..maskWidth-1) {
//                // Gets the confidence of the (x,y) pixel in the mask being in the foreground
//                try {
//                    sumOfAlpha += (1 - bitmap.getColor(x, y).alpha())
//                } catch (e: BufferUnderflowException) {
//                    Log.d("UnderFlowException", "$e")
//                }
//            }
//        }
//
//        return sumOfAlpha
//    }

    init {
        mask = segmentationMask.buffer
        maskWidth = segmentationMask.width
        maskHeight = segmentationMask.height
        isRawSizeMaskEnabled =
            maskWidth != overlay.getImageWidth() || maskHeight != overlay.getImageHeight()
        scaleX = overlay.getImageWidth() * 1f / maskWidth
        scaleY = overlay.getImageHeight() * 1f / maskHeight
    }
}
