package net.onesandzeros.zero.photocast.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.MediaRouteActionProvider;
import android.support.v7.media.MediaRouteSelector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import net.onesandzeros.zero.photocast.R;
import net.onesandzeros.zero.photocast.presentation.fragment.PagerFragment;
import net.onesandzeros.zero.photocast.presentation.view.BaseView;
import net.onesandzeros.zero.photocast.utils.LogUtils;
import net.onesandzeros.zero.photocast.utils.NavigationUtils;

import java.util.Arrays;
import java.util.List;

public    class HomeActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener,
        FacebookCallback<LoginResult>, BaseView.InteractionListener<Object> , GoogleApiClient.ConnectionCallbacks {

    private static final String LOG_TAG = HomeActivity.class.getSimpleName();


    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;


    private static final java.lang.String STATE_RESOLVING_ERROR = "FACEBOOK" ;

    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;



    private MediaRouteSelector  mMediaRouteSelector;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    CallbackManager             mCallbackManager;

    LoginButton                 mLoginButton;

    List<String>                mPermissionNeeds = Arrays.asList("user_photos", "user_videos" ,"email","user_posts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResolvingError = savedInstanceState != null
                && savedInstanceState.getBoolean(STATE_RESOLVING_ERROR, false);


        FacebookSdk.sdkInitialize(getApplicationContext());

        mCallbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_home);

        mLoginButton = (LoginButton) findViewById(R.id.b_login);
        // mLoginButton.setReadPermissions(mPermissionNeeds);
        // mLoginButton.registerCallback(mCallbackManager, this);


        onAccessTokenChanged(AccessToken.getCurrentAccessToken(), AccessToken.getCurrentAccessToken());
        new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                onAccessTokenChanged(oldAccessToken, newAccessToken);
            }
        };


    }




    public boolean onCreateOptionsMenu(Menu menu) {

        MediaRouteSelector mMediaRouteSelector = new MediaRouteSelector.Builder()
                .addControlCategory(CastMediaControlIntent.categoryForCast("F1939F61"))
                .build();


        getMenuInflater().inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_home, menu);
        MenuItem mediaRouteMenuItem = menu.findItem(R.id.media_route_menu_item);

        MediaRouteActionProvider mediaRouteActionProvider =
                (MediaRouteActionProvider) MenuItemCompat.getActionProvider(mediaRouteMenuItem);
            mediaRouteActionProvider.setRouteSelector(mMediaRouteSelector);
        return true;
    }




    protected void onResume() {
        super.onResume();

        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        LogUtils.debugLog(LOG_TAG, "Login Successful " + loginResult.getAccessToken().toString());
    }

    @Override
    public void onCancel() {
        LogUtils.debugLog(LOG_TAG, "Login cancel ");
    }

    @Override
    public void onError(FacebookException e) {
        LogUtils.errorLog(LOG_TAG, "Login failed " + e.toString());
    }

    private void onAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
        if (newAccessToken != null) {
            navigateToFragment();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    private void navigateToFragment() {
        NavigationUtils.startFragment(this.getSupportFragmentManager(), R.id.fl_fragment_container,
                PagerFragment.newInstance(), false, NavigationUtils.NO_ANIMATION);
    }

    @Override
    public void onItemClick(Object item) {

    }
            private void startActivity(Class<?> classType) {
                startActivity(new Intent(HomeActivity.this, classType));
            }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}



