package com.asus.motornews;

import java.util.ArrayList;
import java.util.List;

import com.asus.motornews.UI.ASUSSlidingMenu;
import com.asus.motornews.adapter.ViewPagerAdapter;
import com.asus.motornews.fragment.NewsFragment;
import com.asus.motornews.fragment.PicsFragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SlidingMenuActivity extends FragmentActivity implements OnClickListener {

	private static final String TAG = "SlidingMenuActivity";
	
	private ASUSSlidingMenu slidingMenu;
	private ImageView ivSMBack;
	private ImageView ivSMSetting;
	
	private ViewPager viewPager;
	private List<TextView> textViews;
	
	private int currentScreen;	//当前屏幕

	private List<Fragment> fragments;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slidingmenu);
		initTopView();	
		initMainTab();
		initViewPager();
	}

	/**
	 * 初始化顶部的两个imageview和slidingmenu
	 */
	private void initTopView() {
		slidingMenu = (ASUSSlidingMenu) findViewById(R.id.sm_sliding_menu);
		ivSMBack = (ImageView)findViewById(R.id.iv_sm_main_back);
		ivSMBack.setOnClickListener(this);
		ivSMSetting = (ImageView) findViewById(R.id.iv_sm_main_setting);
		ivSMSetting.setOnClickListener(this);
	}
	
	/**
	 * 初始化主界面的tab的TextView
	 */
	private void initMainTab() {
		textViews = new ArrayList<TextView>();
		TextView tv1 = (TextView) findViewById(R.id.tv_main_tab_title1);
		tv1.setOnClickListener(new TabTextViewClickListener(0));
		TextView tv2 = (TextView) findViewById(R.id.tv_main_tab_title2);
		tv2.setOnClickListener(new TabTextViewClickListener(1));
	}
	
	class TabTextViewClickListener implements OnClickListener{

		int index;
		
		public TabTextViewClickListener(int index){
			this.index = index;
		}
		@Override
		public void onClick(View v) {
			viewPager.setCurrentItem(index, true);
		}
		
	}
	
	/**
	 * 初始化viewPager
	 */
	private void initViewPager() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		fragments = new ArrayList<Fragment>();
		fragments.add(new NewsFragment());
		fragments.add(new PicsFragment());
		viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
	}

	class MyPageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			Log.i(TAG, "-----onPageScrollStateChanged---arg0="+arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			Log.i(TAG, "+++++onPageScrolled+++arg0="+arg0+",arg1="+arg1+",arg2="+arg2);
			
		}

		@Override
		public void onPageSelected(int position) {
			Log.i(TAG, "*******onPageScrolled,arg0="+position);
			if(position==0){
				slidingMenu.setLeftTouchEvent(true);
				slidingMenu.setRightTouchEvent(false);
			}else if(position==fragments.size()-1){
				slidingMenu.setLeftTouchEvent(false);
				slidingMenu.setRightTouchEvent(true);
			}else{
				slidingMenu.setLeftTouchEvent(false);
				slidingMenu.setRightTouchEvent(false);
			}
		}
		
	}

	@Override
	public void onClick(View v) {
		currentScreen = slidingMenu.getCurrentScreen();
		switch (v.getId()) {
		case R.id.iv_sm_main_back:
			if(currentScreen ==	ASUSSlidingMenu.MAIN_SCREEN){
				slidingMenu.setCurrentScreen(ASUSSlidingMenu.LEFT_MENU_SCREEN);
			}else{
				slidingMenu.setCurrentScreen(ASUSSlidingMenu.MAIN_SCREEN);
			}
			break;
		case R.id.iv_sm_main_setting:
			if(currentScreen ==	ASUSSlidingMenu.MAIN_SCREEN){
				slidingMenu.setCurrentScreen(ASUSSlidingMenu.RIGHT_SETTING_SCREEN);
			}else{
				slidingMenu.setCurrentScreen(ASUSSlidingMenu.MAIN_SCREEN);
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * menu中的所有textView对应的点击事件
	 * @param v
	 */
	public void menuTabClick(View v){
		switch (v.getId()) {
		case R.id.tv_sm_menu_news:
			Toast.makeText(this, "新闻", 0).show();
			break;

		default:
			break;
		}
	}
	
}
