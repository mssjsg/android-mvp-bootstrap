package io.github.mssjsg.android.base.dagger.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import io.github.mssjsg.android.base.dagger.ActivityScope;
import io.github.mssjsg.android.base.dagger.qualifier.ForActivity;
import io.github.mssjsg.android.base.presenter.Presenter;

/**
 * Created by sing on 1/22/17.
 */
@Module
public abstract class ActivityModule<S extends Presenter<?>> {

    private S mPresenter;
    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity, S presenter) {
        mActivity = activity;
        mPresenter = presenter;
    }

    @Provides @ActivityScope
    @ForActivity
    public Context provideActivityContext() {
        return mActivity;
    }

    @Provides @ActivityScope
    public AppCompatActivity provideAppCompatActivity() {
        return mActivity;
    }

    @Provides @ActivityScope
    public S providePresenter() {
        return mPresenter;
    }
}
