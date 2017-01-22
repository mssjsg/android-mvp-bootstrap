package io.github.mssjsg.android.base.demo;

import android.app.Application;

import io.github.mssjsg.android.base.dagger.component.DaggerDemoAppComponent;
import io.github.mssjsg.android.base.dagger.component.DemoAppComponent;
import io.github.mssjsg.android.base.dagger.module.AppModule;
import io.github.mssjsg.android.base.util.executor.ExecutorModule;

/**
 * Created by sing on 1/22/17.
 */

public class DemoApplication extends Application {

    private DemoAppComponent mDemoAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mDemoAppComponent = DaggerDemoAppComponent.builder()
                .appModule(new AppModule(this))
                .executorModule(new ExecutorModule()).build();
    }

    public DemoAppComponent getComponent() {
        return mDemoAppComponent;
    }
}
