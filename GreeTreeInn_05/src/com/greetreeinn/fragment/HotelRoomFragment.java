package com.greetreeinn.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.greetreeinn.R;
import com.greetreeinn.adapter.HotelRoomListAdapter;
import com.greetreeinn.entity.HotelRooms;

/**
 * 酒店价格内容页
 * @author Administrator
 *
 */
public class HotelRoomFragment extends Fragment
{
	private ListView hotel_message_room_lv;
	
	private Context context;
	private HotelRooms[] datas;
	
	private HotelRoomListAdapter mAdapter;
	
	public HotelRoomFragment(Context context, HotelRooms[] datas)
	{
		this.context = context;
		this.datas = datas;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.hotel_detail_room_list, container,false);
		
		hotel_message_room_lv = (ListView)
				view.findViewById(R.id.hotel_message_room_lv);
		
		// 设置房间适配器，装载房间类型信息
		mAdapter = new HotelRoomListAdapter(context, datas);
		
		hotel_message_room_lv.setAdapter(mAdapter);
		
		
		return view;
	}
	
	

}
