package io.github.mssjsg.android.base.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import io.github.mssjsg.android.base.dagger.module.AppModule;
import io.github.mssjsg.android.base.dagger.module.DemoModule;
import io.github.mssjsg.android.base.util.executor.ExecutorModule;

/**
 * Created by sing on 1/22/17.
 */
@Singleton
@Component(modules = {AppModule.class, ExecutorModule.class})
public interface DemoAppComponent {
    DemoComponent just(DemoModule demoModule);
}
