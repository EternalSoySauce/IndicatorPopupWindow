# IndicatorPopupWindow
[![](https://jitpack.io/v/eternalsoysauce/IndicatorPopupWindow.svg)](https://jitpack.io/#eternalsoysauce/IndicatorPopupWindow)

### PopupWindow

从AnchorView各方向弹窗的自适应宽高的气泡弹窗


### 效果图

![EasyPopup](https://github.com/eternalsoysauce/IndicatorPopupWindow/images/sample.gif)

### 仓库依赖

Step 1. Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
    repositories {
	//...
	maven { url 'https://jitpack.io' }
    }
}
```
Step 2. Add the dependency
```gradle
dependencies {
    compile 'com.github.eternalsoysauce:IndicatorPopupWindow:VERSION_CODE'
}
```
最新的[VERSION_CODE](https://github.com/eternalsoysauce/IndicatorPopupWindow/releases)

### 使用方法

IndicatorPopupWindow继承自PopupWindow，创建后仍可自定义修改PopupWindow属性。

```kotlin
val indicatorParams = IndicatorParams()
	.text("文案内容")
	.contentLayout(R.layout.sample_tip_popup, R.id.content_textview)
	.arrowWidth(Utils.dp2px(this, 14f))
	.arrowHeight(Utils.dp2px(this, 5f))
	.arrowOffset(if (getRelativeX() == IndicatorPopupWindow.RelativeX.MIDDLE) 0 else Utils.dp2px(this, 12f))
	.bgColor(Color.parseColor("#FFE030"))
	.bgRadius(Utils.dp2px(this, 8f))
	.marginScreenHor(Utils.dp2px(this, 24f))
	.marginScreenVer(Utils.dp2px(this, 24f))
val popupWindow = IndicatorPopupWindow(this, indicatorParams)
popupWindow.showAtAnchorView(it, getRelativeX(), getRelativeY(), 0, 0, isFullScreen())
```

### 参数解释

**IndicatorParams**
```java
public class IndicatorParams {

    protected String text;					// 显示的文案
    protected int contentLayoutResId;		// 用于显示文案的layout
    protected int contentTextViewId;		// 用于显示文案内容的TextViewId

    protected int arrowWidth = 10;			// 气泡箭头宽度
    protected int arrowHeight = 10;			// 气泡箭头高度   
    protected int arrowOffset;				// 气泡箭头相对于contentLayout的偏移量
  
    protected int bgRadius;					// contentLayout的圆角
    protected int bgColor = Color.WHITE;	// contentLayout和箭头的颜色
    protected int marginScreenHor;			// 屏幕横向预留边距
    protected int marginScreenVer;			// 屏幕纵向预留边距
}
```

**showAtAnchorView**

```kotlin
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
)
```

### License

```
Copyright 2021 eternalsoysauce

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
