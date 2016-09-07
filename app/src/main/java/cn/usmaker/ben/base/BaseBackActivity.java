package cn.usmaker.ben.base;

import android.os.Bundle;
import android.view.KeyEvent;

import com.zhy.autolayout.AutoLayoutActivity;

import cn.usmaker.ben.util.ToastUtils;


/**
 * 一般是各个一级页面需要这么做。<br/>
 *
 * Created by chenzhenyang on 2015/11/23.
 */
public class BaseBackActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //TOOD 再按一次返回
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                ToastUtils.showToast(getApplicationContext(), "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
