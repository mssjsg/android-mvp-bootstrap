package io.github.mssjsg.android.base.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.github.mssjsg.android.base.presentation.model.PresentationModel;
import io.github.mssjsg.android.base.presentation.presenter.Presenter;
import io.github.mssjsg.android.base.ui.PresenterHolder;

/**
 * Created by Sing on 23/8/2017.
 */

public abstract class PresenterActivity<V, M extends PresentationModel, P extends Presenter<V>>
        extends AppCompatActivity implements PresenterHolder.Callback<V, M, P> {

    private PresenterHolder<V, M, P> mPresenterHolder;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenterHolder = new PresenterHolder<>(getPresenterView(), getSupportFragmentManager(), this);
        mPresenterHolder.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mPresenterHolder.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        mPresenterHolder.onDestroy();
        super.onDestroy();
    }

    protected abstract V getPresenterView();

    protected P getPresenter() {
        return mPresenterHolder.getPresenter();
    }

    protected M getPresentationModel() {
        return mPresenterHolder.getPresentationModel();
    }
}
