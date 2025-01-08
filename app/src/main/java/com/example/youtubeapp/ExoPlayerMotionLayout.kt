package com.example.youtubeapp

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout

class ExoPlayerMotionLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attributeSet, defStyleAttr) {

    var targetView: View? = null
    private val gestureDetector by lazy {
        // onScroll 이벤트를 감지하여 targetView가 터치된 영역을 포함하는지 확인하는 역할
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(
                e1: MotionEvent?, // 동작 첫번째 지점
                e2: MotionEvent, // 동작 두번째 지점
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                return targetView?.containTouchArea(e1!!.x.toInt(), e1.y.toInt()) ?: false
            }
        })
    }

    //true로 반환하면 더 아래로 터치가 내려가지 않음
    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        //event뷰가 null이 아닌 경우 gestureDetector로 터치 이벤트를 전달
        event?.let{
            return gestureDetector.onTouchEvent(event)
        } ?: return false
    }

    /**터치 지점이 영역에 속하는지 판단해주는 함수*/
    private fun View.containTouchArea(x: Int, y: Int): Boolean {
        return (x in this.left..this.right && y in this.top..this.bottom)
    }
}