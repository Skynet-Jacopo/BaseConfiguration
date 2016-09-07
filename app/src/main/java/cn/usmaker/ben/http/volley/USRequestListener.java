package cn.usmaker.ben.http.volley;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by chenzhenyang on 2015/11/19.
 */
public interface USRequestListener {
     void onSuccess(JSONObject response);

     void onFailure(JSONObject response);

     void onError(VolleyError error);
}
