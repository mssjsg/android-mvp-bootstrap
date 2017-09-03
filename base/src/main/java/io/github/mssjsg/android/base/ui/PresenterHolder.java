package io.github.mssjsg.android.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import io.github.mssjsg.android.base.presentation.model.PresentationModel;
import io.github.mssjsg.android.base.presentation.presenter.Presenter;
import io.github.mssjsg.android.base.ui.fragment.WorkerFragment;

/**
 * Created by Sing on 23/8/2017.
 */

public class PresenterHolder<V, M extends PresentationModel, P extends Presenter<V>> {
    private P mPresenter;
    private M mPresentationModel;
    private Callback<V, M, P> mCallback;
    private FragmentManager mFragmentManager;

    private V mView;

    private static final String EXTRA_PRESEENTATION_MODEL = "io.github.mssjsg.android.base.ui.activity.PresenterActivity";

    public PresenterHolder(V presenterView, FragmentManager fragmentManager, Callback<V, M, P> callback) {
        mView = presenterView;
        mFragmentManager = fragmentManager;
        mCallback = callback;
    }

    public final void onCreate(@Nullable Bundle savedInstanceState) {
        WorkerFragment workerFragment = prepareWorkerFragment();

        mPresentationModel = (M)workerFragment.getPresentationModel();
        if (mPresentationModel == null && savedInstanceState != null) {
            mPresentationModel = savedInstanceState.getParcelable(EXTRA_PRESEENTATION_MODEL);
        }

        if (mPresentationModel == null) {
            mPresentationModel = mCallback.onCreatePresentationModel();
            workerFragment.setPresentationModel(mPresentationModel);
        }

        mCallback.onPreCreatePresenter(savedInstanceState, mPresentationModel);

        mPresenter = (P)workerFragment.getPresenter();
        if (mPresenter == null) {
            mPresenter = mCallback.onCreatePresenter();
            workerFragment.setPresenter(mPresenter);
            mPresenter.bindView(mView);
            mCallback.onPresenterCreated();
        } else {
            mPresenter.bindView(mView);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        if (mPresentationModel != null) {
            outState.putParcelable(EXTRA_PRESEENTATION_MODEL, mPresentationModel);
        }
    }

    public void onDestroy() {
        getPresenter().unbindView();
    }

    private WorkerFragment prepareWorkerFragment() {
        Fragment fragment = mFragmentManager.findFragmentByTag(WorkerFragment.TAG);
        if (!(fragment instanceof WorkerFragment)) {
            fragment = WorkerFragment.newInstance();
            mFragmentManager.beginTransaction().add(fragment, WorkerFragment.TAG).commitNow();
        }

        return (WorkerFragment)fragment;
    }

    public P getPresenter() {
        return mPresenter;
    }

    public M getPresentationModel() {
        return mPresentationModel;
    }

    public interface Callback<V, M extends PresentationModel, P extends Presenter<V>> {
        void onPreCreatePresenter(@Nullable Bundle savedInstanceState, M presentationModel);

        P onCreatePresenter();

        void onPresenterCreated();

        M onCreatePresentationModel();

    }
}
