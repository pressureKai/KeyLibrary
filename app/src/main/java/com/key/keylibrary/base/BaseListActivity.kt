package com.key.keylibrary.base

import com.key.keylibrary.R
import com.key.keylibrary.widget.Toolbar
import com.key.keylibrary.widget.recyclerview.KeyRecyclerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import me.jessyan.autosize.internal.CustomAdapt

/**
 * created by key  on 2019/11/12
 */
abstract class BaseListActivity<T> : BaseActivity(), CustomAdapt {
    var mToolbar: Toolbar? = null
    var mListView: KeyRecyclerView<T>? = null
    var mRefreshOut :SmartRefreshLayout ?= null
    override fun setLayoutId(): Int {
        return R.layout.activity_base_list
    }

    override fun initView() {
        setStateBarColor(R.color.blue, false)
        mToolbar = findViewById(R.id.toolbar)
        mListView = findViewById(R.id.list)
        mRefreshOut = findViewById(R.id.refresh_out)
        mRefreshOut!!.setEnableRefresh(false)
        mRefreshOut!!.setEnableLoadMore(false)
    }



    fun refreshEnable(enable : Boolean){
        mRefreshOut!!.setEnableRefresh(enable)
    }
    fun loadMoreEnable(enable :Boolean){
        mRefreshOut!!.setEnableLoadMore(enable)
    }
}