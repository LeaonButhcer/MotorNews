package com.asus.motornews.UI;

import java.util.Currency;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class ASUSSlidingMenu extends ViewGroup {

	private static final String TAG = "ASUSSlidingMenu";
	private float previousX;	//在移动过程中每次对比得到dx时代前一个点的x坐标
	private static final int ROTATE = 2;	//屏幕自动移动时的速率
	private Scroller mScroller;		//滚动器
	
	public static final int MAIN_SCREEN = 0;			//代表主屏幕
	public static final int LEFT_MENU_SCREEN = -1;		//代表左侧列表
	public static final int RIGHT_SETTING_SCREEN = 1;	//代表右侧列表
	private int currentScreen = MAIN_SCREEN;
	
	private int LEFT_MEASURED_WIDTH;		//屏幕最左端
	private int RIGHT_MEASURED_WIDTH;	//屏幕最右端
	private int touchSlop;

	public ASUSSlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		measureView(widthMeasureSpec, heightMeasureSpec);
	}
	
	/**
	 * 测量view树中包含的子view的大小
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 */
	private void measureView(int widthMeasureSpec, int heightMeasureSpec ){
		//测量左侧menuView的大小
		View menuView = getChildAt(0);
//		menuView.measure(menuView.getLayoutParams().width, heightMeasureSpec);
		//测量中间main
		View mainView = getChildAt(1);
//		mainView.measure(widthMeasureSpec, heightMeasureSpec);
		//测量右边的settingView
		View settingView = getChildAt(2);
//		settingView.measure(settingView.getLayoutParams().width, heightMeasureSpec);
		measureChildren(widthMeasureSpec, heightMeasureSpec);
	}


	/**
	 * 测量完之后进行布局，其中t、b、l、r表示上下左右的四个角度坐标，这样就能确定一个view的布局大小
	 * 
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		View menuView = getChildAt(0);
		View mainView = getChildAt(1);
		View settingView = getChildAt(2);
		
		LEFT_MEASURED_WIDTH = menuView.getMeasuredWidth();
		RIGHT_MEASURED_WIDTH = settingView.getMeasuredWidth();
		
		//布局左侧menuView，因为它在显示屏的左侧，所以左边起点用负数，长度为iew控件的长度，右边正好是屏幕的起点所以是0		
		menuView.layout(-LEFT_MEASURED_WIDTH, 0, 0, b);
		//布局mainView		
		mainView.layout(l, t, r, b);
		//布局settingView,它显示在显示屏的右边，		
		settingView.layout(r, 0, r+RIGHT_MEASURED_WIDTH, b);
	}



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			previousX = event.getX();	//初始化startX
			break;
		case MotionEvent.ACTION_MOVE:
			moveAction(event);
			break;
		case MotionEvent.ACTION_UP:
			upAction(event);
			break;
		default:
			//出现未知Error,报运行时异常
//			throw new RuntimeException();
		}
		return true;
	}

	/**
	 * 移动事件，使屏幕跟着手指的滑动而移动
	 * @param event
	 */
	private void moveAction(MotionEvent event) {
		//记录移动时，当前的x位置
		float moveX = event.getX();
		//计算出移动的距离，当手指向右移动时，为负数；当手指向左移动时，为正数
		float dx = (previousX - moveX);
		previousX = moveX;
		Log.i(TAG, "dx="+dx);
		//移动屏幕
		//判断移动到距离是否已经超过左边界或者右边界
		float scrollX = getScrollX()+dx;
		if(scrollX < -getChildAt(0).getMeasuredWidth()){
			scrollTo(-getChildAt(0).getMeasuredWidth(),0);
		}else if(scrollX > getChildAt(2).getMeasuredWidth()){
			scrollTo(getChildAt(2).getMeasuredWidth(),0);
		}else{
			scrollBy((int)dx,0);
		}		
	}
	
	/**
	 * up操作，当滑动的距离超过左右屏的一半是自动将屏幕滑动到最左边或者最右边
	 * @param event
	 */
	private void upAction(MotionEvent event) {
		int scrollX = getScrollX();
		if(scrollX < -getChildAt(0).getMeasuredWidth()/2){
			currentScreen = LEFT_MENU_SCREEN;
		}else if(scrollX >getChildAt(2).getMeasuredWidth()/2){
			currentScreen = RIGHT_SETTING_SCREEN;
		}else{
			currentScreen = MAIN_SCREEN;
		}
		switchScreen();
	}	
	
	/**
	 * 切换屏幕
	 */
	private void switchScreen() {
		int scrollX = getScrollX();
		int dx;
		if(currentScreen == LEFT_MENU_SCREEN){
			dx = -LEFT_MEASURED_WIDTH - scrollX;
		}else if(currentScreen == RIGHT_SETTING_SCREEN){
			dx = RIGHT_MEASURED_WIDTH - scrollX;
		}else{
			dx = -scrollX;
		}
		//将正在进行中的所有动作复原
		mScroller.forceFinished(true);
		//开始滑动，dx为正数时向左滑动
		mScroller.startScroll(scrollX, 0, dx, 0,Math.abs(dx) * ROTATE);
		//重绘
		invalidate();	//invalidate --> drawChild -->child.draw-->computeScroll
	}



	@Override
	public void computeScroll() {
		if(mScroller.computeScrollOffset()){	//如果返回为true，则表示scroll还未结束
			scrollTo(mScroller.getCurrX(), 0);
			invalidate();
		}
	}
	
	//设置currentScreen,主要死提供给main界面中的两个imageView的点击事件使用
	public void setCurrentScreen(int currentScreen){
		this.currentScreen = currentScreen;
		switchScreen();
	}
	
	public int getCurrentScreen(){
		return currentScreen;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			previousX = (int) ev.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			
			int diffX = (int) (ev.getX() - previousX);
			if(Math.abs(diffX) > touchSlop) {
				return true;
			}
			break;
		default:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}
}
