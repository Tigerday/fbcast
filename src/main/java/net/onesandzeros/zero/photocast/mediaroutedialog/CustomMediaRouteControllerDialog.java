package net.onesandzeros.zero.photocast.mediaroutedialog;

/**
 * Created by dad on 2016-06-18.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.MediaRouteControllerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import net.onesandzeros.zero.photocast.R;


/**
 * This class extends {@link MediaRouteControllerDialog}, and provides a
 * user-defined media route controller dialog.
 */
public class CustomMediaRouteControllerDialog extends
        MediaRouteControllerDialog {

    private TextView mTextView;

    public CustomMediaRouteControllerDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateMediaControlView(Bundle savedInstanceState) {
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(
                R.layout.custom_media_router_controller_dialog_fragment, null);
        mTextView = (TextView) customView.findViewById(R.id.title);
        mTextView.setText(R.string.custom_media_router_control_dialog_text);
        return customView;
    }

}