package com.example.loadlocalvideo.loadlocalvideo;

import android.graphics.Bitmap;

/**
 * Created by libo on 2017/11/7.
 */

public class LocalVideoBean {
    private String path;
    private long duration;
    private String thumbPath;
    private Bitmap bitmap;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
