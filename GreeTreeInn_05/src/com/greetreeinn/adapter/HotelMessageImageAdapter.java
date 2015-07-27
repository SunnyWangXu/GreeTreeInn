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
public class HotelMessageImageAdapter extends FragmentPagerAdapter
{

	private HotelDetailImage[] datas;

	public HotelMessageImageAdapter(FragmentManager fm, HotelDetailImage[] datas)
	{
		super(fm);

		this.datas = datas;

	}

	@Override
	public Fragment getItem(int position)
	{
		final HotelDetailImage hotelDetailImage = datas[position];

		Fragment fragment = new BannerViewFragment();

		Bundle bundle = new Bundle();
		bundle.putString("imageUrl", hotelDetailImage.getBigimg());

		fragment.setArguments(bundle);

		return fragment;
	}

	@Override
	public int getCount()
	{
		return datas.length;
	}

}
