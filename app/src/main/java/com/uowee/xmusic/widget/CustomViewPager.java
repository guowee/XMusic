package com.uowee.xmusic.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * ViewPager嵌套ViewPager，默认情况下，里层的ViewPager，是无法滑动的！
 * 默认情况下，外层的ViewPager，会拦截触屏事件（TouchEvent）。
 * <p>
 * 通过重写里层控件的onTouchEvent方法，调用其父控件的requestDisallowInterceptTouchEvent()方法；
 * 使其父控件，不拦截触屏事件（TouchEvent），以此，便能达到里层的ViewPager，也能滑动的效果了。
 */

public class CustomViewPager extends ViewPager {

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    PointF downPoint = new PointF();

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downPoint.x = ev.getX();
                downPoint.y = ev.getY();
                if (getChildCount() > 1) {
                    // 有内容，多于1个时, 通知其父控件，现在进行的是本控件的操作，不允许拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (this.getChildCount() > 1) { //有内容，多于1个时
                    //有内容，多于1个时,通知其父控件，现在进行的是本控件的操作，不允许拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                // 在UP时判断是否按下和松手的坐标为一个点
                if (PointF.length(ev.getX() - downPoint.x, ev.getY()
                        - downPoint.y) < (float) 5.0) {
                    if (mSingleTouchListener != null) {
                        mSingleTouchListener.onSingleTouch(this);
                    }
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    private OnSingleTouchListener mSingleTouchListener;

    public interface OnSingleTouchListener {
        void onSingleTouch(View view);
    }

    public void setSingleTouchListener(OnSingleTouchListener listener) {
        this.mSingleTouchListener = listener;
    }

}
