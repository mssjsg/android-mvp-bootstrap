package io.github.mssjsg.android.base.demo;

import dagger.Subcomponent;
import io.github.mssjsg.android.base.demo.DemoActivity;
import io.github.mssjsg.android.base.dagger.ActivityScope;

/**
 * Created by sing on 1/22/17.
 */

@ActivityScope
@Subcomponent(modules = DemoModule.class)
public interface DemoComponent {
    void inject(DemoActivity demoActivity);
}
