package io.github.mssjsg.android.base.dagger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by sing on 1/22/17.
 */

public class DaggerWorkerFragment extends Fragment {

    private Object mComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public Object getComponent() {
        return mComponent;
    }

    public void setComponent(Object component) {
        mComponent = component;
    }
}
