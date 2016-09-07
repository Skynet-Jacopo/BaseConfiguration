package cn.usmaker.ben.view.util;

import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by chenzhenyang on 2016/6/4.
 */
public class DialogUtil {

    public static void setSizeAndPosition(Activity activity, Dialog dialog) {
        Window                     win          = dialog.getWindow();
        WindowManager              manager      = activity.getWindowManager();
        Display                    display      = manager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = win.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        layoutParams.verticalMargin = 0.4f;
        layoutParams.width = (int) (display.getWidth() * 0.4);
        layoutParams.height = (int) (display.getWidth() * 0.4);
    }

}
