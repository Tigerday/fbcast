package net.onesandzeros.zero.photocast.presentation.activity;

/**
 * Created by dad on 2016-06-18.
 */

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.MediaRouteButton;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.support.v7.media.MediaRouter.RouteInfo;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;

import net.onesandzeros.zero.photocast.R;

/**
 * Sample activity to demonstrate the use of MediaRouteButton. This activity
 * does not have an action bar. This activity is responsible for setting the
 * visibility of the Cast button.
 *
 * @see ://developer.android.com/guide/topics/media/mediarouter.html
 */
public class MediaRouterButtonActivity extends FragmentActivity {

    private static final String TAG = MediaRouterButtonActivity.class
            .getSimpleName();

    private MediaRouter mMediaRouter;
    private MediaRouteSelector mMediaRouteSelector;
    private MediaRouter.Callback mMediaRouterCallback;
    private MediaRouteButton mMediaRouteButton;
    private CastDevice mSelectedDevice;
    private int mRouteCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_router_button);

        mMediaRouter = MediaRouter.getInstance(getApplicationContext());
        // Create a MediaRouteSelector for the type of routes your app supports
        mMediaRouteSelector = new MediaRouteSelector.Builder()
                .addControlCategory(
                        CastMediaControlIntent.categoryForCast(getResources()
                                .getString(R.string.app_id))).build();
        // Create a MediaRouter callback for discovery events
        mMediaRouterCallback = new MyMediaRouterCallback();

        // Set the MediaRouteButton selector for device discovery.
        mMediaRouteButton = (MediaRouteButton) findViewById(R.id.media_route_button);
        mMediaRouteButton.setRouteSelector(mMediaRouteSelector);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Add the callback to start device discovery
        mMediaRouter.addCallback(mMediaRouteSelector, mMediaRouterCallback,
                MediaRouter.CALLBACK_FLAG_REQUEST_DISCOVERY);
    }

    @Override
    protected void onPause() {
        // Remove the callback to stop device discovery
        mMediaRouter.removeCallback(mMediaRouterCallback);
        super.onPause();
    }

    private class MyMediaRouterCallback extends MediaRouter.Callback {
        @Override
        public void onRouteAdded(MediaRouter router, RouteInfo route) {
            Log.d(TAG, "onRouteAdded");
            if (++mRouteCount == 1) {
                // Show the button when a device is discovered.
                mMediaRouteButton.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onRouteRemoved(MediaRouter router, RouteInfo route) {
            Log.d(TAG, "onRouteRemoved");
            if (--mRouteCount == 0) {
                // Hide the button if there are no devices discovered.
                mMediaRouteButton.setVisibility(View.GONE);
            }
        }

        @Override
        public void onRouteSelected(MediaRouter router, RouteInfo info) {
            Log.d(TAG, "onRouteSelected");
            // Handle route selection.
            mSelectedDevice = CastDevice.getFromBundle(info.getExtras());

            // Just display a message for now; In a real app this would be the
            // hook to connect to the selected device and launch the receiver
            // app
            Toast.makeText(MediaRouterButtonActivity.this,
                    getString(R.string.todo_connect), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onRouteUnselected(MediaRouter router, RouteInfo info) {
            Log.d(TAG, "onRouteUnselected: info=" + info);
            mSelectedDevice = null;
        }
    }

}