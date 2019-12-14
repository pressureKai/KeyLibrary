package com.key.keylibrary.widget.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.key.keylibrary.R;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * created by key  on 2019/2/3
 */
public class RecyclerView extends androidx.recyclerview.widget.RecyclerView {
    public static final int VERTICAL = StaggeredGridLayoutManager.VERTICAL;
    public static final int HORIZONTAL = StaggeredGridLayoutManager.HORIZONTAL;
    private int mWidthMeasureSpec;
    private int mHeightMeasureSpec;
    public  boolean shouldLocalInvalidate = false;
    private LayoutManager layoutManager;
    private Context mContext;
    private int[] lastPositions;
    private int itemCount;
    private boolean isLoadingMore;
    private int numColumn;
    private boolean slided = false;
    private boolean isCanScrollVertical = true;
    private boolean isCanScrollHorizontal = true;
    /**
     * 最小滑动距离 6/13
     * start;
     */
    private int mTouchSlop;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private float mLastX;
    private float mFirstX, mFirstY;
    private int mPosition;
    private Rect mTouchFrame;
    private static final int INVALID_POSITION = -1; // 触摸到的点不在子View范围内
    private static final int INVALID_CHILD_WIDTH = -1;  // 子ItemView不含两个子View
    private static final int SNAP_VELOCITY = 600;   // 最小滑动速度
    private ViewGroup mFlingView;   // 触碰的子View
    private int mMenuViewWidth;    // 菜单按钮宽度
    private boolean mIsSlide;   // 是否滑动子View
    /**
     * end;
     */
    private OnSlideListener onSlideListener;
    private Adapter mAdapter;
    private boolean mCanScroll = true;
    private int lastScrollX = 0;

    public RecyclerView(@NonNull Context context) {
        super(context);
        mContext = context;
        initThis();
    }

