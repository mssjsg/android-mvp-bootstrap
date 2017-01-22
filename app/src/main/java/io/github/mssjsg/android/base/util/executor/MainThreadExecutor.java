package io.github.mssjsg.android.base.util.executor;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * Created by maksing on 5/11/2016.
 */

public class MainThreadExecutor implements Executor {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable command) {
        mHandler.post(command);
    }
}
