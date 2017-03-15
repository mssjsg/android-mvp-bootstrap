package io.github.mssjsg.android.base.usecase;

/**
 * Created by sing on 2/12/17.
 */

public abstract class InteractorCallback<Param, Progress, Result> implements Interactor.Callback<Param, Progress, Result> {
    @Override
    public void onProgress(Param param, Progress progress) {

    }

    @Override
    public void onSuccess(Param param, Result result) {

    }

    @Override
    public void onFailure(Param param, Exception exception) {

    }
}
