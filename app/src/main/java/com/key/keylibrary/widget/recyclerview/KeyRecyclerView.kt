package com.key.keylibrary.widget.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.OvershootInterpolator
import com.key.keylibrary.widget.recyclerview.base.BaseAdapter
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import java.util.*

/**
 * Created by key on 2019/7/22.
 */
class KeyRecyclerView<K> : BaseRecyclerView {
    private var mList: MutableList<K>? = null
    private var mAdapterBase: BaseAdapter<K>? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    fun setAdapter(adapter: BaseAdapter<K>?) {
        super.setAdapter(adapter)
        mAdapterBase = adapter
        if (mAdapterBase!!.list == null) {
            mList = ArrayList()
            mAdapterBase!!.setList(mList)
        } else {
            mList = mAdapterBase!!.list
        }
    }

    fun handleData(list: MutableList<K>?) {
        var list = list
        if (list == null) list = Collections.emptyList()
        mList = list
        mAdapterBase!!.setLists(mList)
        if (mList!!.isNotEmpty()) {
            notifyDataSetChanged()
        }
    }

    override fun removeAllViews() {
        super.removeAllViews()
        mRecyclerView.removeAllViews()
    }


    val list: List<K>
        get() = if (mList == null) ArrayList() else mList!!


    /**
     * 设置RecyclerView动画
     */
    override fun setAnimation() {
        if (openAnimation) {
            val animator = SlideInDownAnimator(OvershootInterpolator(1f))
            animator.removeDuration = 500
            animator.addDuration = 500
            mRecyclerView.itemAnimator = animator
        }
    }

    override fun initList(num: Int) {
        mRecyclerView.initList(num)
    }
}