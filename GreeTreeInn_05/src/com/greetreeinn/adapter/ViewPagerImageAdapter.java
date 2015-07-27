package com.greetreeinn.adapter;

import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.greetreeinn.entity.HotelDetailImage;
import com.greetreeinn.fragment.BannerViewFragment;

/**
 * Banner导航ViewPager对应的适配器，需要结合Fragment，所以使用继承FragmentPagerAdapter
 * 
 * @author Administrator
 * 
 */
public class ViewPagerImageAdapter extends FragmentPagerAdapter
{
	private List<Map<String, String>> datas;

	public ViewPagerImageAdapter(FragmentManager fm,
			List<Map<String, String>> datas)
	{
		super(fm);

		this.datas = datas;

	}

	@Override
	public Fragment getItem(int arg0)
	{
		final Map<String, String> map = datas.get(arg0);

		Fragment fragment = new BannerViewFragment();

		Bundle bundle = new Bundle();
		bundle.putString("id", map.get("bannerId"));
		bundle.putString("imageUrl", map.get("imageUrl"));

		fragment.setArguments(bundle);

		return fragment;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return datas.size();
	}

}
