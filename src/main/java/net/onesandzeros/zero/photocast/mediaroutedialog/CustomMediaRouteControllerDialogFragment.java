package net.onesandzeros.zero.photocast.mediaroutedialog;

/**
 * Created by dad on 2016-06-18.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.MediaRouteControllerDialog;
import android.support.v7.app.MediaRouteControllerDialogFragment;

/**
 * This class extends {@link MediaRouteControllerDialogFragment}, and provides a
 * user-defined media route controller fragment. It also provides access to
 * {@link CustomMediaRouteControllerDialog}.
 */
public class CustomMediaRouteControllerDialogFragment extends
        MediaRouteControllerDialogFragment {

    private CustomMediaRouteControllerDialog mControllerDialog;

    public CustomMediaRouteControllerDialogFragment() {
        super();
    }

    @Override
    public MediaRouteControllerDialog onCreateControllerDialog(Context context,
                                                               Bundle savedInstanceState) {
        mControllerDialog = new CustomMediaRouteControllerDialog(context);

        // Hide the volume slider.
        mControllerDialog.setVolumeControlEnabled(false);
        return mControllerDialog;
    }

    public CustomMediaRouteControllerDialog getControllerDialog() {
        return mControllerDialog;
    }
}