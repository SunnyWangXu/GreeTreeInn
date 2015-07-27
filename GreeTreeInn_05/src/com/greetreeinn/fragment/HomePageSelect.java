package com.greetreeinn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.greetreeinn.R;
import com.greetreeinn.activity.HotelSelectActivity;
import com.greetreeinn.activity.MainActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 首页搜索Fragment
 * 
 * @author Administrator
 * 
 */
public class HomePageSelect extends Fragment implements OnClickListener
{
	@ViewInject(R.id.select_hotel)
	private Button select_hotel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// 显示主页
		View view = inflater.inflate(R.layout.homepage_select, container, false);

		ViewUtils.inject(this, view);
		
		return view;
	}

	@OnClick(R.id.select_hotel)
	@Override
	public void onClick(View v)
	{
		Intent intent = null;
		switch (v.getId())
		{
		case R.id.select_hotel:
			// 设置参数，并且跳转到搜索界面
			if(getActivity() instanceof MainActivity)
			{
				MainActivity activity = (MainActivity) getActivity();
				
				// 跳转到酒店查询的Activity
				intent = new Intent(getActivity(),HotelSelectActivity.class);
				intent.putExtra("cityId", activity.cityId);
				intent.putExtra("keyId", activity.keyId);
				intent.putExtra("checkInTime", activity.startDate);
				intent.putExtra("days", activity.days);
				intent.putExtra("cityName", activity.cityName);
				intent.putExtra("endDate", activity.endDate);
				startActivity(intent);
				
			}
			break;

		default:
			break;
		}
	}

}
