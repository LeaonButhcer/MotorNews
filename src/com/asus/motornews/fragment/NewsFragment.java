package com.asus.motornews.fragment;

import com.asus.motornews.R;
import com.asus.motornews.UI.DragListView;
import com.asus.motornews.adapter.DragListViewBaseAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsFragment extends Fragment {

	DragListView dragListView;
	
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View newsFragment = inflater.inflate(R.layout.fragment_news, null);
		dragListView = (DragListView) newsFragment.findViewById(R.id.draglistview_news);
		dragListView.setAdapter(new DragListViewBaseAdapter(getActivity()));
		return newsFragment;
	}	
	
}
