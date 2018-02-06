package com.dreamzone.mtime.bean;

import java.io.Serializable;

/**
 * Created by qishui on 16-1-13.
 */
public class PhotoBean extends MediaBean implements Serializable {

    public PhotoBean(){}
    public boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof PhotoBean) {
            PhotoBean that = (PhotoBean) o;

            if (this.path == null || that.path == null) {
                return false;
            }

            return this.path.equals(that.path);

        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String str = path + "\n" + name + "\n" + dataTaken + "\n" + selected + "\n" + latitude + "\n" + longitude + "\n" + mime + "\n" + size + "\n";
        return str;
    }

    @Override
    public int hashCode() {
        if (this.path == null) {
            return super.hashCode();
        }
        return this.path.hashCode();
    }
}
