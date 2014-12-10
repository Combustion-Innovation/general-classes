package com.ci.generalclasses.loginmanagers;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Alex on 12/5/14.
 */
public class FBLogin {
    private LoginButton loginButton;
    private static final String TAG = "FBFragment";
    private Fragment fragment;

    public FBLogin(Fragment frag, LoginButton loginButton) {
        System.out.println("IN FBLogin: " + loginButton);
        this.loginButton = loginButton;
        this.loginButton.setReadPermissions(Arrays.asList("user_likes", "email"));

        fragment = frag;
    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
        }
    }

    public JSONObject login() {
        Session session = Session.getActiveSession();
        //final TextView userInfoTextView = (TextView) fragment.getActivity().findViewById(R.id.textView);

        Request.newMeRequest(session, new Request.GraphUserCallback() {
            @Override
            public void onCompleted(GraphUser user, Response response) {
                if (user != null) {
                    // Display the parsed user info
                    System.out.println(user.getFirstName());
                    //userInfoTextView.setText(user.getFirstName() + " " + user.getLastName());
                } else {
                    System.out.println(response.getError().getErrorMessage());
                }
            }
        }).executeAsync();

        return null;
    }
}
