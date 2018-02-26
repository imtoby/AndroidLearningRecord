package cn.toby.android_design_patterns_learning.ch02.example03;

import android.graphics.Bitmap;

/**
 * Created by toby on 18-2-26.
 */

public interface ImageCache {
    public Bitmap get(final String url);
    public void put(final String url, Bitmap bitmap);
}
