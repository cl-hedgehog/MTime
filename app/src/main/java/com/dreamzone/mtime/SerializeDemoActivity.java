package com.dreamzone.mtime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dreamzone.mtime.base.BaseActivity;
import com.dreamzone.mtime.bean.GalleryItem;
import com.dreamzone.mtime.bean.MediaBean;
import com.dreamzone.mtime.bean.PhotoBean;
import com.dreamzone.mtime.bean.Student;
import com.dreamzone.mtime.view.RotatePhotoView;
import com.matrix.appsdk.utils.Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class SerializeDemoActivity extends BaseActivity {


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
        Intent intent = new Intent(context, SerializeDemoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate_photo);
        rotateView = findViewById(R.id.rotate_view);
        tvRotate = findViewById(R.id.tv_rotate);
        tvRestore = findViewById(R.id.tv_rotate2);
//        ButterKnife.bind(this);
        initFolder();
        screenWidth = Tools.getScreenWidth(this);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_welcome);
        rotateView.setBitmap(bitmap);
//        tvRotate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDegree = (mDegree+90)%360;
//                rotateView.setRotation(mDegree);
//                tvContent.setText(mDegree+"");
//
//            }
//        });

        tvRotate.setOnClickListener(e->{
                 saveToSp();
                 getCachePath(SerializeDemoActivity.this);
                 Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
                }
        );
        tvRestore.setOnClickListener(e->restoreFromSp());

    }

    /**
     * 获取app缓存路径
     * @param context
     * @return
     */
    private String getCachePath(Context context ){
        String cachePath = null;
        File file = null;
        boolean isSDMounted = false;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //外部存储可用
            file = context.getExternalCacheDir();
            isSDMounted = true;
        }
        if(file == null) {
            //外部存储不可用
            file = context.getCacheDir();
        }
        if(file != null){
            cachePath = file.getPath();

        }else{
            // 用sd卡外部存储
            if (isSDMounted) {
                try {
                    cachePath = Environment.getExternalStorageDirectory().getPath();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        try{
            Log.e("test", " Build.VERSION = " +  Build.VERSION.SDK_INT);
            Log.e("test", "context.getExternalCacheDir() is " + context.getExternalCacheDir().getPath());
            Log.e("test", "context.getExternalFilesDir() is " + context.getExternalFilesDir(null).getPath());
            Log.e("test", "context.getExternalFilesDir()00 is " + context.getExternalFilesDirs(null)[0].getPath());
            Log.e("test", "context.getExternalFilesDir()11 is " + context.getExternalFilesDirs(null)[1].getPath());
            Log.e("test", "context.getCacheDir() is " + context.getCacheDir().getPath());
            Log.e("test", "context.getFilesDir() is " + context.getFilesDir().getPath());
            Log.e("test", "Environment.getExternalStorageDirectory() is " + Environment.getExternalStorageDirectory().getPath());
            Log.e("test", "Environment.DIRECTORY_MUSIC is " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath());
        }catch (Exception e){
            e.printStackTrace();
        }
        return cachePath ;
    }

    private static String ROOT_DIR;
    private static final String SP_NAME = "MTime_SP";
    private static final String SP_KEY_1 = "MTime_student_1";
    private static final String SP_KEY_2 = "MTime_student_2";
    private static final String SP_KEY_3 = "MTime_student_3";
    private void initFolder(){
        String rootDir = Environment.getExternalStorageDirectory().getPath();
        ROOT_DIR = rootDir + File.separator + "MTime";
        File file = new File(ROOT_DIR);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    private void putSp(String key1, String context){
        SharedPreferences sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key1, context);
        editor.apply();
    }

    private String getSp(String key){
        SharedPreferences sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    Student st = new Student("Tom", 'M', 20);
    private void saveToFile(){
        File file = new File(ROOT_DIR + File.separator+".txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("test", "createNewFile is wrong");
        }
        try {
            // Student对象序列化过程
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(st);
            oos.flush();
            oos.close();
            fos.close();
        }catch (Exception e) {
            e.printStackTrace();
            Log.e("test", "saveToFile is wrong");
        }

    }
    Student mStudent = new Student();
    GalleryItem mGalleryItem = new GalleryItem();

    PhotoBean mPhotoBean = new PhotoBean();
    MediaBean mMediaBean = new MediaBean();
    private void saveToSp(){
        mGalleryItem.path = "/storage/emulated/0/in/log/view_exception.jpg";
        mGalleryItem.dataTaken = 1511834921000L;
        mGalleryItem.size = 327359;
        mGalleryItem.latitude = 0.0;
        mGalleryItem.longitude = 0.0;
        mGalleryItem.fromWhere=200;
        mGalleryItem.width = 1080;
        mGalleryItem.height = 1920;
        mGalleryItem.duration = 1920L;

        try{
            String json = JSON.toJSONString(mStudent);
            Log.d("test", "mStudent ok");
            String gallery = JSON.toJSONString(mGalleryItem);
            Log.d("test", "mGalleryItem ok");
            String photo = JSON.toJSONString(mPhotoBean);
            Log.d("test", "photo ok");
            String media = JSON.toJSONString(mMediaBean);
            Log.d("test", "media ok");
            GalleryItem item = JSON.parseObject(gallery, GalleryItem.class);
            Log.d("test", "parseObject ok");
            putSp(SP_KEY_1, json);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void restoreFromSp(){
        String json = getSp(SP_KEY_1);
        Student student = (Student) JSON.parseObject(json, Student.class);
        Log.e("test","name = " + student.getName());
        Log.e("test","sex = " + student.getSex());
        Log.e("test","year = " + student.getYear());
        Log.e("test","weight = " + student.weight);
        Log.e("test","height = " + student.height);
        Log.e("test", "restoreFromSp is ok");
    }

    private void restoreFromFile(){
        File file = new File(ROOT_DIR + File.separator+".txt");
        if(!file.exists()){
            Log.e("test", "file is null");
            return;
        }
        try {
            // Student对象序列化过程
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Student st1 = (Student) ois.readObject();
            Log.e("test","name = " + st1.getName());
            Log.e("test","sex = " + st1.getSex());
            Log.e("test","year = " + st1.getYear());
            Log.e("test","weight = " + st1.weight);
            Log.e("test","height = " + st1.height);
            ois.close();
            fis.close();
        }catch (Exception e) {
            e.printStackTrace();
            Log.e("test", "restoreFromFile is wrong");
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void openOptionsMenu() {
        super.openOptionsMenu();
    }
}
