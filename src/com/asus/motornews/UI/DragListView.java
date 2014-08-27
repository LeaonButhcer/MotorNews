package com.asus.motornews.UI;

import com.asus.motornews.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

public class DragListView extends ListView {

	public DragListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderView();
	}

	private void initHeaderView() {
		View headerView = inflate(getContext(), R.layout.draglistview_header, null);
		addHeaderView(headerView);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
	}
	
	

}
