package cn.usmaker.ben.view.imageindicator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView.ScaleType;

import com.android.http.WebImageView;

import java.util.List;

import cn.usmaker.base.R;
import cn.usmaker.ben.base.UMApplication;


/**
 * Network ImageIndicatorView, by urls
 * 
 * @author steven-pan
 * 
 */
public class NetworkImageIndicatorView extends ImageIndicatorView {

	public NetworkImageIndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NetworkImageIndicatorView(Context context) {
		super(context);
	}

	/**
	 * 设置显示图片URL列表
	 * 
	 * @param urlList
	 *            URL列表
	 */
	public void setupLayoutByImageUrl(final List<String> urlList) {
		if (urlList == null)
			throw new NullPointerException();

		final int len = urlList.size();
		if (len > 0) {
			for (int index = 0; index < len; index++) {
				final WebImageView pageItem = new WebImageView(getContext());
				pageItem.setScaleType(ScaleType.CENTER_CROP);
				pageItem.setDefaultImageResId(R.drawable.tuijian);
				pageItem.setImageUrl(urlList.get(index),
						UMApplication.getImageLoader());
				addViewItem(pageItem);
			}
		}
	}

}
