package io.github.mssjsg.android.base.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sing on 1/22/17.
 */

@Module
public abstract class ActivityModule<T extends AppCompatActivity> {

    private T mActivity;

    public ActivityModule(T activity) {
        mActivity = activity;
    }

    @Provides @ActivityScope @ForActivity
    public Context provideActivityContext() {
        return mActivity;
    }

    @Provides @ActivityScope
    public T provideActivity() {
        return mActivity;
    }

    @Provides @ActivityScope
    public AppCompatActivity provideAppCompatActivity() {
        return mActivity;
    }
}
