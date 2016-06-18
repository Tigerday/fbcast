package net.onesandzeros.zero.photocast.mediaroutedialog;

/**
 * Created by dad on 2016-06-18.
 */

import android.support.v7.app.MediaRouteControllerDialogFragment;
import android.support.v7.app.MediaRouteDialogFactory;

/**
 * This class extends {@link MediaRouteDialogFactory}, and provides a custom
 * implementation of {@link MediaRouteControllerDialogFragment}. It provides
 * access to {@link CustomMediaRouteControllerDialog}.
 */
public class CustomMediaRouteDialogFactory extends MediaRouteDialogFactory {

    private static final CustomMediaRouteDialogFactory sDefault = new CustomMediaRouteDialogFactory();

    /**
     * Creates a new SampleMediaRouteDialogFactory.
     */
    public CustomMediaRouteDialogFactory() {
        super();
    }

    /**
     * Returns a default SampleMediaRouteDialogFactory.
     */
    public static CustomMediaRouteDialogFactory getDefault() {
        return sDefault;
    }

    @Override
    public CustomMediaRouteControllerDialogFragment onCreateControllerDialogFragment() {
        return new CustomMediaRouteControllerDialogFragment();
    }

}
