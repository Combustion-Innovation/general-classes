package com.ci.generalclasses.loginmanagers;

/**
 * Created by Alex on 12/3/14.
 * LoginManager is a login utility class that makes it easy to reuse login code.
 * It allows a proprietary login. a facebook login, and a twitter login.
 */
public class LoginManager {
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
     android:id="@+id/FBauthButton"
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
}
