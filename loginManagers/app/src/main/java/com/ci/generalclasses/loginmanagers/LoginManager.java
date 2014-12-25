package com.ci.generalclasses.loginmanagers;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Alex on 12/3/14.
 * LoginManager is a login utility class that makes it easy to reuse login code.
 * It allows a proprietary login. a facebook login, and a twitter login.
 */
public class LoginManager {
    private static final String TAG = "LoginManager";
    private static String proprietaryLoginURL = "http://httpbin.org/post";
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static String TWITTER_KEY;
    private static String TWITTER_SECRET;
    /**
     * Before using getFBLogin, you need to do the following
     * 1. add the following to AndroidManifest.xml
     *  - these go just under manifest
     *      - <meta-data android:name="com.facebook.sdk.ApplicationId" android:value="@string/app_id"/>
     *      - <uses-permission android:name="android.permission.INTERNET" />
     *  - these go under application
     *      - <uses-permission android:name="android.permission.SET_DEBUG_APP"/>
     *      - <activity
     android:name="com.facebook.LoginActivity"
     android:label="@string/app_name" >
     <intent-filter>
     <action android:name="android.intent.action.MAIN" />
     <category android:name="android.intent.category.LAUNCHER" />
     </intent-filter>
     </activity>
     * 2. add the following to strings.xml
     *  - <string name="app_id">YOUR APP ID HERE</string>
     * 3. add the following to activity_my.xml
     *  - <com.facebook.widget.LoginButton
     android:id="@+id/fb_auth_buttonon"
     android:layout_width="wrap_content"
     android:layout_height="wrap_content"
     android:layout_gravity="center_horizontal"
     android:layout_marginTop="30dp"
     />
     *
     *  - ^This adds the facebook login button, so you can change its styling however you like
     * 4. add the following to your activity's onCreate method
     *  - if (savedInstanceState == null) {
             getSupportFragmentManager()
             .beginTransaction()
             .add(android.R.id.content, THE NAME OF YOUR FBFragment)
             .commit();
             } else {
             // Or set the fragment from restored state info
             // this will be custom code
             }
     *
     */
    public static FBFragment getFBLogin() {
        return new FBFragment();
    }

    public static ProprietaryFragment initProprietaryLogin(final Activity activity) {
        final ProprietaryFragment proprietaryFragment = new ProprietaryFragment();
        proprietaryFragment.setActivity(activity);
        proprietaryFragment.setLoginURL(proprietaryLoginURL);
        Button button = (Button) activity.findViewById(R.id.proprietary_login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container, (Fragment) proprietaryFragment);
                //ft.setTransition(transition);
                ft.addToBackStack(null);
                ft.commit();
                Log.d(TAG, "Proprietary login!");
            }
        });
        return proprietaryFragment;
    }

    public static void setTwitterKeys(String key, String secret) {
        TWITTER_KEY = key;
        TWITTER_SECRET = secret;
    }

    public static TwitterLoginButton initTwitterLogin(final Activity activity, Callback<TwitterSession> callback) {
        TwitterLoginButton twitterLoginButton = (TwitterLoginButton) activity.findViewById(R.id.twitter_login_button);;
        final TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);

        Fabric.with(activity, new Twitter(authConfig));
        twitterLoginButton.setCallback(callback);

        return twitterLoginButton;
    }
}
