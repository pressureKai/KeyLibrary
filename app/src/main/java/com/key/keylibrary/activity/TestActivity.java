package com.key.keylibrary.activity;

import android.content.Intent;

import com.key.keylibrary.R;
import com.key.keylibrary.base.BaseActivity;

import me.jessyan.autosize.internal.CustomAdapt;

/**
 * created by key  on 2019/10/14
 */
public class TestActivity extends BaseActivity implements CustomAdapt {
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_test);
    }

    @Override
    public void initView() {
        findViewById(R.id.jump).setOnClickListener(view -> {

        });
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 640;
    }
}
