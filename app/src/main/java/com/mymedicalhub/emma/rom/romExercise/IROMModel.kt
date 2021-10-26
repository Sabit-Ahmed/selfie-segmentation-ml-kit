package com.mymedicalhub.emma.rom.romExercise


import com.mymedicalhub.emma.rom.romExercise.data.MaskData
import com.mymedicalhub.emma.rom.romExercise.data.MaskDetails
import com.google.mlkit.vision.segmentation.SegmentationMask
import java.nio.ByteBuffer

interface IROMModel{
    fun getModelMask(modelMask: SegmentationMask) : List<MaskData>
    fun getMaskData(maskHeight:Int, maskWidth:Int, mask:ByteBuffer) : List<MaskDetails>
}