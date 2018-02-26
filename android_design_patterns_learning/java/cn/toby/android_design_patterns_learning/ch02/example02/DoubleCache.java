package cn.toby.android_design_patterns_learning.ch02.example02;

import android.graphics.Bitmap;

/**
 * Created by toby on 18-2-26.
 */

public class DoubleCache {
    ImageCache mMemoryCache = new ImageCache();
    DiskCache mDiskCache = new DiskCache();

    // 先从内存缓存中获取图片，如果没有，再从 SD 卡中获取
    public Bitmap get(final String url) {
        Bitmap bitmap = mMemoryCache.get(url);
        if (null == bitmap) {
            bitmap = mDiskCache.get(url);
        }
        return bitmap;
    }

    // 将图片缓存到内存和 SD 卡中
    public void put(final String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
        mDiskCache.put(url, bitmap);
    }
}
