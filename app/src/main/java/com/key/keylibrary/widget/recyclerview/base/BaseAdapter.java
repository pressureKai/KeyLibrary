package com.key.keylibrary.widget.recyclerview.base;

import android.content.Context;
import android.view.ViewGroup;
import com.key.keylibrary.widget.recyclerview.AdapterRecyclerView;
import com.key.keylibrary.widget.recyclerview.ItemRecyclerView;
import java.util.List;


/**
 * Created by lwxkey on 16/8/19.
 */
public abstract class BaseAdapter<T> extends AdapterRecyclerView {

    public List<T> mList;
    protected Context mContext;


    public void setLists(List<T> list) {
        this.mList = list;
    }

    public void setList(List<T> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public List<T> getList() {
        return mList;
    }

    public BaseAdapter(Context context) {
        super(context);
        mContext = context;
    }


    @Override
    public abstract ItemRecyclerView createView(ViewGroup parent, int viewType);

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

}
