package cn.usmaker.ben.http.volley;

import com.android.volley.VolleyError;

/**
 * Created by Administrator on 2015/11/25.
 */
public interface OnSuccessListener<T>{
    public void onSuccess(T t);
    public void onFailed(String t);
    public void onError(VolleyError error);
}