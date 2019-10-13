package com.key.keylibrary.base;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeCompat;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.external.ExternalAdaptInfo;
import me.jessyan.autosize.onAdaptListener;

/**
 * created by key  on 2019/10/7
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
        initAuto();
    }

    /**
     * 添加主布局
     */
    public abstract void setContentView();

    /**
     * 初始化布局
     */
    public abstract void initView();


    public  void initAuto(){
        AutoSize.initCompatMultiProcess(this);
        AutoSizeConfig.getInstance()
                .setCustomFragment(true)
                .setOnAdaptListener(new onAdaptListener() {
                    @Override
                    public void onAdaptBefore(Object target, Activity activity) {
                        /**
                         *  适配之前
                         */
                    }
                    @Override
                    public void onAdaptAfter(Object target, Activity activity) {
                        /**
                         * 适配之后
                         */
                    }
                });
        customAdaptForExternal();
    }


    private void customAdaptForExternal() {
        AutoSizeConfig.getInstance().getExternalAdaptManager()
                .addExternalAdaptInfoOfActivity(BaseActivity.class, new ExternalAdaptInfo(true, 400));
    }


    @Override
    public Resources getResources() {
        AutoSizeCompat.autoConvertDensity(super.getResources(), 640, false);
        return super.getResources();
    }
}
