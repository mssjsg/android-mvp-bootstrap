package io.github.mssjsg.android.base.presentation.presenter;

import io.github.mssjsg.android.base.presentation.PresentationModel;

/**
 * Created by Sing on 8/7/2017.
 */

public abstract class BasePresenter<View> implements Presenter<View> {

    private View mView;

    protected View getView() {
        return mView;
    }

    @Override
    public void bindView(View view) {
        mView = view;
    }

    @Override
    public void unbindView(View view) {
        if (mView == view) {
            mView = getEmptyView();
        }
    }

    protected abstract View getEmptyView();

    @Override
    public void dispose() {
        //do nothing
    }
}
