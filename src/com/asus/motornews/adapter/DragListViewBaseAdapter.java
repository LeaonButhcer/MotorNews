package com.asus.motornews.adapter;

import com.asus.motornews.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DragListViewBaseAdapter extends BaseAdapter {

	Context mContext;
	LayoutInflater mInflater;
	
	public DragListViewBaseAdapter(Context mContext){
		this.mContext = mContext;
		mInflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		return 20;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.draglistview_item, null);
			holder = new ViewHolder();
			holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_dlv_item_icon);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_dlv_item_title);
			holder.tvSummary = (TextView) convertView.findViewById(R.id.tv_dlv_item_summary);
			holder.tvNumber = (TextView) convertView.findViewById(R.id.tv_dlv_item_number);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		//设置数据
		holder.ivIcon.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_launcher));
		holder.tvTitle.setText("我是一个头");
		holder.tvSummary.setText("我是一个摘要，请指示");
		holder.tvNumber.setText("100跟帖");
		
		return convertView;
	}
	
	class ViewHolder{
		ImageView ivIcon;
		TextView tvTitle;
		TextView tvSummary;
		TextView tvNumber;
	}

}
