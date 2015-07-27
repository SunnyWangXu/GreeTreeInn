package com.greetreeinn.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.greetreeinn.R;
import com.greetreeinn.app.GreenTreeApplication;

/**
 * 显示每一张Banner的Fragment
 * @author Administrator
 *
 */
public class BannerViewFragment extends Fragment
{
	private ImageView iv_viewpage_item;
	
	@Override
	public View onCreateView(
			LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		
		View view = inflater.inflate(
				R.layout.banner_viewpager_item, container,false);
		
		iv_viewpage_item = (ImageView)view.findViewById(R.id.iv_viewpage_item);
		
		// 使用getArguments()获取参数
		Bundle bundle = getArguments();
		
		String imageUrl = bundle.getString("imageUrl");
		
		// 使用Universal-Image-Loader加载图片
		GreenTreeApplication.getApp()
				.getImageLoader().displayImage(
						imageUrl, iv_viewpage_item, 
						GreenTreeApplication.getApp().getOptions());
		
		iv_viewpage_item.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// 跳转到详细页面
			}
		});
		
		
		return view;
	}

}
