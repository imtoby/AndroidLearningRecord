package cn.toby.android_design_patterns_learning.ch02.example03;

import android.graphics.Bitmap;

/**
 * Created by toby on 18-2-26.
 */

public class Test {
    public void test() {
        ImageLoader imageLoader = new ImageLoader();

        // 使用内存缓存
        imageLoader.setImageCache(new MemoryCache());
        
        // 使用 SD 卡缓存
        imageLoader.setImageCache(new DiskCache());
        
        // 使用双缓存
        imageLoader.setImageCache(new DoubleCache());
        
        // 使用自定义的图片缓存方法
        imageLoader.setImageCache(new ImageCache() {
            @Override
            public Bitmap get(String url) {
                // TODO: 18-2-26 自定义方法
                return null;
            }

            @Override
            public void put(String url, Bitmap bitmap) {
                // TODO: 18-2-26 自定义方法
            }
        });
    }
}
