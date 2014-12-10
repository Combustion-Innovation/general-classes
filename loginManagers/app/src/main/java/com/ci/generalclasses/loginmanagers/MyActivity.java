package com.ci.generalclasses.loginmanagers;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AppEventsLogger;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;

import org.json.JSONObject;

import java.util.Arrays;


public class MyActivity extends FragmentActivity implements Communicator {
    private FBFragment fbFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            fbFragment = new FBFragment();
            fbFragment.setCallback(new Request.GraphUserCallback() {
                public void onCompleted(GraphUser user, Response response) {
                    JSONObject data = new JSONObject();
                    try {
                        data.put("_user", user);
                        data.put("_response", response);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("FBFragment", "Exception e");
                    }
                    if (response != null) {
                        // do something with <response> now
                        try {
                            data.put("email", response.getGraphObject().getProperty("email"));
                            data.put("first_name", user.getFirstName());
                            data.put("last_name", user.getLastName());
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("FBFragment", "Exception e");
                        }
                        gotResponse(data);
                    }

                }
            });
            fbFragment.setPermissions(Arrays.asList("email"));
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fbFragment)
                    .commit();
        } else {
            // Or set the fragment from restored state info
            fbFragment = (FBFragment) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
    */

    @Override
    public void gotResponse(JSONObject s) {
        try {
            System.out.println(s.toString(4));
        } catch (Exception e) {

        }
    }
}
