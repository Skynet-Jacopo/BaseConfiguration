package cn.usmaker.ben.util;

import android.content.Context;

import com.github.johnpersano.supertoasts.SuperToast;

/**
 * Toast工具类
 * Created by chenzhenyang on 2015/11/23.
 */
public class ToastUtils {
    private static String oldMsg;
    protected static SuperToast superToast = null;
    private static long oneTime=0;
    private static long twoTime=0;

    /**
     * 显示一个带图片的Toast,图片在文字信息左边
     * @param context
     * @param icon  图标，SuperToast.Icon提供了几个，可以直接传入R.drawable里的图片
     * @param message 显示的信息
     */
    public static void showToast(Context context, int icon, String message){
        if(superToast==null){
            superToast = new SuperToast(context);
            superToast.setDuration(SuperToast.Duration.SHORT);
            superToast.setText(message);
            if(icon!=-1) {
                superToast.setIcon(icon, SuperToast.IconPosition.LEFT);
            }
            superToast.show();
            oneTime= System.currentTimeMillis();
        }else{
            twoTime= System.currentTimeMillis();
            if(message.equals(oldMsg)){
                if(twoTime-oneTime> SuperToast.Duration.SHORT){
                    superToast.show();
                }
            }else{
                superToast.cancelAllSuperToasts();//取消当前所有的superToast
                oldMsg = message;
                superToast.setDuration(SuperToast.Duration.SHORT);
                superToast.setText(message);
                if(icon!=-1) {
                    superToast.setIcon(icon, SuperToast.IconPosition.LEFT);
                }
                superToast.show();
            }
        }
        oneTime=twoTime;
    }


    /**
     * 显示一个只有文本信息的Toast
     * @param context
     * @param message 要显示的文本信息
     */
    public static void showToast(Context context, String message){
        showToast(context, -1,message);
    }
}
