package io.github.mssjsg.android.base.util.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * Created by maksing on 26/11/2016.
 */

public interface ExecutorSupplier {
    /**
     * Executor for IO bounded tasks
     * @return
     */
    Executor io();

    /**
     * Executor for CPU bounded tasks
     * @return
     */
    Executor computation();
}
