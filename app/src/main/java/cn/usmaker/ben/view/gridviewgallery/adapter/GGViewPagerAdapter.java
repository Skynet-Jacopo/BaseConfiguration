package cn.usmaker.ben.view.gridviewgallery.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * ViewPager的PagerAdapter。<br/>
 *
 * @author chenzhenyang
 *
 */
public class GGViewPagerAdapter extends PagerAdapter {

	private List<View> lists;

	public GGViewPagerAdapter(List<View> data) {
		lists = data;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {

		// return false;
		return arg0 == (arg1);
	}

	public Object instantiateItem(View view, int positiion) {
		try {
			// 解决View只能滑动两屏的方法
			ViewGroup parent = (ViewGroup) lists.get(positiion).getParent();
			if (parent != null) {
				parent.removeAllViews();
			}
			// container.addView(v);
			((ViewPager) view).addView(lists.get(positiion), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lists.get(positiion);
	}

	@Override
	public void destroyItem(View view, int positiion, Object arg2) {
		try {
			((ViewPager) view).removeView(lists.get(positiion));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
