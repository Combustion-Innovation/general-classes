package com.ci.generalclasses.loginmanagers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Alex on 12/3/14.
 * LoginManager is a login utility class that makes it easy to reuse login code.
 * It allows a proprietary login. a facebook login, and a twitter login.
 */
public class LoginManager {
    private Activity activity;
    private String url;

    /**
     *
     * @param activity the activity that will be notified of a login
     * @param url the login URL
     */
    public LoginManager(Activity activity, String url) {
        this.activity = activity;
        this.url = url;
    }

    /**
     * logs user in using a proprietary account
     * @param data a list of user data to send to the server
     */
    public void loginProprietary(ArrayList<BasicNameValuePair> data) {
        System.out.println("URL:\t\t\t" + url);
        System.out.println("username:\t\t" + data.get(0).getValue());
        // do login stuff
    }

    /**
     * logs user in using a Facebook account
     */
    /*public void loginFB(Fragment frag) {
        FBLogin login = new FBLogin(frag, );
        login.login();
    }*/

    /**
     * logs user in using a Twitter account
     */
    public void loginTwitter() {

    }
}
