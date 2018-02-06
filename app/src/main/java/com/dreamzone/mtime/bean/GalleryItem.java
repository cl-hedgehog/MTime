package com.dreamzone.mtime.bean;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by mingtian on 16/1/18.
 */
public class GalleryItem extends PhotoBean implements Serializable {

    // -1 无效；0 未上传；1 已上传
    public int status;
    public String compressPath; // 压缩地址
    public String channel;
    // 本地图片上传后，返回的网络地址
    public String url;

    public String key;
    public String uri;

    public int width;
    public int height;
    public int position;
    public int selectPos = -1;

    // 用于分组
    public static final int TYPE_TITLE = 100;
    public static final int TYPE_PHOTO = 200;
    public static final int FROM_IN = 100;
    public static final int FROM_LOCAL = 200;
    public int type;
    public int fromWhere = 200;
    public boolean selectedAll;
    public String group;
    public String textLoc;
    public String textDate;
    public String textSelAll;
    //图片exif地址
    public String location;
    //gps地址
    public String gpsString;
    //时间 日
    public String datatime;

    // 文件类型 image = 0; video = 1;
    public int mediaType;
    public long duration;

    public GalleryItem() {
    }

    public GalleryItem(String path) {
        this.path = path;
        uri = "file://" + path;
    }

    public GalleryItem(MediaBean info) {
        path = info.getPath();
        width=info.getWidth();
        height=info.getHeight();
        setSize(info.getSize());
        setDataTaken(info.getDataTaken());

    }

    public GalleryItem(GalleryItem info) {
        key = info.key;
        uri = info.uri;
        path = info.path;
        width = info.width;
        height = info.height;
        position = info.position;
        selectPos = info.selectPos;
    }

    public int getMediaType() {
        return mediaType;
    }

    public String getDuration() {
        try {
            return formatTimeWithMin(duration);
        } catch (NumberFormatException e) {
            return "0:00";
        }
    }

    public String formatTimeWithMin(long duration) {
        if (duration <= 0) {
            return String.format(Locale.US, "%02d:%02d", 0, 0);
        }
        long totalSeconds = duration / 1000;

        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d", hours * 60 + minutes,
                    seconds);
        } else {
            return String.format(Locale.US, "%02d:%02d", minutes, seconds);
        }
    }

    public boolean isSelected() {
        return selectPos > 0;
    }

    public boolean resetSelected() {
        selectPos = -1;
        return true;
    }

    public int[] getPhotoWH() {
        if (width == 0 || height == 0) {
            width = 100;
            height = 100;
        }
        return new int[]{width, height};
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GalleryItem) {
            GalleryItem that = (GalleryItem) o;

            if (path.equals(that.path)) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }
}
