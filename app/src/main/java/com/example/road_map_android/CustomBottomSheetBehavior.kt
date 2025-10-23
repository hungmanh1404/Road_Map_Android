package com.example.road_map_android

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CustomBottomSheetBehavior<V : View> : BottomSheetBehavior<V> {

    constructor() : super()

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    private var isPeekHeightSet = false

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: V,
        layoutDirection: Int
    ): Boolean {
        val result = super.onLayoutChild(parent, child, layoutDirection)

        if (!isPeekHeightSet) {
            child.post {
                val contentHeight = child.measuredHeight
                val maxHeight = parent.height
                // Đặt nấc đầu tiên (peekHeight) theo content
                peekHeight = contentHeight
                // Gán layout params để có thể kéo lên full màn hình
                val layoutParams = child.layoutParams
                layoutParams.height = maxHeight
                child.layoutParams = layoutParams
                isPeekHeightSet = true
            }
        }

        return result
    }

    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: V, event: android.view.MotionEvent): Boolean {
        return super.onInterceptTouchEvent(parent, child, event)
    }

    override fun onTouchEvent(parent: CoordinatorLayout, child: V, event: android.view.MotionEvent): Boolean {
        return super.onTouchEvent(parent, child, event)
    }
}
