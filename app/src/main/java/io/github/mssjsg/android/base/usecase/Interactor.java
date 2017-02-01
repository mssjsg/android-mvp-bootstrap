package io.github.mssjsg.android.base.usecase;

import java.util.concurrent.Executor;

/**
 * Created by sing on 2/1/17.
 */

public abstract class Interactor<Param, Progress, Result> {

    private Executor mBackgroundExecutor;
    private Executor mPostExecuteExecutor;

    private volatile Callback<Progress, Result> mCallback;

    public Interactor(Executor backgroundExecutor, Executor postExecuteExecutor) {
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

    public final void execute(final Param param) {
        mBackgroundExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Result result = onExecute(param);
                    postResult(result);
                } catch (Exception e) {
                    postException(e);
                }
            }
        });
    }

    public abstract Result onExecute(Param param) throws Exception;

    public interface Callback<Progress, Result> {
        void onProgress(Progress progress);

        void onSuccess(Result result);

        void onFailure(Exception exception);
    }
}
