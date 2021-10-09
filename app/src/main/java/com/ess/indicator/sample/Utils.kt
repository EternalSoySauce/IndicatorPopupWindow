package com.ess.indicator.sample

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup

class Utils {

    companion object {
        fun dp2px(context: Context, dp: Float): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.resources.displayMetrics
            ).toInt()
        }

        fun ViewGroup.childView(): ArrayList<View> {
            val list = arrayListOf<View>()
            val count = childCount
            for (i in 0 until count) {
                list.add(getChildAt(i))
            }
            return list
        }
    }

}