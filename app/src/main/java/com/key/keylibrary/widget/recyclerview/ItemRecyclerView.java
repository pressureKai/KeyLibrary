package com.key.keylibrary.widget.recyclerview;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * created by key  on 2019/2/12
 */
public class ItemRecyclerView extends  RecyclerView.ViewHolder {
    private View itemView;

    public View getItemView() {
        return itemView;
    }

    public ItemRecyclerView(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
    }
}