    public RecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initThis();
    }

    public RecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initThis();
    }


    private void initThis() {
        mTouchSlop = getScreenWidth(mContext) / 3;
        mScroller = new Scroller(mContext);
        initList();
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull androidx.recyclerview.widget.RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull androidx.recyclerview.widget.RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    /**
     *   获取最后一个可见位置
     * @return
     */
    public int getLastVisiblePosition() {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            if (lastPositions == null) {
                lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
            }
            staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
            return findMax(lastPositions);
        }
        return 0;
    }


    /**
     *   获取第一个可见的位置
     * @return
     */
    public int getFirstVisiblePosition() {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            if (lastPositions == null) {
                lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
            }
            staggeredGridLayoutManager.findFirstVisibleItemPositions(lastPositions);
            return findMin(lastPositions);
        }
        return 0;
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private int findMin(int[] lastPositions) {
        int min = lastPositions[0];
        for (int value : lastPositions) {
            if (value > min) {
                min = value;
            }
        }
        return min;
    }


    public void initList() {
        initList(1, VERTICAL);
    }

    public void initList(int numColumn) {
        initList(numColumn, VERTICAL);
    }

    public void initList(int numColumn, int orientation) {
        initList(numColumn, orientation, false);
    }

    public void initList(int numColumn, int orientation, boolean reverseLayout) {
        this.numColumn = numColumn;
        if (numColumn == 1) {
            layoutManager = new LinearLayoutManager(mContext, orientation, reverseLayout) {
                @Override
                public boolean canScrollVertically() {
                    return isCanScrollVertical && super.canScrollVertically();
                }

                @Override
                public boolean canScrollHorizontally() {
                    return isCanScrollHorizontal && super.canScrollHorizontally();
                }
            };
        } else {
            layoutManager = new GridLayoutManager(mContext, numColumn, orientation, reverseLayout) {
                @Override
                public boolean canScrollVertically() {
                    return isCanScrollVertical && super.canScrollVertically();
                }

                @Override
                public boolean canScrollHorizontally() {
                    return isCanScrollHorizontal && super.canScrollHorizontally();
                }
            };
        }

        setLayoutManager(layoutManager);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        mAdapter = adapter;
    }


    public int getNumColumn() {
        return numColumn;
    }



    public void setDivider(int color, int size) {
        addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .color(color).size(size).build());
    }

    public void setDefaultDivider() {
        addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .colorResId(R.color.divider).size(1).build());
    }

    public void setDividerDecoration(ItemDecoration dividerDecoration) {
        addItemDecoration(dividerDecoration);
    }


    /**
     *   RecyclerView滑动到底部
     * @param stackFromEnd
     */
    public void setStackFromEnd(boolean stackFromEnd) {
        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).setStackFromEnd(stackFromEnd);
        }
    }


    /**
     *  滑动到指定位置
     * @param position
     */
    public void goToPosition(int position) {
        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(position, 0);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager) layoutManager).scrollToPositionWithOffset(position, 0);
        }
    }

    /**
     *  竖直滑动
     * @param canScrollVertical
     */
    public void setCanScrollVertical(boolean canScrollVertical) {
        isCanScrollVertical = canScrollVertical;
    }


    /**
     *  水平滑动
     * @param canScrollHorizontal
     */
    public void setCanScrollHorizontal(boolean canScrollHorizontal) {
        isCanScrollHorizontal = canScrollHorizontal;
    }


    /**
     * 2019/6/3 添加侧滑功能
     * key
     */

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (slided) {
            int x = (int) e.getX();
            int y = (int) e.getY();
            obtainVelocity(e);
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!mScroller.isFinished()) {
                        mScroller.abortAnimation();
                    }
                    mFirstX = mLastX = x;
                    mFirstY = y;
                    mPosition = pointToPosition(x, y);
                    if (mPosition != INVALID_POSITION) {
                        View view = mFlingView;
                        mFlingView = (ViewGroup) getChildAt(mPosition -
                                ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition());
                        if (view != null && mFlingView != view && view.getScrollX() != 0) {
                            view.scrollTo(0, 0);
                        }

                        if (mFlingView.getChildCount() == 2) {
                            mMenuViewWidth = mFlingView.getChildAt(1).getWidth();
                        } else {
                            mMenuViewWidth = INVALID_CHILD_WIDTH;
                        }
                    }

                    break;
                case MotionEvent.ACTION_MOVE:
                    mVelocityTracker.computeCurrentVelocity(1000);
                    float xVelocity = mVelocityTracker.getXVelocity();
                    float yVelocity = mVelocityTracker.getYVelocity();
                    if (Math.abs(xVelocity) > SNAP_VELOCITY && Math.abs(xVelocity) > Math.abs(yVelocity)
                            || Math.abs(x - mFirstX) > mTouchSlop
                            && Math.abs(x - mFirstX) > Math.abs(y - mFirstY)) {

                        mIsSlide = true;
                        return true;

                    }
                    break;
                case MotionEvent.ACTION_UP:
                    releaseVelocity();
                    break;
            }
        }
        return super.onInterceptTouchEvent(e);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (slided) {
            if (mIsSlide && mPosition != INVALID_POSITION) {
                float x = e.getX();
                obtainVelocity(e);
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(mMenuViewWidth != INVALID_CHILD_WIDTH){

                            float dx = mLastX - x;
                            if(mFlingView.getScrollX()  + dx <= mMenuViewWidth
                                     && mFlingView.getScrollX() + dx >0){

                                mFlingView.scrollBy((int)dx,0);
                            }
                            mLastX = x;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if(mMenuViewWidth   != INVALID_CHILD_WIDTH){
                            int scrollX = mFlingView.getScrollX();
                            mVelocityTracker.computeCurrentVelocity(100);
                            if(mVelocityTracker.getXVelocity() < -SNAP_VELOCITY){
                                mScroller.startScroll(scrollX,0,mMenuViewWidth - scrollX,0,Math.abs(mMenuViewWidth - scrollX));
                                if(onSlideListener != null){
                                    onSlideListener.onSlide(scrollX,mPosition,true,false);
                                }
                            }else if(mVelocityTracker.getXVelocity() > SNAP_VELOCITY){
                                mScroller.startScroll(scrollX, 0, -scrollX, 0, Math.abs(scrollX));
                                if(onSlideListener != null){
                                    onSlideListener.onSlide(scrollX,mPosition,false,true);
                                }
                            }else if(scrollX >= mMenuViewWidth/3){
//                                if(lastScrollX- scrollX > 0){
                                    mScroller.startScroll(scrollX, 0, mMenuViewWidth - scrollX, 0, Math.abs(mMenuViewWidth - scrollX));
                                    if(onSlideListener != null){
                                        onSlideListener.onSlide(scrollX,mPosition,true,false);
                                    }
//                                }else{
//                                    mScroller.startScroll(scrollX, 0, -scrollX, 0, Math.abs(mMenuViewWidth - scrollX));
//                                    if(onSlideListener != null){
//                                        onSlideListener.onSlide(scrollX,-scrollX,false,true);
//                                    }
//                                }
//                                lastScrollX = scrollX;
                            }else{
                                //缓慢滑动回到最初开始滑动的位置
                                mScroller.startScroll(scrollX, 0, -scrollX, 0, Math.abs(scrollX));
                            }
                            invalidate();
                        }
                        mMenuViewWidth = INVALID_CHILD_WIDTH;
                        mIsSlide = false;
                        mPosition = INVALID_POSITION;
                        releaseVelocity();  // 这里之所以会调用，是因为如果前面拦截了，就不会执行ACTION_UP,需要在这里释放追踪
                        break;
                }
                return true;
            }else{
                closeMenu();
                releaseVelocity();
            }
        }
        return super.onTouchEvent(e);
    }

    /**
     * 2019/6/3
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        return widthPixels;
    }


    /**
     * 2019/6/3
     * 获取加速度
     *
     * @param event
     */
    private void obtainVelocity(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 2019/6/3
     */
    private void releaseVelocity() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }


    /**
     * 2019/6/3
     * 判断触摸时是处于哪一个子View
     * @param x
     * @param y
     * @return
     */
    public int pointToPosition(int x, int y) {
        int firstPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        Rect frame = mTouchFrame;
        if (frame == null) {
            mTouchFrame = new Rect();
            frame = mTouchFrame;
        }
        int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.VISIBLE) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    return firstPosition + i;
                }
            }
        }
        return INVALID_POSITION;
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mFlingView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 将显示子菜单的子view关闭
     * 这里本身是要自己来实现的，但是由于不定制item，因此不好监听器点击事件，因此需要调用者手动的关闭
     */
    public void closeMenu() {
        if (mFlingView != null && mFlingView.getScrollX() != 0) {
            mFlingView.scrollTo(0, 0);
        }
    }


    /**
     *   是否可以滑动
     * @param slided
     */
    public void setSlided(boolean slided) {
        this.slided = slided;
    }



    @Override
    public void requestLayout() {
        if (shouldLocalInvalidate) {
            localRequestLayout();
        } else {
            super.requestLayout();
        }
    }


    @SuppressLint("WrongCall")
    void localRequestLayout() {
        onMeasure(mWidthMeasureSpec, mHeightMeasureSpec);
        onLayout(true, 0, 0, 0, 0);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidthMeasureSpec = widthMeasureSpec;
        mHeightMeasureSpec = widthMeasureSpec;
    }




   public interface OnSlideListener{
        void onSlide(int x,int dx,boolean isLeft,boolean isRight);
   }


    public void setOnSlideListener(OnSlideListener onSlideListener) {
        this.onSlideListener = onSlideListener;
    }



}
