package com.google.mlkit.vision.demo.romExercise


import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.demo.romExercise.data.MaskData
import com.google.mlkit.vision.demo.romExercise.data.MaskDetails
import com.google.mlkit.vision.segmentation.SegmentationMask
import java.nio.ByteBuffer

interface IROMModel{
    fun getModelMask(modelMask: SegmentationMask) : List<MaskData>
    fun getMaskData(modelHeight:Int, modelWeight:Int, modelMask:ByteBuffer) : List<MaskDetails>
}