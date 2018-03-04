package com.dreamzone.mtime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamzone.mtime.base.BaseActivity;
import com.dreamzone.mtime.common.TestSingle;
import com.dreamzone.mtime.robust.PatchManipulateImp;
import com.dreamzone.mtime.robust.RobustCallBackSample;
import com.dreamzone.mtime.view.DrawCircleView;
import com.dreamzone.mtime.view.TestTouchLayout;
import com.meituan.robust.PatchExecutor;

import java.util.ArrayList;
import java.util.List;

public class TestDemoActivity extends BaseActivity {

    private DrawCircleView mCircleView;
    TextView view;
    TextView view1;
    private TestTouchLayout mLayout;
    private List<ImageView> list = new ArrayList<>();
    static MemoryLeak memoryLeak;
    class MemoryLeak {
        void doSomeThing() {
            System.out.println("Wheee!!!");
        }
    }


    public static void start(Context context, int flag) {
        Intent intent = new Intent(context.getApplicationContext(), TestDemoActivity.class);
        intent.addFlags(flag);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, TestDemoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        TestSingle testSingle = TestSingle.getTestSingle(this);
//        memoryLeak = new MemoryLeak();
        Log.d("MTime", "TestDemoActivity onCreate = " + this.hashCode());

        mCircleView = findViewById(R.id.dot_view);
        view = findViewById(R.id.tv_test);
        view1= findViewById(R.id.tv_test1);
        mLayout = findViewById(R.id.touch_layout);
//        finish();
        //RotatePhotoActivity.start(this);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("MTime", "run");
//                view.setText("chage chage");
//            }
//        }).start();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotatePhotoActivity.start(TestDemoActivity.this);
//                Log.d("MTime", "Leak send");
//                for (int i = 0; i < 10000; i++) {
//                    ImageView imageView = new ImageView(TestDemoActivity.this);
//                    list.add(imageView);
//                }
            }
        });

        view1.setText("Patch");
        view1.setOnClickListener(
                //RobustModify.modify();
                v -> patch());

        Log.d("MTime", "TestDemoActivity view.isHardwareAccelerated() = " + view.isHardwareAccelerated());
//        LeakThread leakThread = new LeakThread();
//        leakThread.start();
    }

    private void patch(){
        new PatchExecutor(getApplicationContext(), new PatchManipulateImp(), new RobustCallBackSample()).start();
    }

    class LeakThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(60 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action  = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                Log.d("MTime", " TestDemoActivity ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("MTime", " TestDemoActivity ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.v("MTime", " TestDemoActivity ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("MTime", " TestDemoActivity ACTION_CANCEL");
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("MTime", " TestDemoActivity onTouchEvent");
        return true;
    }

    private void test() {
        float a1=getResources().getDimension(R.dimen.activity_vertical_margin1);
        int a2=getResources().getDimensionPixelOffset(R.dimen.activity_vertical_margin1);
        int a3=getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin1);

        float b1=getResources().getDimension(R.dimen.activity_vertical_margin2);
        int b2=getResources().getDimensionPixelOffset(R.dimen.activity_vertical_margin2);
        int b3=getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin3);

        float c1=getResources().getDimension(R.dimen.activity_vertical_margin3);
        int c2=getResources().getDimensionPixelOffset(R.dimen.activity_vertical_margin3);
        int c3=getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin3);

        Log.i("test", "getDimension= "+a1+", getDimensionPixelOffset="+a2+",getDimensionPixelSize="+a3);
        Log.i("test", "getDimension= "+b1+", getDimensionPixelOffset="+b2+",getDimensionPixelSize="+b3);
        Log.i("test", "getDimension= "+c1+", getDimensionPixelOffset="+c2+",getDimensionPixelSize="+c3);
    }




    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MTime", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MTime", "onResume");

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("MTime", "run222");
//                view.setText("chage okokokok");
//            }
//        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MTime", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MTime", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MTime", "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MTime", "onRestart");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MTime", "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("MTime", "onRestoreInstanceState");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        Log.d("MTime", "onRestoreInstanceState PersistableBundle");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("MTime", "omNewIntent");
    }
}
