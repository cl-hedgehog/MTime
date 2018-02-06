package com.dreamzone.mtime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamzone.mtime.base.AppContext;
import com.dreamzone.mtime.base.BaseActivity;
import com.dreamzone.mtime.common.AppSPUtils;
import com.matrix.appsdk.utils.CountDownTimerUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author BMR
 * @ClassName: AdvertisementActivity
 * @Description: 广告展示页面 可以跳过
 * 首次进入app不显示广告，防止下载过慢导致页面Image显示交替时的splash
 * @date 2016/1/28 11:50
 */
public class AdvertisementActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_advertisement)
    ImageView ivAdvertisement;
    @BindView(R.id.tv_skip)
    TextView tvSkip;

    private String adImgUrl = null;
    private final static String EXTRA_IMG_URL = "extra_img_url";
    private final static long SKIP_TIMER_TOTAL = 5000; // 5s
    private final static long SKIP_TIMER_INTERVAL = 1000; // 1s


    public static void start(Context context, String imgUrl) {
        Intent intent = new Intent(context, AdvertisementActivity.class);
        intent.putExtra(EXTRA_IMG_URL, imgUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        ButterKnife.bind(this);
        adImgUrl = getIntent().getStringExtra(EXTRA_IMG_URL);

        loadAdImg();
        // 首次使用app不显示广告，仅下载，防止下载过慢导致页面Image显示交替时的splash
        if(!AppSPUtils.getSPInstance().readIsOpenedFromSp()){
            gotoMainActivity();
            AppSPUtils.getSPInstance().saveIsOpenedToSp(true);
        }else{
            skipCounter.start();
            tvSkip.setOnClickListener(this);
        }


    }
    private void loadAdImg(){
        ImageLoader.getInstance().displayImage(adImgUrl, ivAdvertisement, AppContext.adImageDisplayOptions);
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private CountDownTimerUtils skipCounter = new CountDownTimerUtils(SKIP_TIMER_TOTAL, SKIP_TIMER_INTERVAL) {
        @Override
        public void onTick(long millisUntilFinished) {
            int curr = (int) (millisUntilFinished / 1000);
            tvSkip.setText(getString(R.string.ad_skip, " " + curr));
        }

        @Override
        public void onFinish() {
            gotoMainActivity();
        }
    };


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tv_skip){
            gotoMainActivity();
        }
    }

    @Override
    protected void onDestroy() {
        skipCounter.cancel();
        super.onDestroy();

    }
}
