package io.github.mssjsg.android.base.util.executor;

import android.os.Process;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sing on 1/22/17.
 */

@Module
public class ExecutorModule {
    private Executor mMainThreadExecutor;
    private ExecutorSupplier mExecutorSupplier;

    public ExecutorModule() {
        mExecutorSupplier = new DefaultExecutorSupplier(new PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND));
        mMainThreadExecutor = new MainThreadExecutor();
    }

    @Provides @Singleton @ForExecutor.Main
    Executor provideMainThreadExecutor() {
        return mMainThreadExecutor;
    }

    @Provides @Singleton @ForExecutor.Io
    Executor provideIoBoundedExecutor() {
        return mExecutorSupplier.io();
    }

    @Provides @Singleton @ForExecutor.Computation
    Executor provideComputationExecutor() {
        return mExecutorSupplier.computation();
    }
}
