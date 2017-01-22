package io.github.mssjsg.android.base.application;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sing on 1/22/17.
 */
@Module
public class AppModule {
    private MssApplication mApplication;

    public AppModule(MssApplication application) {
        mApplication = application;
    }

    @Provides @Singleton @ForApplication
    public Context provideApplication() {
        return mApplication;
    }
}
