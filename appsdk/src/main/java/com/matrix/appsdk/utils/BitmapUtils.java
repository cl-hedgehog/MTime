package com.matrix.appsdk.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.text.format.DateFormat;

import com.matrix.appsdk.common.SDKConstans;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;

@SuppressLint("NewApi")
public class BitmapUtils {

    public static Bitmap createBitmap(String path) {
        Options opts = new Options();
        opts.inJustDecodeBounds = false;
        opts.inDither = false;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(path, opts);
    }

    public static Bitmap createBitmap(String filePath, final int widthTo) {
        if (filePath == null || widthTo <= 0) {
            return null;
        }
        int thumbnailHeight;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        thumbnailHeight = (int) (((float) widthTo) / options.outWidth * options.outHeight);
        int m = options.outWidth > options.outHeight ? options.outWidth : options.outHeight;
        Options newoptions = new Options();
        newoptions.inSampleSize = (m + widthTo - 1) / widthTo;
        newoptions.inJustDecodeBounds = false;
        newoptions.inDither = false;
        newoptions.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, newoptions);
        if (null == bitmap) {
            return null;
        }
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();

        int focusX = width / 2;
        int focusY = height / 2;
        int cropX;
        int cropY;
        int cropWidth;
        int cropHeight;
        if (widthTo * height < thumbnailHeight * width) {
            // Vertically constrained.
            cropWidth = widthTo * height / thumbnailHeight;
            cropX = Math.max(0, Math.min(focusX - cropWidth / 2, width - cropWidth));
            cropY = 0;
            cropHeight = height;
        } else {
            // Horizontally constrained.
            cropHeight = thumbnailHeight * width / widthTo;
            cropY = Math.max(0, Math.min(focusY - cropHeight / 2, height - cropHeight));
            cropX = 0;
            cropWidth = width;
        }
        final Bitmap finalBitmap = Bitmap.createBitmap(widthTo, thumbnailHeight, Bitmap.Config.RGB_565);// RGB_565
        final Canvas canvas = new Canvas(finalBitmap);
        final Paint paint = new Paint();
        paint.setDither(true);
        paint.setFilterBitmap(true);
        canvas.drawColor(0);
        canvas.drawBitmap(bitmap, new Rect(cropX, cropY, cropX + cropWidth, cropY + cropHeight), new Rect(0, 0, widthTo, thumbnailHeight), paint);
        bitmap.recycle();
        return finalBitmap;
    }

    public static Bitmap createFitinBitmap(String path, int screenWidth, int screenHeight) {
        return createFitinBitmap(path, 1, screenWidth, screenHeight);
    }

    public static Bitmap createFitinBitmap(String path, int scale, int screenWidth, int screenHeight) {
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        try {

            int dstWidth = screenWidth;
            int dstHeight = screenHeight;

            int MAX_IMAGE_LENGTH = Math.max(dstWidth, dstHeight);

            BitmapFactory.decodeFile(path, opts);

            int sampleSize1 = opts.outWidth / MAX_IMAGE_LENGTH;
            int sampleSize2 = opts.outHeight / MAX_IMAGE_LENGTH;
            opts.inSampleSize = sampleSize1 > sampleSize2 ? sampleSize1 : sampleSize2;
            opts.inSampleSize = (opts.inSampleSize + 1) / 2 * 2;

            opts.inJustDecodeBounds = false;
            opts.inDither = false;
            opts.inPreferredConfig = Bitmap.Config.RGB_565;

            Bitmap temp0 = BitmapFactory.decodeFile(path, opts);
            if (temp0 == null) {
                return null;
            }

            Matrix m = new Matrix();

            float sample1 = dstWidth / ((float) opts.outWidth);
            float sample2 = dstHeight / ((float) opts.outHeight);
            float sample = sample1 < sample2 ? sample1 : sample2;

            Bitmap temp;
            if (sample < 1.0) {
                m.postScale(sample, sample);
                temp = Bitmap.createBitmap(temp0, 0, 0, temp0.getWidth(), temp0.getHeight(), m, true);
                temp0.recycle();
            } else {
                temp = temp0;
            }
            if (temp.isMutable() && temp.getConfig() == Bitmap.Config.RGB_565) {
                return temp;
            } else {
            }

            Bitmap temp2 = temp.copy(Bitmap.Config.RGB_565, true);

            temp2.getWidth();
            temp2.getHeight();

            temp.recycle();
            temp = null;

            return temp2;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Bitmap ResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        if (bm == null || bm.isRecycled()) {
            return null;
        }
        final int w = bm.getWidth();
        final int h = bm.getHeight();

        final float sw = ((float) newWidth) / w;
        final float sh = ((float) newHeight) / h;
        float ratio = 0.0f;

        if (sw >= sh) {
            ratio = sh;
        } else {
            ratio = sw;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(ratio, ratio);
        return Bitmap.createBitmap(bm, 0, 0, w, h, matrix, true);
    }

    /**
     * 从Assets中读取图片
     *
     * @param mContext
     * @param fileName 文件名
     * @return
     */
    public static Bitmap getImageFromAssetsFile(Context mContext,
                                                String fileName) {
        Bitmap image = null;
        AssetManager am = mContext.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }




    public static Bitmap makeRoundCorner(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int left = 0, top = 0, right = width, bottom = height;
        float roundPx = height / 2;
        if (width > height) {
            left = (width - height) / 2;
            top = 0;
            right = left + height;
            bottom = height;
        } else if (height > width) {
            left = 0;
            top = (height - width) / 2;
            right = width;
            bottom = top + width;
            roundPx = width / 2;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(left, top, right, bottom);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static boolean saveBmp(Context context, Bitmap bitmap, String dirString) {
        File dir = new File(dirString);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String name = dirString + "hjk" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        boolean isSaved = saveBmp(bitmap, name, 100);

        scanPhoto(context, name);

        return isSaved;
    }

    public static boolean saveBmp(Bitmap bitmap, String name) {
        return saveBmp(bitmap, name, 95);
    }

    public static boolean saveBmp(Bitmap bitmap, String name, int quality) {
        if (bitmap == null || bitmap.isRecycled()) {
            return false;
        }
        try {
            File pf = new File(name);
            if (!pf.exists()) {
                pf.createNewFile();
            } else {
                pf.delete();
            }
            FileOutputStream stream;
            stream = new FileOutputStream(pf);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
            stream.flush();
            stream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 让Gallery上能马上看到该图片
     */
    private static void scanPhoto(Context ctx, String imgFileName) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(imgFileName);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        ctx.sendBroadcast(mediaScanIntent);
    }

    public static boolean saveBmpToPNG(Bitmap bitmap, String name) {
        if (bitmap == null || bitmap.isRecycled()) {
            return false;
        }
        try {
            File pf = new File(name);
            if (!pf.exists()) {
                pf.createNewFile();
            } else {
                pf.delete();
            }
            FileOutputStream stream;
            stream = new FileOutputStream(pf);
            bitmap.compress(Bitmap.CompressFormat.PNG, 95, stream);
            stream.flush();
            stream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getExifOrientation(String filepath) {
        if (Tools.isStrEmpty(filepath)) {
            return 0;
        }
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognize a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }

            }
        }
        return degree;
    }

    public static Bitmap rotateAndMirror(Bitmap b, int degrees, boolean mirror, boolean needRgba) {
        if ((degrees != 0 || mirror) && b != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) b.getWidth() / 2, (float) b.getHeight() / 2);
            if (mirror) {
                m.postScale(-1, 1);
                degrees = (degrees + 360) % 360;
                if (degrees == 0 || degrees == 180) {
                    m.postTranslate((float) b.getWidth(), 0);
                } else if (degrees == 90 || degrees == 270) {
                    m.postTranslate((float) b.getHeight(), 0);
                } else {
                    throw new IllegalArgumentException("Invalid degrees=" + degrees);
                }
            }

            m.rectStaysRect();
            try {
                Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
                if (b != b2) {
                    b.recycle();
                    b = b2;
                }
                if (android.os.Build.VERSION.SDK_INT <= 10 && needRgba) {
                    if (b.getConfig() == Bitmap.Config.RGB_565) {
                        Bitmap b3 = b.copy(Bitmap.Config.ARGB_8888, true);
                        if (b != b3) {
                            b.recycle();
                            b = b3;
                        }
                    }
                }

            } catch (OutOfMemoryError ex) {
                // We have no memory to rotate. Return the original bitmap.
            }
        }
        return b;
    }

    public static String ResizeFile(String filename, int screenWidth, int screenHeight, String imgSavePath) {
        String tempFile = getResizedFile(filename, screenWidth, screenHeight,imgSavePath);
        int orient = getExifOrientation(filename);
        if (orient != 0) {
            Bitmap bitmap = createBitmap(tempFile);
            bitmap = rotateAndMirror(bitmap, orient, false, true);
            saveBmp(bitmap, tempFile);
        }
        return tempFile;
    }

    public static String getResizedFile(String filename,int screenWidth, int screenHeight, String imgSavePath) {
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, opts);

        double srcSquare = (double) opts.outWidth * opts.outHeight;
        double dstSquare = (double) screenWidth * screenHeight;

        if (srcSquare > dstSquare) {
            double ratio = srcSquare / dstSquare;
            double scale = Math.sqrt(ratio);
            int widthTo = (int) ((opts.outWidth / scale) / 2) * 2;

            Bitmap bitmap = BitmapUtils.createBitmap(filename, widthTo);
            new DateFormat();
            String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            File dir = new File(imgSavePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String tempfilename = imgSavePath + name;
            boolean bSuccess = BitmapUtils.saveBmp(bitmap, tempfilename);
            if (bSuccess) {
                return tempfilename;
            } else {
                return filename;
            }
        }
        return filename;
    }

    /**
     * <pre>
     * 压缩图片到100K以下,质量压缩方法：
     * </pre>
     *
     * @param sourcePath
     * @return
     */
    public static String compressImage(String sourcePath,int screenWidth, int screenHeight,  String dirString ) {
        File file = new File(sourcePath);
        if (SDKConstans.MAX_UNCOMPRESS_IMAGE_SIZE > file.length()) {
            return sourcePath;
        }
        File dir = new File(dirString);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 压缩后图片存储目录
        String filename = dirString + sourcePath.substring(sourcePath.lastIndexOf("/") + 1);
        Bitmap bitmap = BitmapFactory.decodeFile(sourcePath);

        if (null != bitmap) {
            // 先size压缩
            int orient = getExifOrientation(sourcePath);
            bitmap = rotateAndMirror(bitmap, orient, false, true);
            boolean needCompress = bitmap.getWidth() * bitmap.getHeight() > screenWidth * screenHeight;
            if (needCompress) {
                bitmap = BitmapUtils.ResizedBitmap(bitmap, screenWidth, screenHeight);
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            // size还大的话直接压缩搭配500*500
            if (baos.toByteArray().length > SDKConstans.MAX_UNCOMPRESS_IMAGE_SIZE) {
                bitmap = BitmapUtils.ResizedBitmap(bitmap, 500, 500);
            }
            // jpeg < 100k
            FileOutputStream b = null;
            try {
                b = new FileOutputStream(filename);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, b);// 把数据写入文件

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    b.flush();
                    b.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (null != bitmap && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        return filename;
    }
}
