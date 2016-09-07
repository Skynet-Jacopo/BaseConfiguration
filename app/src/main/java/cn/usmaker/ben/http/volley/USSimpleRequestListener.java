package cn.usmaker.ben.http.volley;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenzhenyang on 2015/11/19.
 */
public class USSimpleRequestListener implements USRequestListener {

    private JSONObject mResponse;
    /**
     * 获取服务器返回的JSON中的success字段的值
     * @return
     */
    public boolean getSuccess(){
        boolean result = false;
        try {
            result = mResponse.getBoolean("success");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取服务器返回的JSON中的code字段的值
     * @return
     */
    public Long getCode(){
        Long result = null;
        try {
            result = mResponse.getLong("code");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取服务器返回的JSON中的msg字段的值
     * @return
     */
    public String getMsg(){
        String result = null;
        try {
            result = mResponse.getString("msg");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取服务器返回的JSON中的infor字段的JSONObject对象
     * @return
     */
    public JSONObject getInfor( ){
        JSONObject result = null;
        try {
            result = mResponse.getJSONObject("infor");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取Infor对象中的列表的值
     * 例如：
     * infor={users:{_total:100,listItems:[]}}
     * 则
     * getJSONObjectFromInfor("users")返回的值为{_total:100,listItems:[]}
     *
     * @param objectName
     * @return
     */
    public JSONObject getJSONObjectFromInfor(String objectName){
        JSONObject result = null;
        try {
            result = getInfor().getJSONObject(objectName);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取Infor对象中的列表的值
     * 例如：
     * infor={users:{_total:100,listItems:[]}}
     * 则
     * getJSONArrayFromInfor("users")返回的值为listItems所指向的数组
     *
     * @param objectName
     * @return
     */
    public JSONArray getJSONArrayFromInfor(String objectName){
        JSONArray array = null;
        try {
            array = getInfor().getJSONObject(objectName).getJSONArray("listItems");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return array;
    }

    private Context context;


    public USSimpleRequestListener(Context context) {
        this.context = context;
    }

    @Override
    public void onSuccess(JSONObject response) {
        mResponse =response;
    }

    @Override
    public void onFailure(JSONObject response) {

    }

    @Override
    public void onError(VolleyError error) {
//        ToastUtil.simpleToast(context, "请检查网络");
    }



}
