package io.github.mssjsg.android.base.usecase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by sing on 2/1/17.
 */

public abstract class Interactor<Param, Progress, Result> {

    private Executor mBackgroundExecutor;
    private Executor mPostExecuteExecutor;

    private volatile Callback<Param, Progress, Result> mCallback;

    private final Map<Object, Future<Result>> mFutures = new HashMap<>();

    private static final Object NULL_PARAM = new Object();

    public Interactor(Executor backgroundExecutor, Executor postExecuteExecutor) {
        mBackgroundExecutor = backgroundExecutor;
        mPostExecuteExecutor = postExecuteExecutor;
    }

    public void setCallback(Callback<Param, Progress, Result> callback) {
        mCallback = callback;
    }

    private void post(final Runnable runnable) {
        if (mCallback != null) {
            mPostExecuteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    if (mCallback != null) {
                        runnable.run();
                    }
                }
            });
        }
    }

    protected void postProgress(final Param param, final Progress progress) {
        post(new Runnable() {
            @Override
            public void run() {
                mCallback.onProgress(param, progress);
            }
        });
    }

    protected void postResult(final Param param, final Result result) {
        post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSuccess(param, result);
            }
        });
    }

    protected void postException(final Param param, final Exception exception) {
        post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFailure(param, exception);
            }
        });
    }

    public final Future<Result> execute(final Param param) {

        final Object futureKey;
        if (param == null) {
            futureKey = NULL_PARAM;
        } else {
            futureKey = param;
        }

        synchronized (mFutures) {
            if (mFutures.get(futureKey) == null) {
                //only submit a new task when it's not in progress

                Callable<Result> callable = new Callable<Result>() {
                    @Override
                    public Result call() throws Exception {
                        boolean taskCancelled = mFutures.get(futureKey).isCancelled();
                        try {
                            Result result = onExecute(param);
                            if (!taskCancelled) {
                                postResult(param, result);
                            }
                            return result;
                        } catch (Exception e) {
                            if (!taskCancelled) {
                                postException(param, e);
                            }
                            throw e;
                        } finally {
                            mFutures.remove(futureKey); //remove the future when it's done
                        }
                    }
                };

                FutureTask<Result> futureTask = new FutureTask<>(callable);

                mFutures.put(futureKey, futureTask);

                mBackgroundExecutor.execute(futureTask);
            }

            return mFutures.get(futureKey);
        }
    }

    protected abstract Result onExecute(Param param) throws Exception;

    public interface Callback<Param, Progress, Result> {
        void onProgress(Param param, Progress progress);

        void onSuccess(Param param, Result result);

        void onFailure(Param param, Exception exception);
    }
}
