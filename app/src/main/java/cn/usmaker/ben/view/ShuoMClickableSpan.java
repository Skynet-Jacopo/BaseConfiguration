package cn.usmaker.ben.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import cn.usmaker.ben.util.ToastUtils;

/**
 * Created by Administrator on 2016/9/6.
 *
 * 不同颜色TextView
 */
public class ShuoMClickableSpan extends ClickableSpan {

    String  string;
    Context context;

    public ShuoMClickableSpan(String str, Context context) {
        super();
        this.string = str;
        this.context = context;
    }


    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.BLUE);
    }


    @Override
    public void onClick(View widget) {
//        Intent intent = new Intent();
//        intent.setClass(context, TwoActivity.class);
//        context.startActivity(intent);
        ToastUtils.showToast(context,"你点击了内容头");

    }

}
