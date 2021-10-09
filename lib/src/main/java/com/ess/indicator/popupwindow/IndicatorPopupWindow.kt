package com.ess.indicator.popupwindow

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.ess.indicator.popupwindow.util.Utils
import com.ess.indicator.popupwindow.util.Utils.Companion.getLocationOnWindow

class IndicatorPopupWindow(context: Context, private val indicatorParams: IndicatorParams) :
    PopupWindow(context) {

    enum class RelativeX {
        LEFT, MIDDLE, RIGHT
    }

    enum class RelativeY {
        TOP, BOTTOM
    }

    init {
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isFocusable = false
        isTouchable = true
        isOutsideTouchable = true
    }

    /**
     * 默认按AnchorView右下方计算位置
     *
     * @param  view AnchorView
     * @param relativeX 水平相对AnchorView方向，左/中/右
     * @param relativeY 垂直相对AnchorView方向，上/下
     * @param offsetX 默认水平方向箭头和AnchorView中心对齐，offsetX为在此基础上的偏移量，relativeX为LEFT时向左偏移，为MIDDLE/RIGHT时向右偏移
     * @param offsetY 默认竖直方向箭头和AnchorView边距为0（紧挨着），offsetY为在此基础上的偏移量，relativeY为TOP时向上偏移，为BOTTOM时向下偏移
     * @param ignoreStatusBar 自适应计算弹窗高度时是否忽略statusBar高度（默认为false），建议全屏时设为true，否则为false
     */
    fun showAtAnchorView(
        view: View,
        relativeX: RelativeX = RelativeX.RIGHT,
        relativeY: RelativeY = RelativeY.BOTTOM,
        offsetX: Int = 0,
        offsetY: Int = 0,
        ignoreStatusBar: Boolean = false
    ) {
        val context = view.context
        val rootLayout = LinearLayout(context)
        val contentLayout = createContentLayout(context)
        val arrowView: View

        rootLayout.orientation = LinearLayout.VERTICAL
        when (relativeY) {
            RelativeY.TOP -> {
                arrowView = createArrowView(context, TriangleDrawable.BOTTOM)
                rootLayout.addView(contentLayout)
                rootLayout.addView(arrowView)
            }
            else -> {
                arrowView = createArrowView(context, TriangleDrawable.TOP)
                rootLayout.addView(arrowView)
                rootLayout.addView(contentLayout)
            }
        }
        arrowView.layoutParams.width = indicatorParams.arrowWidth
        arrowView.layoutParams.height = indicatorParams.arrowHeight

        rootLayout.measure(0, 0)
        val screenSize = Utils.getScreenSize(context)
        val statusBarHeight = if (ignoreStatusBar) 0 else Utils.getStatusBarHeight(context)
        val viewLocation = view.getLocationOnWindow()

        var realX: Int
        var realY: Int
        val arrowMargin = indicatorParams.arrowOffset
        val arrowLayoutParams = arrowView.layoutParams as LinearLayout.LayoutParams

        when (relativeX) {
            RelativeX.LEFT -> {
                while (true) {
                    arrowLayoutParams.leftMargin =
                        (contentLayout.measuredWidth - indicatorParams.arrowWidth - arrowMargin)
                            .coerceIn(0, contentLayout.measuredWidth - indicatorParams.arrowWidth)
                    realX =
                        viewLocation[0] + view.width / 2 - arrowLayoutParams.leftMargin - indicatorParams.arrowWidth / 2 - offsetX
                    if (contentLayout.layoutParams.width == 0) {
                        break
                    } else if (realX - indicatorParams.marginScreenHor < 0) {
                        contentLayout.layoutParams.width =
                            (contentLayout.measuredWidth - (indicatorParams.marginScreenHor - realX)).coerceAtLeast(
                                0
                            )
                        rootLayout.measure(0, 0)
                    } else {
                        break
                    }
                }
            }
            RelativeX.MIDDLE -> {
                while (true) {
                    arrowLayoutParams.leftMargin =
                        ((contentLayout.measuredWidth - indicatorParams.arrowWidth) / 2 + arrowMargin)
                            .coerceIn(0, contentLayout.measuredWidth - indicatorParams.arrowWidth)
                    realX =
                        viewLocation[0] + view.width / 2 - arrowLayoutParams.leftMargin - indicatorParams.arrowWidth / 2 + offsetX
                    if (contentLayout.layoutParams.width == 0) {
                        break
                    } else if (realX - indicatorParams.marginScreenHor < 0) {
                        contentLayout.layoutParams.width =
                            (contentLayout.measuredWidth - (indicatorParams.marginScreenHor - realX)).coerceAtLeast(
                                0
                            )
                        rootLayout.measure(0, 0)
                    } else if (realX + contentLayout.measuredWidth + indicatorParams.marginScreenHor > screenSize[0]) {
                        contentLayout.layoutParams.width =
                            (contentLayout.measuredWidth - (realX + contentLayout.measuredWidth + indicatorParams.marginScreenHor - screenSize[0])).coerceAtLeast(
                                0
                            )
                        rootLayout.measure(0, 0)
                    } else {
                        break
                    }
                }
            }
            else -> {
                arrowLayoutParams.leftMargin = arrowMargin.coerceIn(
                    0,
                    contentLayout.measuredWidth - indicatorParams.arrowWidth
                )
                realX =
                    viewLocation[0] + view.width / 2 - arrowLayoutParams.leftMargin - indicatorParams.arrowWidth / 2 + offsetX
                if (realX + contentLayout.measuredWidth + indicatorParams.marginScreenHor > screenSize[0]) {
                    contentLayout.layoutParams.width =
                        (contentLayout.measuredWidth - (realX + contentLayout.measuredWidth + indicatorParams.marginScreenHor - screenSize[0])).coerceAtLeast(
                            0
                        )
                    rootLayout.measure(0, 0)
                }
            }
        }

        when (relativeY) {
            RelativeY.TOP -> {
                while (true) {
                    realY =
                        viewLocation[1] - contentLayout.measuredHeight - indicatorParams.arrowHeight - offsetY
                    if (contentLayout.layoutParams.height == 0) {
                        break
                    } else if (realY - statusBarHeight - indicatorParams.marginScreenVer < 0) {
                        contentLayout.layoutParams.height =
                            (contentLayout.measuredHeight - (indicatorParams.marginScreenVer + statusBarHeight - realY)).coerceAtLeast(
                                0
                            )
                        rootLayout.measure(0, 0)
                    } else {
                        break
                    }
                }
            }
            else -> {
                realY = viewLocation[1] + view.height + offsetY
                val bottomPos =
                    realY + contentLayout.measuredHeight + indicatorParams.arrowHeight - statusBarHeight + indicatorParams.marginScreenVer
                if (bottomPos > screenSize[1]) {
                    contentLayout.layoutParams.height =
                        (contentLayout.measuredHeight - (bottomPos - screenSize[1])).coerceAtLeast(0)
                }
            }
        }

        contentView = rootLayout
        showAtLocation(view, Gravity.TOP or Gravity.START, realX, realY)
    }

    private fun createArrowView(
        context: Context,
        @TriangleDrawable.ArrowDirection arrowDirection: Int
    ): View {
        val view = View(context)
        view.background = TriangleDrawable(arrowDirection, indicatorParams.bgColor)
        return view
    }

    private fun createContentLayout(context: Context): ViewGroup {
        val drawable = GradientDrawable()
        drawable.setColor(indicatorParams.bgColor)
        drawable.cornerRadius = indicatorParams.bgRadius.toFloat()

        val contentLayout = FrameLayout(context)
        contentLayout.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        contentLayout.background = drawable

        val textView: TextView
        if (indicatorParams.contentLayoutResId != 0 && indicatorParams.contentTextViewId != 0) {
            val contentView = LayoutInflater.from(context)
                .inflate(indicatorParams.contentLayoutResId, null, false)
            textView = contentView.findViewById(indicatorParams.contentTextViewId)
            contentLayout.addView(contentView)
        } else {
            textView = TextView(context)
            contentLayout.addView(textView)
        }
        textView.text = indicatorParams.text
        return contentLayout
    }

}