package com.key.keylibrary.base;

import com.key.keylibrary.R;
import com.key.keylibrary.widget.Toolbar;
import com.key.keylibrary.widget.recyclerview.KeyRecyclerView;

import me.jessyan.autosize.internal.CustomAdapt;

/**
 * created by key  on 2019/11/12
 */
public abstract class BaseListActivity extends BaseActivity implements CustomAdapt {
    protected Toolbar mToolbar;
    protected KeyRecyclerView mList;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_base_list);
        mToolbar = findViewById(R.id.toolbar);
        mList = findViewById(R.id.list);
    }

    @Override
    public void initView() {
        setStateBarColor(R.color.blue,false);
    }


}
