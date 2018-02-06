package com.dreamzone.mtime;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.dreamzone.mtime.base.BaseActivity;
import com.dreamzone.mtime.view.RotatePhotoView;
import com.matrix.appsdk.utils.Tools;



public class RotatePhotoActivity extends BaseActivity {


    //    @BindView(R.id.rotate_view)
    RotatePhotoView rotateView;
    //    @BindView(R.id.tv_content)
    TextView tvContent;
    //    @BindView(R.id.tv_rotate)
    Button tvRotate;
    //    @BindView(R.id.tv_rotate2)
    Button tvRestore;
    private int mDegree;

    private int screenWidth;

    public static void start(Context context) {
        Intent intent = new Intent(context, RotatePhotoActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, int flag) {
        Intent intent = new Intent(context, RotatePhotoActivity.class);
        intent.addFlags(flag);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate_photo);
        rotateView = findViewById(R.id.rotate_view);
        tvRotate = findViewById(R.id.tv_rotate);
        tvRestore = findViewById(R.id.tv_rotate2);
        tvContent = findViewById(R.id.tv_content);
        //        ButterKnife.bind(this);
        screenWidth = Tools.getScreenWidth(this);
        Log.d("MTime", "RotatePhotoActivity onCreate = " + this.hashCode());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_welcome);
        rotateView.setBitmap(bitmap);
        tvRotate.setOnClickListener(v -> {
//            RobustModify.modify();
            rotate();
        });

//        tvRestore.setOnClickListener(v -> {
////            RobustModify.modify();
////            addNew();
//        });
    }

//    @Modify
    private void rotate() {
        mDegree = (mDegree + 90) % 360;
        rotateView.setRotation(mDegree);
        tvContent.setText(mDegree+"");
//        tvContent.setText(mDegree + " robust fix");
    }

//    @Add
//    private void addNew() {
//        tvRestore.setText("RotateFix");
//    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("MTime", "RotatePhotoActivity onNewIntent");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void openOptionsMenu() {
        super.openOptionsMenu();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
