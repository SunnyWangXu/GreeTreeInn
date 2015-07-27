package com.greetreeinn.fragment;

import com.greetreeinn.R;
import com.greetreeinn.activity.MainActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 显示左边菜单内容
 * @author 
 *
 */
public class MenuPageFragment extends Fragment
{
	@ViewInject(R.id.hotel_homepage)
	private LinearLayout hotel_homepage;
	
	private FragmentManager fragmentManager;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		
		View view = inflater.inflate(R.layout.menu_page_ll, container,false);
		
		ViewUtils.inject(this, view);
		
		return view;
	}
	
	@OnClick({R.id.hotel_homepage})
	public void onClick(View view)
	{
		fragmentManager = getActivity().getSupportFragmentManager();
		
		// 判断是否是MainActivity，其实就是判断是否是SlidingFragmentActivity
		if (getActivity() instanceof MainActivity)
		{
			MainActivity activity = (MainActivity) getActivity();
			
			switch (view.getId())
			{
			case R.id.hotel_homepage:
				fragmentManager.beginTransaction()
						.replace(
								R.id.main_page_container, 
								new HomePageFragment()).commit();
				break;

			default:
				break;
			}
			
			// 隐藏菜单
			activity.toggle();
		}
		
	}
}






