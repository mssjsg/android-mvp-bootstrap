package io.github.mssjsg.android.base.presentation;

import android.os.Bundle;

/**
 * Created by Sing on 8/7/2017.
 * Keep formatted data for UI display, it also keeps the logic in saving and restoring UI states.
 */

public interface PresentationModel {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyDataChanged();

    void saveState(Bundle bundle);

    void restoreState(Bundle bundle);

    interface Observer {
        void onViewModelChanged();
    }
}
