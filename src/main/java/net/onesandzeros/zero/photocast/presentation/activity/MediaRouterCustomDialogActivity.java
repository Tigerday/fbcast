package net.onesandzeros.zero.photocast.presentation.activity;

/**
 * Created by dad on 2016-06-18.
 */

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.MediaRouteActionProvider;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.support.v7.media.MediaRouter.RouteInfo;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;

import net.onesandzeros.zero.photocast.R;

/**
 * Sample activity to demonstrate the use of the MediaRouteActionProvider for
 * adding the Cast button to the action bar. This activity shows how to create a
 * custom MediaRouter control dialog.
 *
 * @see  //developer.android.com/guide/topics/media/mediarouter.html
 */
public class MediaRouterCustomDialogActivity extends ActionBarActivity {

    private static final String TAG = MediaRouterCustomDialogActivity.class
            .getSimpleName();

    private MediaRouter mMediaRouter;
    private MediaRouteSelector mMediaRouteSelector;
    private MediaRouter.Callback mMediaRouterCallback;
    private CastDevice mSelectedDevice;

    private CustomMediaRouteDialogFactory mCustomMediaRouteDialogFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_router_action_bar_button);

        mCustomMediaRouteDialogFactory = new CustomMediaRouteDialogFactory();

        mMediaRouter = MediaRouter.getInstance(getApplicationContext());
        // Create a MediaRouteSelector for the type of routes your app supports
        mMediaRouteSelector = new MediaRouteSelector.Builder()
                .addControlCategory(
                        CastMediaControlIntent.categoryForCast(getResources()
                                .getString(R.string.app_id))).build();
        // Create a MediaRouter callback for discovery events
        mMediaRouterCallback = new MyMediaRouterCallback();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_home, menu);
        MenuItem mediaRouteMenuItem = menu.findItem(R.id.media_route_menu_item);
        MediaRouteActionProvider mediaRouteActionProvider = (MediaRouteActionProvider) MenuItemCompat
                .getActionProvider(mediaRouteMenuItem);
        // Set the MediaRouteActionProvider selector for device discovery.
        mediaRouteActionProvider.setRouteSelector(mMediaRouteSelector);
        // Set the custom dialog factory for a custom control dialog
        mediaRouteActionProvider
                .setDialogFactory(mCustomMediaRouteDialogFactory);
        return true;
    }

    private class MyMediaRouterCallback extends MediaRouter.Callback {

        @Override
        public void onRouteSelected(MediaRouter router, RouteInfo info) {
            Log.d(TAG, "onRouteSelected");
            // Handle route selection.
            mSelectedDevice = CastDevice.getFromBundle(info.getExtras());

            // Just display a message for now; In a real app this would be the
            // hook to connect to the selected device and launch the receiver
            // app
            Toast.makeText(
                    MediaRouterCustomDialogActivity.this,
                    getString(R.string.todo_connect) + '\n'
                            + getString(R.string.custom_dialog),
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onRouteUnselected(MediaRouter router, RouteInfo info) {
            Log.d(TAG, "onRouteUnselected: info=" + info);
            mSelectedDevice = null;
        }
    }
}