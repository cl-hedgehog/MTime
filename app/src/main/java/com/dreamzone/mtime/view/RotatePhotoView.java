package com.dreamzone.mtime.view;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

/**
 * @author bohe
 * @ClassName: RotatePhoto
 * @Description:
 * @date 2017/9/4 下午8:46
 */
@SuppressLint("AppCompatCustomView")
public class RotatePhotoView extends ImageView {

    private int mDegree;
    private RectF mRect1;
    private Bitmap mBitmapOri;
    private Bitmap mBitmap;
    private ValueAnimator mValueAnimator;
    private int mAnimDegree;
    private float mWHRatio;

    public RotatePhotoView(Context context) {
        this(context, null);
    }

    public RotatePhotoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotatePhotoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.parseColor("#ff333333"));
        setScaleType(ScaleType.MATRIX);
    }

    private void initAnim() {
        mValueAnimator = ValueAnimator.ofInt(mLastDegree, mDegree);
        mValueAnimator.addUpdateListener(animation -> {
            mAnimDegree = (int) animation.getAnimatedValue();
            Log.d("RotateView", "mAnimDegree = " + mAnimDegree);
            invalidate();
        });
        mValueAnimator.start();
    }

    int stepDegree;
    float syAll;
    public void setRotation(int degree){
        mLastDegree = mDegree;
        if(degree == 270){
            degree = -90;
            mLastDegree = 0;
        }
        mDegree = degree;
        stepDegree = (mDegree - mLastDegree)/(count-1);
        syAll = viewWidth * 1f/bmpHeight;
        i = 1;
        initAnim();

    }

    private void onRotationChange() {
        Animation rotateAnimation  = new RotateAnimation(mLastDegree, mDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation
                .RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        startAnimation(rotateAnimation);
    }

    private void onScaleChange() {
        int w = viewWidth;
        int h = viewHeight;
        if(mDegree == 90 || mDegree == 270){
            w = viewHeight;
            h = viewWidth;
        }
        Animation scale  = new ScaleAnimation(1f, w*1f/viewWidth, 1f, h*1f/viewHeight, Animation
                .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setFillAfter(true);
        scale.setInterpolator(new LinearInterpolator());


        Animation rotateAnimation  = new RotateAnimation(mLastDegree, mDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation
                .RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new LinearInterpolator());

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(scale);
        set.addAnimation(rotateAnimation);
        set.setDuration(1000);
        set.setRepeatCount(0);
        set.setFillAfter(true);
        startAnimation(set);
    }

    public void setBitmap(Bitmap bitmap){
        mBitmapOri = bitmap;
        mOriMatrix = getMatrix();
        mMatrix= new Matrix();
        bmpWidth = mBitmapOri.getWidth();
        bmpHeight = mBitmapOri.getHeight();
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        adjustCenterInside();
        canvas.save();
        mMatrix.reset();
        if(mDegree == 90 || mDegree == -90){
            float dstWidth, dstHeight;
            dstHeight = viewWidth*1f;
            dstWidth = (dstHeight * mWHRatio);
            float sx = dstWidth/viewWidth;
            float sy = dstHeight/viewHeight;
            mMatrix.postScale(sx, sy, viewWidth/2, viewHeight/2);
        }
        mMatrix.postRotate(mAnimDegree, viewWidth/2, viewHeight/2);
        Log.d("RotateView", "viewWidth = " + viewWidth + ", viewHeight = " + viewHeight);
        Log.d("RotateView", "mDegree = " + mDegree + ", mLastDegree = " + mLastDegree);
        Log.d("RotateView", "mAnimDegree = " + mDegree + ", mMatrix = " + mMatrix);
        Log.d("RotateView", "canvas.hashCode = " + canvas.hashCode() + ",canvas  = " + canvas);
        canvas.drawBitmap(mBitmap, mMatrix, null);
        canvas.restore();
    }

    int viewWidth;
    int viewHeight;
    int bmpWidth;
    int bmpHeight;
    private void adjustCenterInside(){
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        if(mBitmap == null){
            float sx = 1f*viewWidth/bmpWidth;
            float sy = 1f*viewHeight/bmpHeight;
            mWHRatio = 1f*viewWidth/viewHeight;
            mMatrix.postScale(sx, sy, bmpWidth/2, bmpHeight/2);
            mBitmap = Bitmap.createBitmap(mBitmapOri, 0,0,bmpWidth, bmpHeight, mMatrix, true);
            setImageBitmap(mBitmap);
        }
        Log.d("RotateView", "getH = " + mBitmap.getHeight() + ", getW = " + mBitmap.getWidth());
    }

    private Matrix mMatrix;
    private int mLastDegree = 0;
    private void setMatrix(){
        mMatrix = new Matrix();
        mMatrix.reset();
//        if(mLastDegree <= mDegree){
//            mMatrix.postRotate(mLastDegree, 0, 0);
//            float sy = viewWidth * 1f / bmpHeight;
//            mMatrix.postScale(sy, sy, 0, 0);
//            mMatrix.postTranslate(viewWidth, 0);
//            mLastDegree += 2;
//            invalidate();
//        }
        mMatrix.postRotate(mDegree, 0, 0);
        if(mDegree == 90 || mDegree==270){
            float sy = viewWidth * 1f/bmpHeight;
            mMatrix.postScale(sy, sy, 0, 0);
        }
        if(mDegree == 90){
            mMatrix.postTranslate(viewWidth,0);
        }else if(mDegree == 270){
            mMatrix.postTranslate(0,viewWidth);

        }else if(mDegree == 180){
            mMatrix.postTranslate(viewWidth, viewHeight);
        }
    }

    Matrix mOriMatrix;

    private final int count = 10;
    private int rotate;
    private int i=1;
    public void setRotateAnim(){
        int rotate = mLastDegree + stepDegree * i;
        i++;
        mMatrix.set(mOriMatrix);
        if(mDegree == 90 || mDegree == -90){
            float sy = syAll * count/(i);
            mMatrix.postScale(sy, sy, bmpWidth/2, bmpHeight/2);
        }
        mMatrix.postRotate(rotate, bmpWidth/2, bmpHeight/2);
        setImageMatrix(mMatrix);

    }

    private void  anim(int start, int end){
        PropertyValuesHolder vh = PropertyValuesHolder.ofObject("Rotate", new RotateValueHolder(), start, end);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this, vh);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();

    }

    private static class RotateValueHolder implements TypeEvaluator<Integer>{

        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            int rotate =(int)(startValue + fraction*(endValue-startValue));
            return rotate%360;
        }
    }






}
