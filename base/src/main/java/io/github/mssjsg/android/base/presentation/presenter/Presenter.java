package io.github.mssjsg.android.base.presentation.presenter;

import io.github.mssjsg.android.base.presentation.PresentationModel;

/**
 * Created by sing on 1/22/17.
 */

public interface Presenter<View> {

    void bindView(View view);

    void unbindView(View view);

    void dispose();
}
