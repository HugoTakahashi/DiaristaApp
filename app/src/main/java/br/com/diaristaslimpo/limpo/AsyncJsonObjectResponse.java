package br.com.diaristaslimpo.limpo;

import org.json.JSONObject;

/**
 * Created by Hugo on 10/11/2016.
 */

public interface AsyncJsonObjectResponse {
    void processFinish(JSONObject output);
}