package net.onesandzeros.zero.photocast.presentation.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;


import com.google.android.gms.common.api.GoogleApiClient;


import net.onesandzeros.zero.photocast.utils.LogUtils;

public abstract class BaseActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {



    private static final String LOG_TAG = "BASE_ACTIVITY";

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("PhotoCast");
    }

    @Override
    public void onStart() {
        LogUtils.debugLog(LOG_TAG, "[LIFECYCLE] onStart(): " + this.getClass().getSimpleName());
        super.onStart();
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        LogUtils.debugLog(LOG_TAG, "[LIFECYCLE] onActivityResult(): " + this.getClass().getSimpleName());
        super.onActivityResult(arg0, arg1, arg2);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR
                && keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // Taof
            // the platform where it doesn't exist.ke care of calling this method on earlier versions
            onBackPressed();
        }

        return super.onKeyDown(keyCode, event);
    }





    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
        } catch (IllegalStateException ex) {
            LogUtils.errorLog(LOG_TAG, "Cannot transact after activity onPause", ex);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            onBackPressed();
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
