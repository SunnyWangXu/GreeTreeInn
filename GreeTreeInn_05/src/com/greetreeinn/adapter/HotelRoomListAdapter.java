package com.greetreeinn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.greetreeinn.R;
import com.greetreeinn.entity.HotelRooms;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class HotelRoomListAdapter extends BaseAdapter
{
	private Context context;
	private HotelRooms[] datas;
	
	public HotelRoomListAdapter(Context context,HotelRooms[] datas)
	{
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getCount()
	{
		return datas.length;
	}

	@Override
	public Object getItem(int position)
	{
		return datas[position];
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		HotelRoomHolder holder = null;
		HotelRooms item = datas[position];
		
		if(convertView == null)
		{
			holder = new HotelRoomHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.hotel_room_item, null);
			
			ViewUtils.inject(holder,convertView);
			
			convertView.setTag(holder);
		}
		else
		{
			holder = (HotelRoomHolder)convertView.getTag();
		}

		holder.roomTypeName.setText(item.getTypeName());
		holder.price.setText(item.getPrice()+"");
		if(item.getIsFull().equals("true"))
		{
			holder.isFull.setText("满房");
		}
		else
		{
			holder.isFull.setText("未满");
		}
		
		return convertView;
	}
	
	private class HotelRoomHolder
	{
		@ViewInject(R.id.hotel_message_roon_lv_item_name)
		TextView roomTypeName;
		@ViewInject(R.id.hotel_message_roon_lv_item_price)
		TextView price;
		@ViewInject(R.id.hotel_message_roon_lv_item_full)
		TextView isFull;
	}
	

}





