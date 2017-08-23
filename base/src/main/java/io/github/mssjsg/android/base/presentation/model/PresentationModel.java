package io.github.mssjsg.android.base.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sing on 23/8/2017.
 */
public abstract class PresentationModel implements Parcelable {

    public PresentationModel() {
    }

    protected PresentationModel(Parcel in) {
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
