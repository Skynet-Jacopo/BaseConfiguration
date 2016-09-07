package cn.usmaker.ben.http.volley;

import android.content.Context;
import android.text.TextUtils;

import com.android.http.LoadControler;
import com.android.http.RequestManager;
import com.android.http.RequestMap;
import com.android.volley.Request;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenzhenyang on 2015/11/19.
 */
public class HttpRequestGet {

    private Context           context;
    private String            url;
    private RequestMap        params;
    private USRequestListener requestListener;


    public HttpRequestGet(Context context, String url, RequestMap params, USRequestListener requestListener) {
        this.context = context;
        this.url = url;
        this.params = params;
        this.requestListener = requestListener;
    }


    public void validate() {
        if (context == null) {
            throw new RuntimeException("context is null");
        }
        if (TextUtils.isEmpty(url)) {
            throw new RuntimeException("url is null");
        }
        if (params == null) {
            throw new RuntimeException("params is null");
        }
        if (requestListener == null) {
            throw new RuntimeException("requestListener is null");
        }
    }

    private static final int TIMEOUT_COUNT = 10 * 1000;

    private static final int RETRY_TIMES = 1;

    public LoadControler doPost() {
        Logger.d("正在请求 [%s] .....，请求参数：[%s]", url, params);
        validate();
        Map<String, String> headers = new HashMap<String, String>();
        if (!TextUtils.isEmpty(SimpleRequestListener.sessionID)) {
            headers.put("Cookie", SimpleRequestListener.sessionID);
        }
        Logger.i("发送Cookie :%s", SimpleRequestListener.sessionID);

        LoadControler mLoadControler = RequestManager.getInstance().request(Request.Method.GET, url, params, headers,
                new SimpleRequestListener(requestListener), false, TIMEOUT_COUNT, RETRY_TIMES, 0);
        return mLoadControler;
    }

    public LoadControler doMultipartPost() {
        Logger.d("正LoadControler在请求 [%s] .....，请求参数：[%s]", url, params);
        validate();
        LoadControler mLoadControler = RequestManager.getInstance().post(url, params, new SimpleRequestListener(requestListener), 2);
        return mLoadControler;
    }

    public LoadControler doPostForJson() {
        Logger.d("正在请求 [%s] .....，请求参数：[%s]", url, params);
        validate();
        Map<String, String> headers = new HashMap<String, String>();
        if (!TextUtils.isEmpty(SimpleRequestListener.sessionID)) {
            headers.put("Cookie", SimpleRequestListener.sessionID);
        }
        Logger.i("发送Cookie :%s", SimpleRequestListener.sessionID);

        LoadControler mLoadControler = RequestManager.getInstance().request(Request.Method.GET, url, params, headers,
                new SimpleFormatRequestListener(requestListener), false, TIMEOUT_COUNT, RETRY_TIMES, 0);
        return mLoadControler;
    }

}
