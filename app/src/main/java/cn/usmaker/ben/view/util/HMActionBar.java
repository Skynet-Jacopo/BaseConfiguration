package cn.usmaker.ben.view.util;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.usmaker.base.R;


/**
 * Custom Common ActionBar
 */
public class HMActionBar extends RelativeLayout {

    private Context mContext;

    private View mRootView;

    private TextView  mLeftTextView;
    private ImageView mLeftImageView;
    private ImageView mRightViewImage;
    private ImageView mRightViewImage2;

    private TextView mTitleView;

    private TextView mRightView;

    public HMActionBar(Context context) {
        super(context);
        init(context);
    }

    public HMActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HMActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.layout_actionbar, this, true);
        findViews();
    }

    private void findViews() {
        mLeftTextView = (TextView) mRootView.findViewById(R.id.actionbar_left_text);
        mLeftImageView = (ImageView) mRootView.findViewById(R.id.actionbar_left_image);
        mTitleView = (TextView) mRootView.findViewById(R.id.actionbar_title);
        mRightView = (TextView) mRootView.findViewById(R.id.actionbar_right);
        mRightViewImage = (ImageView) mRootView.findViewById(R.id.actionbar_right_image);
        mRightViewImage2 = (ImageView) mRootView.findViewById(R.id.actionbar_right_image2);
        setListeners();
    }
    public void setListeners(){
        mLeftImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)mContext).finish();
            }
        });
    }

    public void setLeftText(String text) {
        if (mLeftTextView != null) {
            mLeftTextView.setText(text);
            mLeftTextView.setVisibility(View.VISIBLE);
        }
    }

    public void setLeftImageResource(int resId) {
        if (mLeftImageView != null) {
            mLeftImageView.setImageResource(resId);
            mLeftImageView.setVisibility(View.VISIBLE);
        }
    }

    public void setRightImageResource(int resId) {
        if (mRightViewImage != null) {
            mRightViewImage.setImageResource(resId);
            mRightViewImage.setVisibility(View.VISIBLE);
        }
    }

    public void setRightImageResource2(int resId) {
        if (mRightViewImage2 != null) {
            mRightViewImage2.setImageResource(resId);
            mRightViewImage2.setVisibility(View.VISIBLE);
        }
    }

    public void setTitle(int resId) {
        if (mTitleView != null) {
            mTitleView.setText(resId);
            mTitleView.setVisibility(View.VISIBLE);
        }
    }

    public void setTitle(String title) {
        if (mTitleView != null) {
            mTitleView.setText(title);
            mTitleView.setVisibility(View.VISIBLE);
        }
    }

    public void setRightText(int resId) {
        if (mRightView != null) {
            mRightView.setText(resId);
            mRightView.setVisibility(View.VISIBLE);
        }
    }

    public void setRightText(String text) {
        if (mRightView != null) {
            mRightView.setText(text);
            mRightView.setVisibility(View.VISIBLE);
        }
    }

    public void setLeftTextOnClickListener(OnClickListener listener) {
        if (mLeftTextView != null) {
            this.mLeftTextView.setOnClickListener(listener);
        }
    }

    public void setLeftImageOnClickListener(OnClickListener listener) {
        if (mLeftImageView != null) {
            this.mLeftImageView.setOnClickListener(listener);
        }
    }

    public void setRightViewOnClickListener(OnClickListener listener) {
        if (mRightView != null) {
            this.mRightView.setOnClickListener(listener);
        }
    }

    public void setRightViewImageOnClickListener(OnClickListener listener) {
        if (mRightViewImage != null) {
            this.mRightViewImage.setOnClickListener(listener);
        }
    }

    public void setRightViewImageOnClickListener2(OnClickListener listener) {
        if (mRightViewImage2 != null) {
            this.mRightViewImage2.setOnClickListener(listener);
        }
    }

    public void setRightTextOnClickListener(OnClickListener listener) {
        if (mRightView != null) {
            this.mRightView.setOnClickListener(listener);
        }
    }

}
