package com.ci.generalclasses.loginmanagers;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 12/20/14.
 */
public class ProprietaryLoginTask extends AsyncTask<String, String, String> {
    private Activity activity;
    private final String TAG = "ProprietaryLoginTask";
    @Override
    protected String doInBackground(String... params) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(params[0]);

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("username", params[1]));
        pairs.add(new BasicNameValuePair("password", params[2]));
        HttpResponse response = null;
        String result = null;
        try {
            post.setEntity(new UrlEncodedFormEntity(pairs));
            response = client.execute(post);
            result = EntityUtils.toString(response.getEntity());
        } catch(Exception e) {
            e.printStackTrace();
        }

        return response != null ? result : "Failed";
    }

    protected void onProgressUpdate(String... progress) {
        for (int i = 0; i < progress.length; i++) {
            Log.d(TAG, progress[i]);
        }
    }

    @Override
    protected void onPostExecute(String result) {
        //Log.d("MyAsyncTask", result);
        try {
            ((Communicator) activity).gotResponse(new JSONObject(result));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
