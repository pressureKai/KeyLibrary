package com.key.keylibrary.activity

import com.key.keylibrary.AAInfographicsLib.AAChartConfiger.AAChartView
import com.key.keylibrary.R
import com.key.keylibrary.base.BaseActivity
import me.jessyan.autosize.internal.CustomAdapt

class IndexActivity : BaseActivity(), CustomAdapt {
    private var aaChartView: AAChartView? = null
    override fun setContentView() {
        setContentView(R.layout.activity_main)
    }

    override fun initView() {
        aaChartView = findViewById(R.id.chart_view)
    }

    override fun isBaseOnWidth(): Boolean {
        return false
    }

    override fun getSizeInDp(): Float {
        return 640f
    }
}
