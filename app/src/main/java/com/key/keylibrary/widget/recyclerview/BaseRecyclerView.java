package com.key.keylibrary.widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.key.keylibrary.R;
import com.key.keylibrary.widget.recyclerview.base.BaseFrameView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;


/**
 * created by key  on 2019/2/15
 */
public class BaseRecyclerView extends BaseFrameView {
    protected SmartRefreshLayout mSwipe;
    protected RecyclerView mRecyclerView;
    protected FrameLayout mError;
    protected FrameLayout mLoading;
    protected FrameLayout mTop;
    protected Adapter mAdapter;
    protected OnRefreshListener onRefreshListener;
    protected OnLoadMoreListener onLoadMoreListener;
    protected boolean openAnimation = true;


    public BaseRecyclerView(Context context) {
        super(context);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void initBase() {
        super.initBase();
        mView = View.inflate(getContext(), R.layout.recycler_list_base, null);
        init();
    }

    protected void init() {
        mSwipe = mView.findViewById(R.id.base_list_body);
        mRecyclerView = mView.findViewById(R.id.base_list_recycler);
        mError = mView.findViewById(R.id.base_list_error);
        mLoading = mView.findViewById(R.id.base_list_loading);
        mTop =  mView.findViewById(R.id.base_list_top);
        mSwipe.setOnRefreshListener(refreshLayout -> {
              if(onRefreshListener != null){
                  onRefreshListener.onRefresh(refreshLayout);
              }
        });
        mSwipe.setOnLoadMoreListener(refreshLayout -> {
            if(onLoadMoreListener != null){
                onLoadMoreListener.onLoadMore(refreshLayout);
            }
        });
        mRecyclerView.initList();
    }
    public void initList(int numColumn){
        mRecyclerView.initList(numColumn);
    }

    public void initList(int numColumn,int orientation){
        mRecyclerView.initList(numColumn,orientation);
    }


    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     * 获取 RecyclerView
     * @return
     */
    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    /**
     *   设置分割条的大小和颜色
     * @param color
     * @param size
     */
    public void setDivider(int color, int size) {
        mRecyclerView.setDivider(color, size);
    }


    /**
     *  设置默认分隔条
     */
    public void setDefaultDivider() {
        mRecyclerView.setDefaultDivider();
    }


    /**
     *  设置分隔条样式
     * @param itemDecoration
     */
    public void setDividerDecration(androidx.recyclerview.widget.RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.setDividerDecoration(itemDecoration);
    }


    /**
     *   滑动到第一个位置
     * @param position
     */
    public void goToPosition(int position) {
        mRecyclerView.goToPosition(position);
    }


    public void setRefreshEnable(boolean enable) {
        mSwipe.setEnableRefresh(enable);
    }


    public void setLoadMoreEnable(boolean enable){
        mSwipe.setEnableLoadMore(enable);
    }
    /**
     *  从下到上添加
     * @param stackFromEnd
     */
    public void setStackFromEnd(boolean stackFromEnd) {
        mRecyclerView.setStackFromEnd(stackFromEnd);
    }


    /**
     * 通知适配器数据发生改变
     */
    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }


    /**
     *  设置RecyclerView 参数
     * @param context
     */
    public void setRecyclerViewParams(Context context){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context) {
            @Override
            public androidx.recyclerview.widget.RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new androidx.recyclerview.widget.RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(androidx.recyclerview.widget.RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
    }



    /**
     *   设置RecyclerView动画
     */
    public void setAnimation(){
        if(openAnimation){
            SlideInDownAnimator animator = new SlideInDownAnimator(new OvershootInterpolator(1f));
            animator.setRemoveDuration(1000);
            animator.setAddDuration(1000);
            mRecyclerView.setItemAnimator(animator);
        }
    }



    public interface OnRefreshListener {
        void onRefresh(RefreshLayout refreshLayout);
    }
    public interface OnLoadMoreListener {
        void onLoadMore(RefreshLayout refreshLayout);
    }


    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
}
