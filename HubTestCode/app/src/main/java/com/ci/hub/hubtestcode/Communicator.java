package com.ci.hub.hubtestcode;

import org.json.JSONObject;

public interface Communicator {
    public void gotResponse(JSONObject data);
    public void gotSilentResponse(JSONObject data);
}