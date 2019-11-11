package com.key.keylibrary.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * created by key  on 2019/11/11
 */
public class UiUtils {


    /**
     * 加载布局文件
     *
     * @param id
     * @return
     */
    public static View inflate(Context context,int id) {
        return View.inflate(context, id, null);
    }



    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        return widthPixels;
    }
}
