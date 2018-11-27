package com.xq.androidfaster.util.tools;

import java.util.concurrent.atomic.AtomicInteger;

public final class IDUtils {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * @return 每次调用该方法都会获取一个唯一的ID
     */
    public synchronized static int getId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}
