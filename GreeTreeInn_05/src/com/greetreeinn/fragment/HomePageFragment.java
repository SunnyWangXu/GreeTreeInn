package com.greetreeinn.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.greetreeinn.R;
import com.greetreeinn.activity.MainActivity;
import com.greetreeinn.adapter.ViewPagerImageAdapter;
import com.greetreeinn.utils.NetworkHttpUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 首页要显示的Fragment
 * 
 */
public class HomePageFragment extends Fragment implements OnClickListener
{
	private FragmentManager mFragmentManager;
	
	@ViewInject(R.id.home_page_content)
	private RelativeLayout home_page_content;
	
	@ViewInject(R.id.homepage_viewpage)
	private ViewPager viewPager;
	
	@ViewInject(R.id.home_view_point)
	private LinearLayout home_view_point;
	
	@ViewInject(R.id.hotel_iv_menu)
	private ImageView hotel_iv_menu;
	
	@ViewInject(R.id.hotel_iv_login)
	private ImageView hotel_iv_login;
	
	@ViewInject(R.id.home_radio)
	private RadioGroup home_radio;
	
	@ViewInject(R.id.home_page_radio_fragment)
	private FrameLayout home_page_radio_fragment;
	
	// 保存切换原点
	private ImageView[] imageViews;
	
	// 保存网络获取的图片集合
	private List<Map<String, String>> data_images;
	
	private ViewPagerImageAdapter mBannerViewAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// 显示主页
		View view = inflater.inflate(R.layout.home_page, container, false);

		ViewUtils.inject(this, view);
		
		hotel_iv_menu.setOnClickListener(this);
		hotel_iv_login.setOnClickListener(this);
		
		data_images = new ArrayList<Map<String,String>>();
		
		mFragmentManager = getActivity().getSupportFragmentManager();
		
		
		// 设置边沿可滑动显示SlidingMenu
		if (getActivity() instanceof SlidingFragmentActivity)
		{
			SlidingFragmentActivity acitivity = (SlidingFragmentActivity) getActivity();
			acitivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		}
		
		// 设置ViewPager高度
		setViewPageLayoutParams();
		
		// 加载Banner
		loadBanner();
		
		//  初始化ViewPager
		initViewPager();
		
		// 搜索和收藏切换
		// 默认显示搜索Fragment
		mFragmentManager.beginTransaction()
								.replace(R.id.home_page_radio_fragment, 
												new HomePageSelect(), "HotelPageSelectOrCollect").commit();
		
		// 设置切换
		home_radio.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				// checkedId 选中的RadioButton的id
				switch (checkedId)
				{
				// 搜索酒店显示
				case R.id.home_page_radio_select:
					
					mFragmentManager.beginTransaction()
						.replace(R.id.home_page_radio_fragment, new HomePageSelect()).commit();

					break;
				// 收藏显示
				case R.id.home_page_radio_collect:

					mFragmentManager.beginTransaction()
						.replace(R.id.home_page_radio_fragment, new HomePageCollect()).commit();

					break;

				default:
					break;
				}
				
			}
		});

		return view;
	}
	
	/**
	 * 初始化ViewPager
	 */
	private void initViewPager()
	{

		// ViewPager+Fragment -> 显示图片
		// 使用FragmentPagerAdapter 
		mBannerViewAdapter = 
				new ViewPagerImageAdapter(getChildFragmentManager(), data_images);
		
		viewPager.setAdapter(mBannerViewAdapter);
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener()
		{
			
			@Override
			public void onPageSelected(int position)
			{
				// 圆点切换
				for (int i = 0; i < imageViews.length; i++)
				{
					if(i == position)
					{
						imageViews[i].setBackgroundResource(R.drawable.point_green_active);
					}
					else
					{
						imageViews[i].setBackgroundResource(R.drawable.point_grey);
					}
					
					//home_view_point.getChildAt(position)
					//	.setBackgroundResource(R.drawable.point_green_active);
				}
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}

	/**
	 * 获取Banners
	 */
	private void loadBanner()
	{
		// 访问网络
		NetworkHttpUtils.getBanner(null, new RequestCallBack<String>()
		{

			@Override
			public void onFailure(HttpException arg0, String arg1)
			{
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> response)
			{
				// 解析图片内容
				try
				{
					JSONObject o1 = new JSONObject(response.result);
					
					JSONObject o2 = o1.getJSONObject("responseData");
					
					JSONArray banners = o2.getJSONArray("bannerList");
					
					JSONObject banner = null;
					
					for (int i = 0; i < banners.length(); i++)
					{
						banner = banners.getJSONObject(i);
						
						Map<String, String> map = new HashMap<String, String>();
						map.put("bannerId", banner.getString("bannerId"));
						map.put("imageUrl", banner.getString("bannerUrl"));
						
						data_images.add(map);
					}
					
					// 有多少个图片就显示多少个圆点
					if(null != data_images)
					{
						// 添加圆点
						addPoints();
						
						//initViewPager();
						mBannerViewAdapter.notifyDataSetChanged();
					}
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
				
			}

		});
	}
	
	private ImageView imageView;
	
	/**
	 * 添加圆点
	 */
	private void addPoints()
	{
		int size = data_images.size();
		imageViews = new ImageView[size];
		
		for (int i = 0; i < imageViews.length; i++)
		{
			imageView = new ImageView(getActivity());
			
			imageView.setLayoutParams(new LayoutParams(20, 20));
			
			imageViews[i] = imageView;
			
			if(i == 0)
			{
				imageViews[i].setBackgroundResource(R.drawable.point_green_active);
			}
			else
			{
				imageViews[i].setBackgroundResource(R.drawable.point_grey);
			}
			// 添加圆点到容器
			home_view_point.addView(imageView);
		}
		
	}

	/**
	 * 设置ViewPager高度
	 */
	private void setViewPageLayoutParams()
	{
		//获取整个屏幕高度
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		int screenHeight = dm.heightPixels;
		int screenWidth = dm.widthPixels;
		
		LayoutParams lp = home_page_content.getLayoutParams();
		lp.width = screenWidth;
		lp.height = screenHeight / 3;
		
		home_page_content.setLayoutParams(lp);
		
		//home_page_content
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.hotel_iv_menu:
			if (getActivity() instanceof MainActivity)
			{
				MainActivity activity = (MainActivity) getActivity();
				activity.toggle();
			}
			break;

		default:
			break;
		}
	}
}






