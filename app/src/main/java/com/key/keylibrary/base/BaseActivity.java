package com.key.keylibrary.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * created by key  on 2019/10/7
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
    }

    /**
     * 添加主布局
     */
    public abstract void setContentView();

    /**
     * 初始化布局
     */
    public abstract void initView();
}
