package com.mymedicalhub.emma.rom.poseModel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import androidx.camera.core.ImageProxy
import com.mymedicalhub.emma.rom.maskModel.BitmapUtils
import com.mymedicalhub.emma.rom.poseModel.data.Device
import com.mymedicalhub.emma.rom.poseModel.ml.MoveNet
import com.mymedicalhub.emma.rom.poseModel.ml.PoseDetector
import com.mymedicalhub.emma.rom.poseModel.core.Draw
import com.mymedicalhub.emma.rom.poseModel.core.ImageUtils
import com.mymedicalhub.emma.rom.poseModel.core.VisualizationUtils
import com.mymedicalhub.emma.rom.romExercise.data.Point

class PoseEstimationProcessor {
    private var device = Device.CPU
    private var poseDetector: PoseDetector? = null
    private val minConfidence = .2f

    @SuppressLint("UnsafeOptInUsageError")
    fun posStart(imageProxy: ImageProxy){
        val bitmap = BitmapUtils.getBitmap(imageProxy)
        processImage(bitmap!!)
    }

    private fun processImage(bitmap: Bitmap) {
        var score = 0f
        var outputBitmap = bitmap
        Log.d("bitmap","$poseDetector")
        // run detect pose
        // draw points and lines on original image
        poseDetector?.estimateSinglePose(bitmap)?.let { person ->
            score = person.score
            Log.d("score","Person Bitmap: $score")
            if (score > minConfidence) {
                val height = bitmap.height
                val width = bitmap.width

                outputBitmap = VisualizationUtils.drawBodyKeyPoints(bitmap)
            }
        }
    }
    fun createPoseEstimator(context: Context) {
//        val output = input.bitmapInternal!!.copy(input.bitmapInternal!!.config, true)//copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas()
        poseDetector?.close()
        poseDetector = null
        poseDetector = MoveNet.create(context, device)
        Log.d("poseModel", "Done Pose Model")
        val draw = Draw(canvas, Color.WHITE, 2f)
        draw.writeText(
            "Hello pose",
            Point(500f, 500f),
            Color.rgb(19, 93, 148),//blue
            65f
        )
    }
}