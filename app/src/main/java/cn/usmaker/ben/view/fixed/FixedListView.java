package cn.usmaker.ben.view.fixed;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by chenzhenyang on 2015/7/15.
 */
public class FixedListView extends ListView {
    public FixedListView(Context context) {
        super(context);
    }

    public FixedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int width,int height){
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
        super.onMeasure(width,expandSpec);
    }

}
