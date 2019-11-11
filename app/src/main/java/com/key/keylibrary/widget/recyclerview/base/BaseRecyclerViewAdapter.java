package com.key.keylibrary.widget.recyclerview.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.key.keylibrary.utils.UiUtils;
import com.key.keylibrary.widget.recyclerview.ItemRecyclerView;

/**
 * created by key  on 2019/11/11
 */
public abstract class BaseRecyclerViewAdapter<T> extends BaseAdapter<T> {
    private OnItemClickListener onItemClickListener;
    protected OnDeleteListener onDeleteListener;
    protected Context context;
    protected int mLayoutId;
    public BaseRecyclerViewAdapter(Context context) {
        super(context);
        this.context = context;
        mLayoutId = setLayoutId();
    }

    @Override
    public void setData(View view, int position) {
        view.setOnClickListener(view1 -> {
            if(onItemClickListener != null){
                onItemClickListener.onItemClick(view1,position);
            }
        });
        initView(view,position);
    }

    @Override
    public ItemRecyclerView createView(ViewGroup parent, int viewType) {
        return new ItemRecyclerView(UiUtils.inflate(context,mLayoutId));
    }
    public interface OnItemClickListener{
       void onItemClick(View view, int position);
    }
    public interface OnDeleteListener{
        void onDelete(View view,int position);
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public abstract int setLayoutId();
    public abstract void initView(View view, int position);
}
