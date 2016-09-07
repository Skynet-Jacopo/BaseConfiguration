package cn.usmaker.ben.view.gridviewgallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.usmaker.base.R;
import cn.usmaker.ben.view.gridviewgallery.GridViewItem;
import lib.lhh.fiv.library.FrescoZoomImageView;

/**
 * GridView的Adapter。<br>
 *
 * @author chenzhenyang
 *
 */
public  class GGGridViewAdapter extends BaseAdapter {

	private List<GridViewItem> mItems;
	private Context            context;
	/** ViewPager页码 */
	private int                index;
	/** 根据屏幕大小计算得到的每页item个数 */
	private int                pageItemCount;
	/** 传进来的List的总长度 */
	private int                totalSize;

	/** 当前页item的实际个数 */
	// private int itemRealNum;
	@SuppressWarnings("unchecked")
	public GGGridViewAdapter(Context context, List<?> list) {
		this.context = context;
		this.mItems = (List<GridViewItem>) list;
	}

	public GGGridViewAdapter(Context context, List<?> list, int index,
							 int pageItemCount) {
		this.context = context;
		this.index = index;
		this.pageItemCount = pageItemCount;
		mItems = new ArrayList<GridViewItem>();
		totalSize = list.size();
		// itemRealNum=list.size()-index*pageItemCount;
		// 当前页的item对应的实体在List<?>中的其实下标
		int list_index = index * pageItemCount;
		for (int i = list_index; i < list.size(); i++) {
			mItems.add((GridViewItem) list.get(i));
		}

	}

	@Override
	public int getCount() {
		int size = totalSize / pageItemCount;
		if (index == size)
			return totalSize - pageItemCount * index;
		else
			return pageItemCount;
		// return itemRealNum;
	}

	@Override
	public Object getItem(int position) {
		// return null;
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder iv;
		if (convertView == null) {
			iv = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.gridview_gallery_item_demo, null);
			iv.iv_icon = (FrescoZoomImageView) convertView
					.findViewById(R.id.img_icon);
			iv.tv_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			convertView.setTag(iv);
		} else {
			iv = (ViewHolder) convertView.getTag();
		}
		iv.updateViews(position, null);
		return convertView;
	}

	class ViewHolder {
		FrescoZoomImageView iv_icon;
		TextView            tv_name;

		protected void updateViews(final int position, Object inst) {
			// 不管用
			// iv_icon.setBackgroundResource(list_info.get(position).getIconID());
			final GridViewItem item = mItems.get(position);
			if(item.getIconUrl()!=null){
				//用图片加载器加载这个URL的图片。
				iv_icon.loadView(item.getIconUrl(),R.drawable.home_page);
			}else{
				iv_icon.setImageResource(item.getIconID());
			}
			if(item.getOnClickListener()!=null){
				iv_icon.setOnDraweeClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
					//	ToastUtils.showToast(context,item.getName());

					}
				});
			}
			tv_name.setText(item.getName());
		}
	}

}
