package com.key.keylibrary.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AlertDialog;
import com.key.keylibrary.R;

/**
 * created by key  on 2019/10/23
 */
public class CustomAlertDialog {
    private static final int CENTER = 0;
    private static final int TOP = 1;
    private static final int BOTTOM = 2;
    private AlertDialog  mDialog;
    private Context context;


    public AlertDialog getAlertDialog() {
        return mDialog;
    }


    public CustomAlertDialog(){

    }

    public CustomAlertDialog(Context context, View view, int position,
                             boolean fullScreen,boolean isFullHorizontal, int width,
                             int height, int verticalPadding,
                             int horizontalPadding, boolean isFocus,
                             boolean openAnimation, boolean isOutSideCancel, int theme){
        if (isFullHorizontal) {
            width = WindowManager.LayoutParams.MATCH_PARENT;
        }

        if (fullScreen) {
            height =  WindowManager.LayoutParams.MATCH_PARENT;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        mDialog = builder.create();
        mDialog.setCanceledOnTouchOutside(isOutSideCancel);
        if(mDialog.getWindow() != null){
            mDialog.getWindow().setLayout(width, height);
        }
        mDialog.show();
        mDialog.setContentView(view);
        Window window = mDialog.getWindow();
        switch (position) {
            case 0:
                window.setGravity(Gravity.CENTER);
                break;
            case 1:
                window.setGravity(Gravity.TOP);
                break;
            case 2:
                window.setGravity(Gravity.BOTTOM);
                break;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        window.getDecorView().setPadding(dip2px(context, (float) horizontalPadding), dip2px(context, (float) verticalPadding),
                dip2px(context, (float) horizontalPadding),dip2px(context, (float) verticalPadding));
        if (openAnimation) {
            window.setWindowAnimations(R.style.dialog_style);
        }
        WindowManager.LayoutParams lp = window.getAttributes();
        if (isFocus) {
            lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        }
        lp.width = width;
        lp.height = height;
        window.setAttributes(lp);
    }


    public  class Builder{
        private Context context;
        private View view;
        private int position = CustomAlertDialog.CENTER;
        private boolean fullScreen = false;
        private boolean fullHorizontal = false;
        private int width = WindowManager.LayoutParams.MATCH_PARENT;
        private int height = WindowManager.LayoutParams.WRAP_CONTENT;
        private int verticalPadding = 0;
        private int horizontalPadding = 13;
        private boolean isFocus = false;
        private boolean openAnimation = true;
        private boolean isOutSideCancel = true;
        private int theme = R.style.NormalDialogTheme;


       public Builder(Context context,View view){
           this.context = context;
           this.view = view;
       }

        public Builder setPosition(int position) {
            this.position = position;
            return this;
        }


        public Builder setFullScreen(boolean fullScreen) {
            this.fullScreen = fullScreen;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }


        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }


        public Builder setVerticalPadding(int verticalPadding) {
            this.verticalPadding = verticalPadding;
            return this;
        }

        public Builder setHorizontalPadding(int horizontalPadding) {
            this.horizontalPadding = horizontalPadding;
            return this;
        }

        public Builder setFocus(boolean focus) {
            isFocus = focus;
            return this;
        }

        public Builder setOpenAnimation(boolean openAnimation) {
            this.openAnimation = openAnimation;
            return this;
        }

        public Builder setOutSideCancel(boolean outSideCancel) {
            isOutSideCancel = outSideCancel;
            return this;
        }

        public Builder setTheme(int theme) {
            this.theme = theme;
            return this;
        }
        public AlertDialog build(){
          return   new CustomAlertDialog(context,view,position,fullScreen,fullHorizontal,
                   width,height,verticalPadding,horizontalPadding,isFocus,
                   openAnimation,isOutSideCancel,theme).getAlertDialog();
        }
    }


    public int dip2px(Context context,float dip) {
        if(context != null){
            float density = context.getResources().getDisplayMetrics().density;
            return (int) (dip * density + 0.5f);
        }else{
            return  0;
        }
    }
    /**
     *   1.EditText 使用观察者模式or代理模式 or 同时使用两种模式
     *   2.模式需求分析
     *     1). 当用户点击提交（外部事件）和切换状态（内部事件）时，可以利用EditText内部的方法自动判断当前的字段是否符合规制
     *     2). 密码模式
     *     3). 外部事件与内部事件可能同时发生
     *     4). 代理模式，被代理对象与代理对象继承同一个接口，被代理对象持有代理对象的引用
     *     5). 观察者模式使用RxJava实现
     *   3.自定义EditText实现
     *     1). 布局，自定义类类，以及状态码的编写
     *     2). 自定义内部接口和内部代理对象（或可通过此代理对象进行逻辑上的下一步操作）
     *     3). 通过RxJava对editText状态改变进行监听
     */
}
