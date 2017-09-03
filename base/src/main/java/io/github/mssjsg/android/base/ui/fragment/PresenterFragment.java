package io.github.mssjsg.android.base.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import io.github.mssjsg.android.base.presentation.model.PresentationModel;
import io.github.mssjsg.android.base.presentation.presenter.Presenter;
import io.github.mssjsg.android.base.ui.PresenterHolder;

/**
 * Created by Sing on 23/8/2017.
 */

public abstract class PresenterFragment<V, M extends PresentationModel, P extends Presenter<V>>
        extends Fragment implements PresenterHolder.Callback<V, M, P> {

    private PresenterHolder<V, M, P> mPresenterHolder;

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenterHolder = new PresenterHolder<>(getPresenterView(), getChildFragmentManager(), this);
        mPresenterHolder.onCreate(savedInstanceState);
    }

    protected abstract V getPresenterView();

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenterHolder.onSaveInstanceState(outState);
    }

    protected P getPresenter() {
        return mPresenterHolder.getPresenter();
    }

    protected M getPresentationModel() {
        return mPresenterHolder.getPresentationModel();
    }
}
