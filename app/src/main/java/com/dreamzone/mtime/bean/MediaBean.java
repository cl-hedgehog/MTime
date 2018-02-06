package com.dreamzone.mtime.bean;

import java.io.Serializable;

/**
 * Created by qishui on 16-1-21.
 */
public class MediaBean implements Serializable{
    public String path;
    public String name;
    public long dataTaken;
    public double latitude;
    public double longitude;
    public String mime;
    public long size;
    public long dateCreate;
    public long dateModify;
    public int width;
    public int height;

    public MediaBean(){}

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDataTaken() {
        return dataTaken;
    }

    public void setDataTaken(long dataTaken) {
        this.dataTaken = dataTaken;
    }

    public long getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(long dateCreate) {
        this.dateCreate = dateCreate;
    }

    public long getDateModify() {
        return dateModify;
    }

    public void setDateModify(long dateModify) {
        this.dateModify = dateModify;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    /**
     * ！！！！需要注意，在有些手机上宽高可能为0
     *
     * @return 返回图片宽度
     */
    public int getWidth() {
        return width;
    }

    /**
     * ！！！！需要注意，在有些手机上宽高可能为0
     *
     * @return返回图片高度
     */
    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
