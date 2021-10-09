package com.ess.indicator.popupwindow

import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.annotation.IntDef
import androidx.annotation.IntRange

class TriangleDrawable(@param:ArrowDirection private val mArrowDirection: Int, color: Int) : Drawable() {

    @IntDef(TOP, BOTTOM, LEFT, RIGHT)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class ArrowDirection

    companion object {
        const val TOP = 1
        const val BOTTOM = 2
        const val LEFT = 3
        const val RIGHT = 4
    }

    private val mPaint: Paint = Paint()

    init {
        mPaint.isAntiAlias = true
        mPaint.color = color
        mPaint.style = Paint.Style.FILL
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeCap = Paint.Cap.ROUND
    }

    override fun draw(canvas: Canvas) {
        val path = createPath()
        canvas.drawPath(path, mPaint)
    }

    private fun createPath(): Path {
        val bound = bounds
        val path = Path()
        if (mArrowDirection == TOP) {
            path.moveTo(bound.right / 2f, 0f)
            path.lineTo(0f, bound.bottom.toFloat())
            path.lineTo(bound.right.toFloat(), bound.bottom.toFloat())
            path.close()
        } else if (mArrowDirection == BOTTOM) {
            path.moveTo(bound.right / 2f, bound.bottom.toFloat())
            path.lineTo(0f, 0f)
            path.lineTo(bound.right.toFloat(), 0f)
            path.close()
        } else if (mArrowDirection == LEFT) {
            path.moveTo(0f, bound.bottom / 2f)
            path.lineTo(bound.right.toFloat(), 0f)
            path.lineTo(bound.right.toFloat(), bound.bottom.toFloat())
            path.close()
        } else {
            path.moveTo(bound.right.toFloat(), bound.bottom / 2f)
            path.lineTo(0f, 0f)
            path.lineTo(0f, bound.bottom.toFloat())
            path.close()
        }
        return path
    }

    override fun setAlpha(@IntRange(from = 0, to = 255) alpha: Int) {}
    override fun setColorFilter(colorFilter: ColorFilter?) {}
    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }

}