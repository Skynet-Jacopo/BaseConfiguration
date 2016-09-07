package cn.usmaker.ben.base;

import android.app.Application;

import com.android.http.RequestManager;
import com.android.volley.toolbox.ImageLoader;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.AndroidLogTool;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import cn.usmaker.base.BuildConfig;
import cn.usmaker.ben.view.imageindicator.NetworkImageCache;



/**
 * Application基类，主要用来初始化各个框架
 * Created by chenzhenyang on 2015/11/18.
 */
public class UMApplication extends Application {

    private static final UMApplication instance = new UMApplication();

    public static UMApplication getInstance() {
        return instance;
    }


    private static ImageLoader sImageLoader = null;

    private final NetworkImageCache imageCacheMap = new NetworkImageCache();

    public static ImageLoader getImageLoader() {
        return sImageLoader;
    }

    // 定义是否是强制显示log的模式
    protected static final boolean LOG = false;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Logger
        Logger.init()                 // default PRETTYLOGGER or use just init()
                .methodCount(2)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE)
                .methodOffset(2)                // default 0
                .logTool(new AndroidLogTool()); // custom log tool, optional

        if (UMApplication.LOG) {
            Logger.init().logLevel(LogLevel.FULL);
        }

        //初始化Android Volley Manager
        RequestManager.getInstance().init(this);
        sImageLoader = new ImageLoader(RequestManager.getInstance()
                .getRequestQueue(), imageCacheMap);

        //初始化Fresco
        Fresco.initialize(this);
    }
}
