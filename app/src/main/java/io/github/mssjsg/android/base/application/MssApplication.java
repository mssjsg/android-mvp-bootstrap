package io.github.mssjsg.android.base.application;

import android.app.Application;

import io.github.mssjsg.base.DaggerAppComponent;
/**
 * Created by sing on 1/22/17.
 */

public class MssApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
