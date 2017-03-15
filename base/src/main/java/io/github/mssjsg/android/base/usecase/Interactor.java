package io.github.mssjsg.android.base.usecase;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by sing on 2/1/17.
 */

public abstract class Interactor<Param, Progress, Result> {

    private ExecutorService mBackgroundExecutor;
    private Executor mPostExecuteExecutor;

    private volatile Callback<Param, Progress, Result> mCallback;

    private Map<Param, Future<Result>> mFutures = new ConcurrentHashMap<>();
    private Future<Result> mNullParamFuture;

    public Interactor(ExecutorService backgroundExecutor, Executor postExecuteExecutor) {
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
        if ((param != null && mFutures.get(param) == null) || mNullParamFuture == null) {
            //only submit a new task when it's not in progress

            Future<Result> future = mBackgroundExecutor.submit(new Callable<Result>() {
                @Override
                public Result call() throws Exception {
                    try {
                        Result result = onExecute(param);
                        postResult(param, result);
                        return result;
                    } catch (Exception e) {
                        postException(param, e);
                        throw e;
                    } finally {
                        if (param == null) {
                            mNullParamFuture = null;
                        } else {
                            mFutures.remove(param); //remove the future when it's done
                        }
                    }
                }
            });

            if (param == null) {
                mNullParamFuture = future;
            } else {
                mFutures.put(param, future);
            }
        }

        if (param == null) {
            return mNullParamFuture;
        } else {
            return mFutures.get(param);
        }
    }

    public final Future<Result> execute() {
        return execute(null);
    }

    protected abstract Result onExecute(Param param) throws Exception;

    public interface Callback<Param, Progress, Result> {
        void onProgress(Param param, Progress progress);

        void onSuccess(Param param, Result result);

        void onFailure(Param param, Exception exception);
    }
}
