package com.dreamzone.mtime;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dreamzone.mtime.base.BaseActivity;
import com.matrix.appsdk.image.FastBlurUtil;
import com.matrix.appsdk.utils.BitmapUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FastBlurActivity extends BaseActivity {
    private final static String TAG = "blur";

    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_ratio)
    TextView tvRatio;
    @BindView(R.id.sb_ratio)
    SeekBar sbRatio;
    private String imgPath;
    private Bitmap mOriginBmp;
    private Bitmap mScaledBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastblur);
        ButterKnife.bind(this);
//        imgPath = "jurassic_world.jpg";
        imgPath = "yuan.jpg";
        int scaleRatio = 10;

        mOriginBmp = BitmapUtils.getImageFromAssetsFile(this, imgPath);
        mScaledBitmap = Bitmap.createScaledBitmap(mOriginBmp,
                mOriginBmp.getWidth() / scaleRatio,
                mOriginBmp.getHeight() / scaleRatio,
                false);

        ivImg.setImageBitmap(mOriginBmp);
        sbRatio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i(TAG, "progress is " + progress);
                fastBlurByRatio(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Log.d("MTime", " tvRatio.isHardwareAccelerated() = " + tvRatio.isHardwareAccelerated());
    }

    private void fastBlurByRatio(int radius){
        long start = System.currentTimeMillis();
//        Bitmap scaledBitmap = mOriginBmp.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap blurBitmap = FastBlurUtil.doBlur(mScaledBitmap, radius, true);
        Log.i(TAG, "radius is " + radius + ", time consume is =" + String.valueOf(System.currentTimeMillis() - start) );
        ivImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ivImg.setImageBitmap(blurBitmap);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, FastBlurActivity.class);
        context.startActivity(intent);
    }

}
