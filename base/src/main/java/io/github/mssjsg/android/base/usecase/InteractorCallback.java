package io.github.mssjsg.android.base.usecase;

import io.github.mssjsg.android.base.usecase.Interactor;

/**
 * Created by sing on 2/12/17.
 */

public abstract class InteractorCallback<Progress, Result> implements Interactor.Callback<Progress, Result> {
    @Override
    public void onProgress(Progress progress) {

    }

    @Override
    public void onSuccess(Result result) {

    }

    @Override
    public void onFailure(Exception exception) {

    }
}
