package com.key.keylibrary.base

import androidx.constraintlayout.widget.ConstraintLayout
import com.key.keylibrary.R
import com.key.keylibrary.utils.UiUtils
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
    override fun setLayoutId(): Int {
        return R.layout.activity_base_list
    }

    override fun initView() {
        setStateBarColor(R.color.blue, false)
        mToolbar = findViewById(R.id.toolbar)
        mListView = findViewById(R.id.list)
        val layoutParams = mListView!!.layoutParams
        layoutParams.height = UiUtils.getScreenHeight(this) - (UiUtils.measureView(mToolbar)[1] + UiUtils.getStateBar(this))
        mListView!!.layoutParams = layoutParams
        val i = UiUtils.measureView(mToolbar)[1] + UiUtils.getStateBar(this)
        mListView!!.setPadding(0,0,0, i)
    }



}