package com.asus.motornews;

import com.asus.motornews.UI.ASUSSlidingMenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class SlidingMenuActivity extends Activity implements OnClickListener {

	private ASUSSlidingMenu slidingMenu;
	private ImageView ivSMBack;
	private ImageView ivSMSetting;
	
	private int currentScreen;	//当前屏幕
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slidingmenu);
		initView();
	}

	private void initView() {
		slidingMenu = (ASUSSlidingMenu) findViewById(R.id.sm_sliding_menu);
		ivSMBack = (ImageView)findViewById(R.id.iv_sm_main_back);
		ivSMBack.setOnClickListener(this);
		ivSMSetting = (ImageView) findViewById(R.id.iv_sm_main_setting);
		ivSMSetting.setOnClickListener(this);
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
