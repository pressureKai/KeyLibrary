package com.key.keylibrary.activity;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.key.keylibrary.R;
import com.key.keylibrary.base.BaseActivity;
import com.key.keylibrary.bean.BusMessage;
import com.key.keylibrary.widget.recyclerview.KeyRecyclerView;
import com.key.keylibrary.widget.recyclerview.base.BaseRecyclerViewAdapter;
import java.util.ArrayList;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * created by key  on 2019/10/14
 */
public class TestActivity extends BaseActivity implements CustomAdapt {
    private KeyRecyclerView mKeyRecyclerView;
    private  ArrayList<String> mList;

    @Override
    public void initView() {
        mKeyRecyclerView =  findViewById(R.id.list);
         mList = new ArrayList<>();
        for(int i=0; i < 100;i ++){
            mList.add( i + "");
        }
        TestAdapter testAdapter = new TestAdapter(this);
        testAdapter.setOnItemClickListener((view, position) -> Log.e("SHOW",mList.get(position) + ""));
        testAdapter.setOnDeleteListener((view,position) -> {
            Log.e("POSITION",position + "");
            mKeyRecyclerView.getList().remove(position);
            mKeyRecyclerView.getmRecyclerView().closeMenu();
            mList = (ArrayList<String>)mKeyRecyclerView.getList();
            mKeyRecyclerView.handleData(mList);
        });
        mKeyRecyclerView.getmRecyclerView().initList(3);
        mKeyRecyclerView.setAdapter(testAdapter);
        mKeyRecyclerView.handleData(mList);
        mKeyRecyclerView.setOnRefreshListener(refreshLayout -> {
                mKeyRecyclerView.getList().add(mKeyRecyclerView.getList().size() + "");
                mList = (ArrayList<String>)mKeyRecyclerView.getList();
                mKeyRecyclerView.handleData(mList);
                refreshLayout.finishRefresh();
        });

        mKeyRecyclerView.setOnLoadMoreListener(refreshLayout -> {
                mKeyRecyclerView.getList().add(mKeyRecyclerView.getList().size() + "");
                mList = (ArrayList<String>)mKeyRecyclerView.getList();
                mKeyRecyclerView.handleData(mList);
                refreshLayout.finishLoadMore();
        });


        mKeyRecyclerView.getmRecyclerView().setSlided(true);
        mKeyRecyclerView.getmRecyclerView().setOnSlideListener((x, dx, isLeft, isRight) -> {
            if(isLeft){
                Log.e("SHOW", "Left");
            }else{
                Log.e("SHOW", "Right");
            }
        });

    }

    @Override
    public void receiveMessage(BusMessage busMessage) {

    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 640;
    }




    public class TestAdapter extends BaseRecyclerViewAdapter<String> {
        public TestAdapter(Context context) {
            super(context);
        }
        @Override
        public int setLayoutId() {
            return R.layout.item_test_list;
        }

        @Override
        public void initView(View view, int position) {
            TextView textView = view.findViewById(R.id.text);
            if(getList().get(position) == null){
                textView.setText("null");
            }else{
                if(getList().get(position).isEmpty()){
                    textView.setText("empty");
                }else{
                    textView.setText(getList().get(position));
                }
            }


        }

        @Override
        public void setData(View view, int position) {
            super.setData(view, position);
            view.findViewById(R.id.delete).setOnClickListener(view1 -> {
                if(onDeleteListener != null){
                    onDeleteListener.onDelete(view1,position);
                }
            });
        }
    }
}
