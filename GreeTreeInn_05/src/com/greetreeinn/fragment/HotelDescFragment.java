package com.greetreeinn.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greetreeinn.R;

/**
 * 酒店价格内容页
 * 
 * @author Administrator
 * 
 */
public class HotelDescFragment extends Fragment
{
	private String desc;

	public HotelDescFragment(Context context, String desc)
	{
		this.desc = desc;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.hotel_desc, container, false);

		TextView desc_tv = (TextView) view.findViewById(R.id.hotel_message_desc_tv);

		desc_tv.setText(desc);

		return view;
	}

}
