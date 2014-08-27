package com.asus.motornews.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;
	private String[] titles = new String[]{"tab1","tab2"};
	
	public ViewPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}
	
	

	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}



	@Override
	public Fragment getItem(int location) {
		return fragments.get(location);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}
