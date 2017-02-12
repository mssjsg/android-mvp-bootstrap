package io.github.mssjsg.android.base.dagger.module;

import android.os.Process;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.mssjsg.android.base.util.executor.DefaultExecutorSupplier;
import io.github.mssjsg.android.base.util.executor.ExecutorSupplier;
import io.github.mssjsg.android.base.util.executor.ForExecutor;
import io.github.mssjsg.android.base.util.executor.MainThreadExecutor;
import io.github.mssjsg.android.base.util.executor.PriorityThreadFactory;

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
    ExecutorService provideIoBoundedExecutor() {
        return mExecutorSupplier.io();
    }

    @Provides @Singleton @ForExecutor.Computation
    ExecutorService provideComputationExecutor() {
        return mExecutorSupplier.computation();
    }
}
