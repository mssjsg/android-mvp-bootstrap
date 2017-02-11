package io.github.mssjsg.android.base.usecase;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by sing on 2/1/17.
 */

public abstract class Interactor<Param, Progress, Result> {

    private ExecutorService mBackgroundExecutor;
    private Executor mPostExecuteExecutor;

    private volatile Callback<Progress, Result> mCallback;

    public Interactor(ExecutorService backgroundExecutor, Executor postExecuteExecutor) {
        mBackgroundExecutor = backgroundExecutor;
        mPostExecuteExecutor = postExecuteExecutor;
    }

    public void setCallback(Callback<Progress, Result> callback) {
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

    protected void postProgress(final Progress progress) {
        post(new Runnable() {
            @Override
            public void run() {
                mCallback.onProgress(progress);
            }
        });
    }

    protected void postResult(final Result result) {
        post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSuccess(result);
            }
        });
    }

    protected void postException(final Exception exception) {
        post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFailure(exception);
            }
        });
    }

    public final Future<Result> execute(final Param param) {
        return mBackgroundExecutor.submit(new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                try {
                    Result result = onExecute(param);
                    postResult(result);
                    return result;
                } catch (Exception e) {
                    postException(e);
                    throw e;
                }
            }
        });
    }

    public final Future<Result> execute() {
        return execute(null);
    }

    protected abstract Result onExecute(Param param) throws Exception;

    public interface Callback<Progress, Result> {
        void onProgress(Progress progress);

        void onSuccess(Result result);

        void onFailure(Exception exception);
    }
}
