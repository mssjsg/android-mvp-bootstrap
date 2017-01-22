package io.github.mssjsg.android.base.dagger.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.mssjsg.android.base.dagger.qualifier.ForApplication;

/**
 * Created by sing on 1/22/17.
 */
@Module
public class AppModule {
    private Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides @Singleton @ForApplication
    public Context provideApplicationContext() {
        return mApplication;
    }
}
