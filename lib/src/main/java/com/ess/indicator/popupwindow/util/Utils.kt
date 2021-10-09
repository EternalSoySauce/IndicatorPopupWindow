package com.ess.indicator.popupwindow.util

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.view.View
import android.view.WindowManager

internal class Utils {

    companion object {
        fun getScreenSize(context: Context): IntArray {
            if (context is Activity) {
                val outRect = Rect()
                context.window.decorView.getWindowVisibleDisplayFrame(outRect)
                return intArrayOf(outRect.width(), outRect.height())
            } else {
                val point = Point()
                (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                    .defaultDisplay.getSize(point)
                return intArrayOf(point.x, point.y)
            }
        }

        fun View.getLocationOnWindow(): IntArray {
            val array = IntArray(2)
            getLocationInWindow(array)
            return array
        }

        fun getStatusBarHeight(context: Context): Int {
            val resources = context.applicationContext.resources
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                return resources.getDimensionPixelSize(resourceId)
            } else {
                return 0
            }
        }
    }

}