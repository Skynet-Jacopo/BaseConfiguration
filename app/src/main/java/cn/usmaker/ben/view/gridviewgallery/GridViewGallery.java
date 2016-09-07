package cn.usmaker.ben.view.gridviewgallery;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.usmaker.base.R;
import cn.usmaker.ben.view.fixed.FixedGridView;
import cn.usmaker.ben.view.gridviewgallery.adapter.GGGridViewAdapter;
import cn.usmaker.ben.view.gridviewgallery.adapter.GGViewPagerAdapter;



public class GridViewGallery extends LinearLayout {

    private Context            context;
    /**
     * 保存实体对象链表
     */
    private List<GridViewItem> gridViewItems;
    /**
     * 保存每个页面的GridView视图
     */
    private List<View>         gridViews;
    /**
     * ViewPager
     */
    private ViewPager          vp_viewpager;
    /**
     * Indicators容器
     */
    private LinearLayout       ll_dots;
    /**
     * 一个ImageView就是一个原点
     */
    private ImageView[]        dots;
    /**
     * ViewPager当前页
     */
    private int                currentIndex;
    /**
     * ViewPager页数
     */
    private int                viewPagerSize;
    /**
     * 默认一页12个item
     */
    private int itemCountPerPage = 10;

    public static final String TAG = "GridViewGallery";

    private Integer numColumns              = 5;// GridView的行数
    private Integer numRows                 = 2;// GridView的列数
    private Integer itemLayout              = 0;// GridView Item的布局
    private float   itemHeight              = 0;// GridView Item的布局
    private float   indicatorHeight         = 0;// Indicator的高度
    private int     indicatorBackgroudColor = 0;//Indicator的背景
    private int     contentBackgroudColor   = 0;//Indicator的背景
    private float   gvgContentPadding       = 0;// ViewPager两边的间距
    private float   itemTextSize            = 16;
    private float   indicatorSize           = 10; //原点指示器的大小

    public GridViewGallery(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridViewGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initAttrs(attrs, defStyle);
        //初始化每一个页的Item的个数
        itemCountPerPage = numColumns * numRows;
        this.gridViewItems = new ArrayList<GridViewItem>();
        initView();
        calculateDots();
        setViewPagerAdapter();
    }

    @SuppressWarnings("unchecked")
    public GridViewGallery(Context context, List<?> list) {
        super(context);
        Log.e(TAG, "进入list的构造方法");
        this.context = context;
        this.gridViewItems = (List<GridViewItem>) list;
        initView();
        calculateDots();
        setViewPagerAdapter();
    }

