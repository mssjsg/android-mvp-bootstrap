package io.github.mssjsg.android.base.usecase;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by sing on 2/1/17.
 */

public abstract class Interactor<Param, Progress, Result> {

    private Executor mBackgroundExecutor;
    private Executor mPostExecuteExecutor;

    private final Map<Object, TaskItem<Param, Progress, Result>> mFutures = new HashMap<>();

    private static final Object NULL_PARAM = new Object();

    public Interactor(Executor backgroundExecutor, Executor postExecuteExecutor) {
        mBackgroundExecutor = backgroundExecutor;
        mPostExecuteExecutor = postExecuteExecutor;
    }

    protected void postProgress(final Param param, final Progress progress) {
        final TaskItem<Param, Progress, Result> taskItem = getTaskItem(param);
        mPostExecuteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskItem.mCallbacks.onProgress(param, progress);
            }
        });
    }

    protected void postResult(final Param param, final Result result) {
        final TaskItem<Param, Progress, Result> taskItem = getTaskItem(param);
        mPostExecuteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskItem.mCallbacks.onSuccess(param, result);
            }
        });
    }

    protected void postException(final Param param, final Exception exception) {
        final TaskItem<Param, Progress, Result> taskItem = getTaskItem(param);
        mPostExecuteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskItem.mCallbacks.onFailure(param, exception);
            }
        });
    }

    private TaskItem<Param, Progress, Result> getTaskItem(Param param) {
        if (param == null) {
            return mFutures.get(NULL_PARAM);
        } else {
            return mFutures.get(param);
        }
    }

    public final Future<Result> execute(final Param param, final Callback<Param, Progress, Result> callback) {

        final Object futureKey;
        if (param == null) {
            futureKey = NULL_PARAM;
        } else {
            futureKey = param;
        }

        synchronized (mFutures) {
            TaskItem<Param, Progress, Result> taskItem = mFutures.get(futureKey);

            if (taskItem == null) {
                //only submit a new task when it's not in progress

                Callable<Result> callable = new Callable<Result>() {
                    @Override
                    public Result call() throws Exception {
                        TaskItem<Param, Progress, Result> taskItem = mFutures.get(futureKey);

                        boolean taskCancelled = taskItem.mResultFuture.isCancelled();
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
                mFutures.put(futureKey, new TaskItem<>(futureTask, callback));

                mBackgroundExecutor.execute(futureTask);
            } else {
                mFutures.get(futureKey).mCallbacks.addCallback(callback);
            }

            return mFutures.get(futureKey).mResultFuture;
        }
    }

    protected abstract Result onExecute(Param param) throws Exception;

    public interface Callback<Param, Progress, Result> {
        void onProgress(Param param, Progress progress);

        void onSuccess(Param param, Result result);

        void onFailure(Param param, Exception exception);
    }

    private static class TaskItem<Param, Progress, Result> {
        Future<Result> mResultFuture;
        CallbackWrapper<Param, Progress, Result> mCallbacks;

        TaskItem(Future<Result> resultFuture, Callback<Param, Progress, Result> callback) {
            mResultFuture = resultFuture;
            mCallbacks = new CallbackWrapper<>(callback);
        }
    }

    private static class CallbackWrapper<Param, Progress, Result> implements Callback<Param, Progress, Result> {

        Set<Callback<Param, Progress, Result>> mCallbacks;

        public CallbackWrapper(Callback<Param, Progress, Result> callback) {
            mCallbacks = new CopyOnWriteArraySet<>();
            if (callback != null) {
                mCallbacks.add(callback);
            }
        }

        @Override
        public void onProgress(Param param, Progress progress) {
            for (Callback<Param, Progress, Result> callback : mCallbacks) {
                callback.onProgress(param, progress);
            }
        }

        @Override
        public void onSuccess(Param param, Result result) {
            for (Callback<Param, Progress, Result> callback : mCallbacks) {
                callback.onSuccess(param, result);
            }
        }

        @Override
        public void onFailure(Param param, Exception exception) {
            for (Callback<Param, Progress, Result> callback : mCallbacks) {
                callback.onFailure(param, exception);
            }
        }

        void addCallback(Callback<Param, Progress, Result> callback) {
            if (callback != null) {
                mCallbacks.add(callback);
            }
        }
    }
}
