package com.key.keylibrary.base

import android.view.View
import com.key.keylibrary.R
import com.key.keylibrary.utils.UiUtils
import com.key.keylibrary.widget.Toolbar
import com.key.keylibrary.widget.recyclerview.KeyRecyclerView
import kotlinx.android.synthetic.main.activity_base_list.*
import me.jessyan.autosize.internal.CustomAdapt

/**
 * created by key  on 2019/11/12
 */
abstract class BaseListActivity<T> : BaseActivity(), CustomAdapt {
    var mToolbar: Toolbar? = null
    var mListView: KeyRecyclerView<T>? = null
    var onErrorContentClickListener :OnErrorContentClickListener ?= null
    override fun setLayoutId(): Int {
        return R.layout.activity_base_list
    }

    override fun initView() {
        setStateBarColor(R.color.blue, false)
        mToolbar = findViewById(R.id.toolbar)
        mListView = findViewById(R.id.list)
        mToolbar!!.setLeftTitleShow(true)
        mToolbar!!.setLeftTitleText("标题")
        val layoutParams = mListView!!.layoutParams
        layoutParams.height = UiUtils.getScreenHeight(this) -
                (UiUtils.measureView(mToolbar)[1] + UiUtils.getStateBar(this))
        mListView!!.layoutParams = layoutParams
        if(error_content != null){
            error_content.setOnClickListener {
                if(onErrorContentClickListener != null){
                    onErrorContentClickListener!!.onErrorContentClick()
                }
            }
        }
    }



    public fun setListShow(isShow: Boolean){
        if(isShow){
            if(error_content != null){
                error_content.visibility = View.VISIBLE
            }
            if(mListView != null){
                mListView!!.visibility = View.GONE
            }
        }else{
            if(error_content != null){
                error_content.visibility = View.GONE
            }
            if(mListView != null){
                mListView!!.visibility = View.VISIBLE
            }
        }
    }


    public interface OnErrorContentClickListener{
        fun onErrorContentClick()
    }




}