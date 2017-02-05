package io.github.mssjsg.android.base.util.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by maksing on 26/11/2016.
 */

public class DefaultExecutorSupplier implements ExecutorSupplier {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    // Allows for simultaneous reads and writes.
    private static final int NUM_IO_BOUND_THREADS = 2;
    private static final int NUM_LIGHTWEIGHT_BACKGROUND_THREADS = 1;

    // We want at least 2 threads and at most 4 threads in the core pool,
    // preferring to have 1 less than the CPU count to avoid saturating
    // the CPU with background work
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int COMPUTATION_KEEP_ALIVE_SECONDS = 30;

    private final Executor mIoBoundedExecutor;
    private final Executor mComputationExecutor;

    public DefaultExecutorSupplier(ThreadFactory threadFactory) {

        //create computation thread pool
        BlockingQueue<Runnable> computationWorkQueue = new LinkedBlockingQueue<Runnable>(128);

        ThreadPoolExecutor computationThreadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, COMPUTATION_KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                computationWorkQueue, new NamedThreadFactory("Computation", threadFactory));

        computationThreadPoolExecutor.allowCoreThreadTimeOut(true);

        mComputationExecutor = computationThreadPoolExecutor;

        //create io thread pool
        mIoBoundedExecutor = Executors.newCachedThreadPool(new NamedThreadFactory("IO", threadFactory));
    }

    @Override
    public Executor io() {
        return mIoBoundedExecutor;
    }

    @Override
    public Executor computation() {
        return mComputationExecutor;
    }
}
