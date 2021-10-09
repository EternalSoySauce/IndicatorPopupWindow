package com.ess.indicator.sample

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.ess.indicator.popupwindow.IndicatorParams
import com.ess.indicator.popupwindow.IndicatorPopupWindow
import com.ess.indicator.sample.Utils.Companion.childView

class MainActivity : AppCompatActivity() {

    private fun setFullScreen(fullScreen: Boolean) {
        val window = window
        val decorView = window.decorView
        if (fullScreen) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            decorView.systemUiVisibility = decorView.systemUiVisibility or (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            decorView.systemUiVisibility = decorView.systemUiVisibility xor (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    private fun isFullScreen(): Boolean {
        return window.attributes.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN == WindowManager.LayoutParams.FLAG_FULLSCREEN
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val parent = findViewById<ViewGroup>(R.id.content_layout)
        parent.childView().forEach {
            if (it is Button && it !is SwitchCompat) {
                it.setOnClickListener {
                    val indicatorParams = IndicatorParams()
                        .text("撒大使发放机快乐环岛克拉斯疯狂了撒娇娇撒泼寄刀片搜安静的泼撒娇破的接撒破旧的坡按实际")
                        .contentLayout(R.layout.sample_tip_popup, R.id.content_textview)
                        .arrowWidth(Utils.dp2px(this, 14f))
                        .arrowHeight(Utils.dp2px(this, 5f))
                        .arrowOffset(if (getRelativeX() == IndicatorPopupWindow.RelativeX.MIDDLE) 0 else Utils.dp2px(this, 12f))
                        .bgColor(Color.parseColor("#FFE030"))
                        .bgRadius(Utils.dp2px(this, 8f))
                        .marginScreenHor(Utils.dp2px(this, 24f))
//                        .marginScreenVer(Utils.dp2px(this, 24f))
                    val popupWindow = IndicatorPopupWindow(this, indicatorParams)
                    popupWindow.showAtAnchorView(it, getRelativeX(), getRelativeY(), 0, 0, isFullScreen())
                }
            }
        }

        findViewById<SwitchCompat>(R.id.switch_fullscreen).setOnCheckedChangeListener { buttonView, isChecked ->
            setFullScreen(isChecked)
        }
    }

    private fun getRelativeX(): IndicatorPopupWindow.RelativeX {
        val radioGroup = findViewById<RadioGroup>(R.id.radio_group_x)
        radioGroup.childView().forEachIndexed { index, view ->
            if ((view as RadioButton).isChecked) {
                return IndicatorPopupWindow.RelativeX.values()[index]
            }
        }
        return IndicatorPopupWindow.RelativeX.values()[0]
    }

    private fun getRelativeY(): IndicatorPopupWindow.RelativeY {
        val radioGroup = findViewById<RadioGroup>(R.id.radio_group_y)
        radioGroup.childView().forEachIndexed { index, view ->
            if ((view as RadioButton).isChecked) {
                return IndicatorPopupWindow.RelativeY.values()[index]
            }
        }
        return IndicatorPopupWindow.RelativeY.values()[0]
    }
}