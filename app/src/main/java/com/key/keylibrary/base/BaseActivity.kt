package com.key.keylibrary.base

import android.app.Activity
import android.app.UiAutomation
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.gyf.immersionbar.ImmersionBar
import com.key.keylibrary.R
import com.key.keylibrary.bean.BusMessage
import com.key.keylibrary.utils.UiUtils
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeCompat
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.onAdaptListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * created by key  on 2019/10/7
 */
abstract class BaseActivity : AppCompatActivity() {
    private var unbinder: Unbinder? = null
    protected var handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
       // setStateBarColor(R.color.white, true)
        setContentView(setLayoutId())
        unbinder = ButterKnife.bind(this)
        registerEventBus(this)
        initView()
        initAuto()
    }

    override fun onResume() {
        super.onResume()
        registerEventBus(this)
    }

    override fun onDestroy() {
        unregisterEventBus(this)
        unbinder!!.unbind()
        super.onDestroy()
    }


    /**
     * 初始化布局
     */
    abstract fun initView()


    private fun initAuto() {
        AutoSize.initCompatMultiProcess(this)
        AutoSizeConfig.getInstance()
                .setCustomFragment(true).onAdaptListener = object : onAdaptListener {
            override fun onAdaptBefore(target: Any, activity: Activity) {
                Log.e("Auto","Auto Before Height :"  +  UiUtils.getScreenHeight(activity))
                Log.e("Auto","Auto Before Width :"  +  UiUtils.getScreenWidth(activity))
            }

            override fun onAdaptAfter(target: Any, activity: Activity) {
                Log.e("Auto","Auto Before Height :"  +  UiUtils.getScreenHeight(activity))
                Log.e("Auto","Auto Before Width :"  +  UiUtils.getScreenWidth(activity))
            }
        }
    }




    override fun getResources(): Resources {
        AutoSizeCompat.autoConvertDensity(super.getResources(), 840f, false)
        return super.getResources()
    }


    override fun onPause() {
        if(isEventBusRegister(this)){
           unregisterEventBus(this)
        }
        super.onPause()
    }
    fun setStateBarColor(color: Int, isDark: Boolean) {
        ImmersionBar.with(this)
                .statusBarColor(color)
                .statusBarDarkFont(isDark)
                .fitsSystemWindows(true)
                .init()
    }

    private fun isEventBusRegister(subscribe: Any): Boolean {
        return EventBus.getDefault().isRegistered(subscribe)
    }

    private fun registerEventBus(subscribe: Any) {
        if (!isEventBusRegister(subscribe)) {
            EventBus.getDefault().register(subscribe)
        }
    }

    private fun unregisterEventBus(subscribe: Any) {
        if (isEventBusRegister(subscribe)) {
            EventBus.getDefault().unregister(subscribe)
        }
    }

    private fun removeEventBusMessage(message: Any) {
        EventBus.getDefault().removeStickyEvent(message)
    }

    @Subscribe(threadMode = ThreadMode.ASYNC, sticky = true)
    fun onMessageReceive(busMessage: BusMessage<Any>) {
        if (busMessage.target == javaClass.simpleName) {
            receiveMessage(busMessage)
            removeEventBusMessage(busMessage)
        }
    }

    abstract fun receiveMessage(busMessage: BusMessage<Any>)
    abstract fun setLayoutId(): Int

    fun sendBusMessage(busMessage: BusMessage<Any>){
        EventBus.getDefault().postSticky(busMessage)
    }
    protected open fun initSystemBar() {
        ImmersionBar.with(this).navigationBarColor(R.color.white)
                .autoDarkModeEnable(true)
                .statusBarDarkFont(true, 0.7f)
                .fitsSystemWindows(fitsSystemWindows()).init()
    }
    protected open fun fitsSystemWindows(): Boolean {
        return false
    }
}
