package com.greetreeinn.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.greetreeinn.R;
import com.greetreeinn.activity.HotelSelectActivity;
import com.greetreeinn.app.GreenTreeApplication;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class HotelListAdapter extends BaseAdapter
{
	private List<Map<String,String>> datas;
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	
	public HotelListAdapter(Context context, List<Map<String,String>> datas)
	{
		this.mContext = context;
		this.datas = datas;
		mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount()
	{
		return datas.size();
	}

	@Override
	public Object getItem(int position)
	{
		return datas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.hotel_list_item, null);
			
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		
		// 获取数据
		Map<String,String> map = datas.get(position);
		holder.select_lv_item_title.setText(map.get("name"));
		holder.select_item_tv_price.setText(map.get("price"));
		holder.select_lv_item_score.setText(map.get("score"));
		
		// 是否提供相应服务，显示不同图片
		if("true".equals(map.get("isWIFI")))
		{
			holder.select_lv_item_iv_wifi.setVisibility(View.VISIBLE);
			holder.select_lv_item_iv_wifi.setImageResource(R.drawable.wifi);
		}
		else if("true".equals(map.get("isBreakfast")))
		{
			holder.select_lv_item_iv_breakfast.setVisibility(View.VISIBLE);
			holder.select_lv_item_iv_breakfast.setImageResource(R.drawable.breakfast);
		}
		else if("true".equals(map.get("isPark")))
		{
			holder.select_lv_item_iv_park.setVisibility(View.VISIBLE);
			holder.select_lv_item_iv_park.setImageResource(R.drawable.park);
		}
		

		// 获取整个屏幕高度
		DisplayMetrics dm = new DisplayMetrics();
		HotelSelectActivity activty = (HotelSelectActivity) mContext;
		activty.getWindowManager().getDefaultDisplay().getMetrics(dm);

		int screenHeight = dm.heightPixels;
		int screenWidth = dm.widthPixels;

		// 获取布局参数
		LayoutParams lp = holder.select_lv_item_iv.getLayoutParams();
		lp.width = screenWidth/3;
		lp.height = screenHeight / 6;
		
		// 设置布局参数
		holder.select_lv_item_iv.setLayoutParams(lp);
		
		// 显示图片
		GreenTreeApplication.getApp()
				.getImageLoader().displayImage(
						map.get("imageUrl"), holder.select_lv_item_iv);
		
		return convertView;
	}
	
	public class ViewHolder
	{
		@ViewInject(R.id.select_lv_item_iv)
		public ImageView select_lv_item_iv;
		
		@ViewInject(R.id.select_lv_item_title)
		public TextView select_lv_item_title;
		
		@ViewInject(R.id.select_lv_item_iv_wifi)
		public ImageView select_lv_item_iv_wifi;
		
		@ViewInject(R.id.select_lv_item_iv_park)
		public ImageView select_lv_item_iv_park;
		
		@ViewInject(R.id.select_lv_item_iv_breakfast)
		public ImageView select_lv_item_iv_breakfast;
		
		@ViewInject(R.id.select_item_tv_price)
		public TextView select_item_tv_price;
		
		@ViewInject(R.id.select_lv_item_score)
		public TextView select_lv_item_score;
		
	}
	
}
