/*
 * Copyright (c) 2014, 青岛司通科技有限公司 All rights reserved.
 * File Name：RushBuyCountDownTimerView.java
 * Version：V1.0
 * Author：zhaokaiqiang
 * Date：2014-9-26
 */
package cn.usmaker.ben.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import cn.usmaker.base.R;


@SuppressLint("HandlerLeak")
public class RushBuyCountDownTimerView extends LinearLayout {
    //天,十位
    private TextView tv_day_decade;
    //天,个位
    private TextView tv_day_unit;
    // 小时，十位
    private TextView tv_hour_decade;
    // 小时，个位
    private TextView tv_hour_unit;
    // 分钟，十位
    private TextView tv_min_decade;
    // 分钟，个位
    private TextView tv_min_unit;
    // 秒，十位
    private TextView tv_sec_decade;
    // 秒，个位
    private TextView tv_sec_unit;

    private TextView tv_day;
    private TextView tv_hour;
    private TextView tv_min;
    private TextView tv_sec;
    private Context  context;

    private int   day_decade;
    private int   day_unit;
    private int   hour_decade;
    private int   hour_unit;
    private int   min_decade;
    private int   min_unit;
    private int   sec_decade;
    private int   sec_unit;
    // 计时器
    private Timer timer;

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            countDown();
        }

        ;
    };

    public RushBuyCountDownTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_countdowntimer, this);

        tv_day_decade = (TextView) view.findViewById(R.id.tv_day_decade);
        tv_day_unit = (TextView) view.findViewById(R.id.tv_day_unit);
        tv_hour_decade = (TextView) view.findViewById(R.id.tv_hour_decade);
        tv_hour_unit = (TextView) view.findViewById(R.id.tv_hour_unit);
        tv_min_decade = (TextView) view.findViewById(R.id.tv_min_decade);
        tv_min_unit = (TextView) view.findViewById(R.id.tv_min_unit);
        tv_sec_decade = (TextView) view.findViewById(R.id.tv_sec_decade);
        tv_sec_unit = (TextView) view.findViewById(R.id.tv_sec_unit);
        tv_day = (TextView) view.findViewById(R.id.tv_day);
        tv_hour = (TextView) view.findViewById(R.id.tv_hour);
        tv_min = (TextView) view.findViewById(R.id.tv_min);
        tv_sec = (TextView) view.findViewById(R.id.tv_sec);
        AssetManager assetManager = getContext().getAssets();
        Typeface     typeface     = Typeface.createFromAsset(assetManager, "fonts/msyh.ttc");

        tv_day.setTypeface(typeface);
        tv_hour.setTypeface(typeface);
        tv_min.setTypeface(typeface);
        tv_sec.setTypeface(typeface);
    }

    /**
     * @param
     * @return void
     * @throws
     * @Description: 开始计时
     */
    public void start() {

        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            }, 0, 1000);
        }
    }

    /**
     * @param
     * @return void
     * @throws
     * @Description: 停止计时
     */
    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * @param
     * @return void
     * @throws Exception
     * @throws
     * @Description: 设置倒计时的时长
     */
    public void setTime(int hour, int min, int sec) {

        if (hour >= 100 * 24 || min >= 60 || sec >= 60 || hour < 0 || min < 0
                || sec < 0) {
            throw new RuntimeException("时间格式输入有误,请检查您的代码!");
        }
        day_decade = hour / 240;
        day_unit = hour / 24 - day_decade * 10;

        hour_decade = (hour - (day_decade * 10 + day_unit) * 24) / 10;
        hour_unit = hour - (day_decade * 10 + day_unit) * 24 - hour_decade * 10;

        min_decade = min / 10;
        min_unit = min - min_decade * 10;

        sec_decade = sec / 10;
        sec_unit = sec - sec_decade * 10;

        tv_day_decade.setText(day_decade + "");
        tv_day_unit.setText(day_unit + "");
        tv_hour_decade.setText(hour_decade + "");
        tv_hour_unit.setText(hour_unit + "");
        tv_min_decade.setText(min_decade + "");
        tv_min_unit.setText(min_unit + "");
        tv_sec_decade.setText(sec_decade + "");
        tv_sec_unit.setText(sec_unit + "");

    }

    /**
     * @param
     * @return boolean
     * @throws
     * @Description: 倒计时
     */
    private void countDown() {

        if (isCarry4Unit(tv_sec_unit)) {
            if (isCarry4Decade(tv_sec_decade)) {

                if (isCarry4Unit(tv_min_unit)) {
                    if (isCarry4Decade(tv_min_decade)) {

                        if (isCarryUnit(tv_hour_unit)) {
                            if (isCarryDecade(tv_hour_decade)) {

                                if (isCarry4Unit(tv_day_unit)) {
                                    if (isCarry4Decade(tv_day_decade)) {
                                        Toast.makeText(context, "时间到了",
                                                Toast.LENGTH_SHORT).show();
                                        stop();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param
     * @return boolean
     * @throws
     * @Description: 变化十位，并判断是否需要进位
     */
    private boolean isCarry4Decade(TextView tv) {

        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 5;
            tv.setText(time + "");
            return true;
        } else {
            tv.setText(time + "");
            return false;
        }

    }

    /**
     * @param
     * @return boolean
     * @throws
     * @Description: 变化个位，并判断是否需要进位
     */
    private boolean isCarry4Unit(TextView tv) {

        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 9;
            tv.setText(time + "");
            return true;
        } else {
            tv.setText(time + "");
            return false;
        }
    }

    private boolean isCarryDecade(TextView tv) {

        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 2;
            tv.setText(time + "");
            return true;
        } else {
            tv.setText(time + "");
            return false;
        }

    }

    /**
     * @param
     * @return boolean
     * @throws
     * @Description: 变化个位，并判断是否需要进位
     */
    private boolean isCarryUnit(TextView tv) {

        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 3;
            tv.setText(time + "");
            return true;
        } else {
            tv.setText(time + "");
            return false;
        }
    }
}
