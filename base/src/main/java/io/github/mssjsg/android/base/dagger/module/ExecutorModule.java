package io.github.mssjsg.android.base.dagger.module;

import android.os.Process;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

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

    @Singleton
    @Provides
    ThreadFactory provideThreadFactory() {
        return new PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND);
    }

    @Singleton
    @Provides
    ExecutorSupplier provideExecutorSupplier(ThreadFactory threadFactory) {
        return new DefaultExecutorSupplier(threadFactory);
    }

    @Provides @Singleton @ForExecutor.Main
    Executor provideMainThreadExecutor() {
        return new MainThreadExecutor();
    }

    @Provides @Singleton @ForExecutor.Io
    Executor provideIoBoundedExecutor(ExecutorSupplier executorSupplier) {
        return executorSupplier.io();
    }

    @Provides @Singleton @ForExecutor.Computation
    Executor provideComputationExecutor(ExecutorSupplier executorSupplier) {
        return executorSupplier.computation();
    }
}
