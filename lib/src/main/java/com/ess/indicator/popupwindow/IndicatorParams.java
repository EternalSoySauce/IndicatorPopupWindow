package com.ess.indicator.popupwindow;

import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

public class IndicatorParams {

    // 显示的文案
    protected String text;
    // 用于显示文案的layout，包含TextView
    protected int contentLayoutResId;
    protected int contentTextViewId;

    // 箭头尺寸
    protected int arrowWidth = 10;
    protected int arrowHeight = 10;
    // 箭头相对于contentLayout的偏移量
    protected int arrowOffset;

    // contentLayout的圆角
    protected int bgRadius;
    // contentLayout和箭头的颜色
    protected int bgColor = Color.WHITE;
    // 屏幕横向预留边距
    protected int marginScreenHor;
    // 屏幕纵向预留边距
    protected int marginScreenVer;

    public IndicatorParams text(String text) {
        this.text = text;
        return this;
    }

    public IndicatorParams contentLayout(@LayoutRes int layoutResId, @IdRes int textViewId) {
        this.contentLayoutResId = layoutResId;
        this.contentTextViewId = textViewId;
        return this;
    }

    public IndicatorParams bgColor(@ColorInt int color) {
        this.bgColor = color;
        return this;
    }

    public IndicatorParams bgRadius(int radius) {
        this.bgRadius = radius;
        return this;
    }

    public IndicatorParams arrowWidth(int width) {
        this.arrowWidth = width;
        return this;
    }

    public IndicatorParams arrowHeight(int height) {
        this.arrowHeight = height;
        return this;
    }

    public IndicatorParams arrowOffset(int offset) {
        this.arrowOffset = offset;
        return this;
    }

    public IndicatorParams marginScreenHor(int marginScreenHor) {
        this.marginScreenHor = marginScreenHor;
        return this;
    }

    public IndicatorParams marginScreenVer(int marginScreenVer) {
        this.marginScreenVer = marginScreenVer;
        return this;
    }

}
