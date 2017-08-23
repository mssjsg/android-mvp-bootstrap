package io.github.mssjsg.android.base.presentation.presenter;

import io.github.mssjsg.android.base.presentation.model.PresentationModel;

/**
 * Created by Sing on 8/7/2017.
 */

public abstract class BasePresenter<View, Model extends PresentationModel> implements Presenter<View> {

    private View mView;
    private Model mModel;

    public BasePresenter(Model model) {
        mModel = model;
    }

    protected View getView() {
        return mView;
    }

    protected Model getPresentationModel() {
        return mModel;
    }

    @Override
    public void bindView(View view) {
        mView = view;
        onViewBinded(view, mModel);
    }

    @Override
    public void unbindView() {
        mView = getEmptyView();
    }

    protected abstract void onViewBinded(View view, Model model);

    protected abstract View getEmptyView();
}
