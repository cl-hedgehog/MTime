package com.dreamzone.mtime.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author bohe
 * @ClassName: DrawCircleView
 * @Description:
 * @date 2017/12/2 上午10:46
 */
public class DrawCircleView extends View {
    //初始化圆的位置
    public float currentX=40;
    public float currentY=50;
    Paint paint;
    public DrawCircleView(Context context) {
        this(context,null);
    }

    public DrawCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context,null,0);
    }

    public DrawCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //创建画笔
        paint= new Paint();
        //填充颜色
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //开始画圆，float cx开始x的位置, float cy开始Y的位置, float radius圆的半径, @NonNull Paint paint画笔
        canvas.drawCircle(currentX,currentY,30,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //getX获得点击x位置，getY获得点击Y的位置
//        currentX= event.getX();
//        currentY=event.getY();
//        //重绘自身
//        invalidate();
//        //返回true自身消费
        boolean res = false;
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d("MTime", " DrawCircleView onTouchEvent ACTION_DOWN");
                res = false;
                break;
            case MotionEvent.ACTION_UP:
                Log.d("MTime", " DrawCircleView onTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.v("MTime", " DrawCircleView onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("MTime", " DrawCircleView ACTION_CANCEL");
                break;
            default:
                break;
        }
        return res;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }
}