    /**
     * 初始化配置文件里边的属性
     *
     * @param attrs
     * @param defStyle
     */
    private void initAttrs(AttributeSet attrs, int defStyle) {
        TypedArray arr = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.GridViewGallery, defStyle, 0);
        numColumns = arr.getInteger(R.styleable.GridViewGallery_numColumns, 6);
        numRows = arr.getInteger(R.styleable.GridViewGallery_numRows, 2);
        itemLayout = arr.getResourceId(R.styleable.GridViewGallery_itemLayout,
                0);
        itemHeight = arr.getDimension(R.styleable.GridViewGallery_itemHeight, 0);
        indicatorHeight = arr.getDimension(R.styleable.GridViewGallery_indicatorHeight, 0);
        indicatorBackgroudColor = arr.getColor(R.styleable.GridViewGallery_indicatorBackgroudColor, getResources().getColor(android.R.color.transparent));
        contentBackgroudColor = arr.getColor(R.styleable.GridViewGallery_contentBackgroudColor, getResources().getColor(R.color.white));
        gvgContentPadding = arr.getDimension(R.styleable.GridViewGallery_gvgContentPadding, 0);
        indicatorSize = arr.getDimension(R.styleable.GridViewGallery_indicatorSize, 10);
        arr.recycle();
    }

    /**
     * 初始化viewPager和ll_dot。<br>
     */
    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_gridview_gallery, null);
        vp_viewpager = (ViewPager) view.findViewById(R.id.vp_viewpager);
        vp_viewpager.setBackgroundColor(contentBackgroudColor);
        //这个设置高度的方法，好使。
        vp_viewpager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int) (itemHeight * numRows) + 1));
        ll_dots = (LinearLayout) view.findViewById(R.id.ll_dots);
        ll_dots.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int) indicatorHeight));
        ll_dots.setBackgroundColor(indicatorBackgroudColor);
        addView(view);
    }

    /**
     * 计算底部原点的个数，并将底部原点与对应的ViewPager中的一个Pager替换。<br/>
     * 同时计算viewPagerSize
     */
    private void calculateDots() {
        int extra = 1;
        if (gridViewItems.size() % itemCountPerPage == 0) {
            extra = 0;
        }
        viewPagerSize = gridViewItems.size() / itemCountPerPage + extra;

        if (viewPagerSize > 0) {
            ll_dots.removeAllViews();
            if (viewPagerSize == 1) {
                ll_dots.setVisibility(View.GONE); // 只有1页，不显示indicator栏。
            } else if (viewPagerSize > 1) {
                ll_dots.setVisibility(View.VISIBLE);
                for (int j = 0; j < viewPagerSize; j++) {
                    ImageView image = new ImageView(context);
                    LayoutParams params = new LayoutParams(
                            (int)indicatorSize, (int)indicatorSize); // dot的宽高
                    params.setMargins(3, 0, 3, 0);
                    image.setBackgroundResource(R.drawable.haide);
                    ll_dots.addView(image, params);
                }
            }
        }

        // 大于一页的话，初始化ViewPager。
        if (viewPagerSize > 1) {
            dots = new ImageView[viewPagerSize];
            for (int i = 0; i < viewPagerSize; i++) {
                // 从布局中填充dots数组
                dots[i] = (ImageView) ll_dots.getChildAt(i);
                // dots[i].setEnabled(true);
                // dots[i].setTag(i);
            }
            currentIndex = 0; // 当前页
            // dots[currentIndex].setEnabled(false);
            dots[currentIndex].setBackgroundResource(R.drawable.red);
            vp_viewpager
                    .setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                        @Override
                        public void onPageSelected(int arg0) {
                            setCurrentIndicator(arg0);
                        }

                        @Override
                        public void onPageScrolled(int arg0, float arg1,
                                                   int arg2) {
                        }

                        @Override
                        public void onPageScrollStateChanged(int arg0) {
                        }
                    });
        }
    }


    private void setViewPagerAdapter() {
        gridViews = new ArrayList<View>();
        for (int i = 0; i < viewPagerSize; i++) {
            gridViews.add(initViewPagerItem(i));
        }
        vp_viewpager.setAdapter(new GGViewPagerAdapter(gridViews));
    }

    /**
     * 初始化Item的时候用。
     * @param items
     */
    public void addGridItems(List<GridViewItem> items) {
        gridViewItems.clear();
        gridViewItems.addAll(items);
        calculateDots();
        setViewPagerAdapter();
    }

    public void addGridItem(GridViewItem item) {
        gridViewItems.add(item);
        calculateDots();
        setViewPagerAdapter();
    }


    /**
     * 设置当前底部小圆点
     *
     * @param positon
     */
    private void setCurrentIndicator(int positon) {
        if (positon < 0 || positon > viewPagerSize - 1
                || currentIndex == positon) {
            return;
        }
        for (int i = 0; i < dots.length; i++) {
            dots[i].setBackgroundResource(R.drawable.haide);
        }
        // dots[positon].setEnabled(false);
        // dots[currentIndex].setEnabled(true);
        dots[positon].setBackgroundResource(R.drawable.red);
        currentIndex = positon;
    }

    /**
     * 初始化ViewPager中每个Pager。<br>
     * 这里主要就是初始化每个Pager的GridView。<br>
     *
     * @param index
     * @return
     */
    private GridView initViewPagerItem(int index) {

        FixedGridView fixedGridView = new FixedGridView(getContext());
        fixedGridView.setNumColumns(numColumns);
        fixedGridView.setGravity(Gravity.CENTER);
        fixedGridView.setSelector(new ColorDrawable());
        fixedGridView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        LayoutParams layoutParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        fixedGridView.setLayoutParams(layoutParams);

        // 每个页面GridView的Adpter
        GGGridViewAdapter adapter = new GGGridViewAdapter(context,
                gridViewItems, index, itemCountPerPage);

        fixedGridView.setAdapter(adapter);
        fixedGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (null != gridViewItems.get(
                        position + currentIndex * itemCountPerPage)
                        .getOnClickListener()) {
                    // 调用GridView的每个Item的click事件。
                    gridViewItems
                            .get(position + currentIndex * itemCountPerPage)
                            .getOnClickListener()
                            .ongvItemClickListener(
                                    position + currentIndex * itemCountPerPage);
                }
            }
        });
        return fixedGridView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (itemHeight == 0) {//如果没有设置item的高度的话，高度按照每个Item的高度x1.25。
            heightSize = (int) ((widthSize / numColumns) * 1.25 * 2 + indicatorHeight);
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    // 宽
    public int getViewWidth(View view) {
        view.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        return view.getMeasuredWidth();
    }

    // 高
    public int getViewHeight(View view) {
        view.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        return view.getMeasuredHeight();
    }
}
