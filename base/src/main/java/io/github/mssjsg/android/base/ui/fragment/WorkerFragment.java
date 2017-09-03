package io.github.mssjsg.android.base.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import io.github.mssjsg.android.base.presentation.model.PresentationModel;
import io.github.mssjsg.android.base.presentation.presenter.Presenter;

/**
 * Created by Sing on 22/8/2017.
 * This fragment is responsible for doing work and keeping data across configuration changes
 */

public class WorkerFragment extends Fragment {

    public static final String TAG = "";

    private Presenter<?> mPresenter;

    private PresentationModel mPresentationModel;

    public static WorkerFragment newInstance() {
        return new WorkerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void setPresenter(Presenter<?> presenter) {
        mPresenter = presenter;
    }

    public Presenter<?> getPresenter() {
        return mPresenter;
    }

    public PresentationModel getPresentationModel() {
        return mPresentationModel;
    }

    public void setPresentationModel(PresentationModel presentationModel) {
        mPresentationModel = presentationModel;
    }
}
