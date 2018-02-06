package com.dreamzone.mtime.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @author bohe
 * @ClassName: TestTouchLayout
 * @Description:
 * @date 2018/1/31 下午5:20
 */
public class TestTouchLayout extends LinearLayout {
    public TestTouchLayout(Context context) {
        super(context);
    }

    public TestTouchLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestTouchLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d("MTime", " TestTouchLayout ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("MTime", " TestTouchLayout ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.v("MTime", " TestTouchLayout ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("MTime", " TestTouchLayout ACTION_CANCEL");
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("MTime", " TestTouchLayout onInterceptTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("MTime", " TestTouchLayout onInterceptTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.v("MTime", " TestTouchLayout onInterceptTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("MTime", " TestTouchLayout onInterceptTouchEvent ACTION_CANCEL");
                break;
            default:
                break;
        }
//        Log.d("MTime", " TestTouchLayout onInterceptTouchEvent super.onInterceptTouchEvent(ev)=" + super.onInterceptTouchEvent(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d("MTime", " TestTouchLayout onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("MTime", " TestTouchLayout onTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.v("MTime", " TestTouchLayout onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("MTime", " TestTouchLayout ACTION_CANCEL");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
