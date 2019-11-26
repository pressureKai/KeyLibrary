package com.key.keylibrary.base

import com.key.keylibrary.R
import com.key.keylibrary.widget.Toolbar
import com.key.keylibrary.widget.recyclerview.KeyRecyclerView
import me.jessyan.autosize.internal.CustomAdapt

/**
 * created by key  on 2019/11/12
 */
abstract class BaseListActivity<T> : BaseActivity(), CustomAdapt {
    protected var mToolbar: Toolbar? = null
    protected var mList: KeyRecyclerView<T>? = null
    override fun setLayoutId(): Int {
        return R.layout.activity_base_list
    }

    override fun initView() {
        setStateBarColor(R.color.blue, false)
        mToolbar = findViewById(R.id.toolbar)
        mList = findViewById(R.id.list)
    }
}