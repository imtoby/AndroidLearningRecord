package cn.toby.android_design_patterns_learning.ch02.example03;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by toby on 18-2-28.
 */

public final class CloseUtils {
    private CloseUtils() {
    }

    /**
     * 关闭 Closeable 对象
     *
     * @param closeable
     */
    public static void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
