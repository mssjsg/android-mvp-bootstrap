package io.github.mssjsg.android.base.util.executor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by maksing on 26/11/2016.
 */
class NamedThreadFactory implements ThreadFactory {
    private final AtomicInteger mCount = new AtomicInteger(1);

    private String mName;
    private ThreadFactory mThreadFactory;

    public NamedThreadFactory(String name, ThreadFactory threadFactory) {
        mName = name;
        mThreadFactory = threadFactory;
    }

    public Thread newThread(Runnable r) {
        Thread thread = mThreadFactory.newThread(r);
        thread.setName(mName + " #" + mCount.getAndIncrement());
        return thread;
    }
}
