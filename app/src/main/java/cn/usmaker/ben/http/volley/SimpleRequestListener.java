package cn.usmaker.ben.http.volley;

import android.text.TextUtils;

import com.android.http.RequestManager;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by chenzhenyang on 2015/11/19.
 */
public class SimpleRequestListener implements RequestManager.RequestListener {

    private USRequestListener requestListener;

    public   SimpleRequestListener(USRequestListener requestListener){
        this.requestListener =requestListener;
    }

    @Override
    public void onRequest() {

    }

    public static String sessionID = null;

    @Override
    public void onSuccess(String response, Map<String, String> headers, String url, int actionId) {
        //TODO 在这把headers的cookie信息保存起来
        //服务器有响应
        Logger.d("请求URL[%s]，服务器端的响应：%s",url,response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            boolean    success    = jsonObject.getBoolean("success");

            String tempSessionID = headers.get("Set-Cookie");
            if(!TextUtils.isEmpty(tempSessionID)) {
//                Logger.i("原始Cookie:%s",tempSessionID);
               //String code = tempSessionID.substring(tempSessionID.indexOf("="),tempSessionID.indexOf(";"));
//                sessionID = tempSessionID.substring(0, tempSessionID.indexOf(";"));// 获取sessionID
                sessionID = tempSessionID;
            }
            Logger.i("接受Cookie:%s",sessionID);
            if (success) {
                requestListener.onSuccess(jsonObject);
            } else {
                requestListener.onFailure(jsonObject);
            }

        } catch (JSONException e) {
            Logger.e("JSONException:%s", e.getMessage());
        }
    }

    @Override
    public void onError(String errorMsg, String url, int actionId) {
        Logger.e("errorMsg:%s", errorMsg);
//        error.printStackTrace();
        //服务器没有响应
//
//                Log.d("TAG", String.format("%s，error：%s", operater, error.getMessage()));
//                onVolleyLoadDataListener.onError(error);
    }
}
