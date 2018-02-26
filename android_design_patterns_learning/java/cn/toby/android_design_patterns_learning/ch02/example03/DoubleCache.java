package cn.toby.android_design_patterns_learning.ch02.example03;

import android.graphics.Bitmap;

/**
 * Created by toby on 18-2-26.
 */

public class DoubleCache implements ImageCache {

    ImageCache mMemoryCache = new MemoryCache();
    ImageCache mDiskCache = new DiskCache();

    @Override
    public Bitmap get(String url) {
        // 先从内存缓存中获取图片，如果没有，再从 SD 卡中获取
        Bitmap bitmap = mMemoryCache.get(url);
        if (null == bitmap) {
            bitmap = mDiskCache.get(url);
        }
        return bitmap;
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        // 将图片缓存到内存和 SD 卡中
        mMemoryCache.put(url, bitmap);
        mDiskCache.put(url, bitmap);
    }
}
