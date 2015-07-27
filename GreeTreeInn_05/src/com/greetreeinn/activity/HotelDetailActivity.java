package com.greetreeinn.activity;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.greetreeinn.R;
import com.greetreeinn.adapter.HotelMessageImageAdapter;
import com.greetreeinn.entity.HotelDetailImage;
import com.greetreeinn.entity.HotelRooms;
import com.greetreeinn.entity.Hotels;
import com.greetreeinn.entity.ResponseData;
import com.greetreeinn.fragment.HotelDescFragment;
import com.greetreeinn.fragment.HotelRoomFragment;
import com.greetreeinn.fragment.HotelTopicFragment;
import com.greetreeinn.utils.NetworkHttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.hotel_detail_message)
public class HotelDetailActivity extends FragmentActivity
{
	@ViewInject(R.id.hotel_detail_page_content)
	private RelativeLayout hotel_detail_page_content;
	
	@ViewInject(R.id.hotel_detail_viewpage)
	private ViewPager hotel_detail_viewpage;
	
	@ViewInject(R.id.hotel_detail_view_point)
	private LinearLayout hotel_detail_view_point;

	@ViewInject(R.id.hotel_name)
	private TextView hotel_name;
	
	@ViewInject(R.id.hotel_address)
	private TextView hotel_address;
	
	@ViewInject(R.id.hotel_detail_iv_wifi)
	private ImageView hotel_detail_iv_wifi;
	
	@ViewInject(R.id.hotel_detail_iv_breakfast)
	private ImageView hotel_detail_iv_breakfast;
	
	@ViewInject(R.id.hotel_detail_iv_park)
	private ImageView hotel_detail_iv_park;
	
	@ViewInject(R.id.hotel_radio_group)
	private RadioGroup hotel_radio_group;
	
	@ViewInject(R.id.hotel_radio_fragment)
	private FrameLayout hotel_radio_fragment;
	
	// 酒店对象
	private Hotels hotels;
	private HotelDetailImage[] hotelDetailImages;
	private HotelRooms[] hotelRooms;
	private String hotelDesc;
	private HotelMessageImageAdapter mAdapter;
	private String hotelId;
	
	// 1,获取参数
	// 2,初始化视图
	// 3,适配器
	// 4,请求内容
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// 获取参数
		initData();
		
		// 视图加载
		ViewUtils.inject(this);
		
		initViewPager();
		
		initMeessageRadioGroup();
		
		// 请求酒店信息
		loadHotelMessage();
	}
	
	/**
	 * 处理房间信息，介绍和酒店点评的切换
	 */
	private void initMeessageRadioGroup()
	{
		
		hotel_radio_group.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				Fragment checkFragment = null;
				
				switch (checkedId)
				{
				// 房间价格
				case R.id.hotel_radio_price:
					checkFragment = new HotelRoomFragment(HotelDetailActivity.this,hotelRooms);
					break;
				// 酒店介绍
				case R.id.hotel_radio_desc:
						
					checkFragment = new HotelDescFragment(HotelDetailActivity.this,hotelDesc);
					break;
				// 评价
				case R.id.hotel_radio_score:

					checkFragment = new HotelTopicFragment(hotelId);
					
					break;

				default:
					break;
				}
				
				// hotel_radio_fragment
				
				// 显示对应切换的内容
				getSupportFragmentManager().beginTransaction()
							.replace(R.id.hotel_radio_fragment,
											checkFragment).commit();
				
			}
		});
	}

	private void initViewPager()
	{

		// 显示图片的ViewPager
		setViewPageLayoutParams();
		
		// 设置适配器处理图片加载
		mAdapter = new HotelMessageImageAdapter(
					getSupportFragmentManager(), hotelDetailImages);
		
		hotel_detail_viewpage.setAdapter(mAdapter);
		
		// 设置ViewPager的切换监听
		hotel_detail_viewpage.setOnPageChangeListener(new OnPageChangeListener()
		{
			// 当选择某一页是，圆点切换
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
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0)
			{
			}
		});
	}
	
	/**
	 * 设置ViewPager高度
	 */
	private void setViewPageLayoutParams()
	{
		//获取整个屏幕高度
		DisplayMetrics dm = new DisplayMetrics();
		
		// 在Activity中getWindowManager() 获得WindowManager
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		int screenHeight = dm.heightPixels;
		int screenWidth = dm.widthPixels;
		
		// hotel_detail_page_content必须先已经装在
		LayoutParams lp = hotel_detail_page_content.getLayoutParams();
		lp.width = screenWidth;
		lp.height = screenHeight / 3;
		
		hotel_detail_page_content.setLayoutParams(lp);
	}
	
	private void initView()
	{
		ResponseData hotel = hotels.getResponseData();
		if(null != hotel)
		{
			// 基本视图处理
			hotel_name.setText(hotel.getName());
			hotel_address.setText(hotel.getAddress());
			
			Integer[] services = hotel.getService();
			// 41 park 51 breakfast 91 WIFI
			for (int i = 0; i < services.length; i++)
			{
				if(services[i] == 41)
				{
					hotel_detail_iv_park.setVisibility(View.VISIBLE);
					hotel_detail_iv_park.setImageResource(R.drawable.park);
				}
				else if(services[i] == 51)
				{
					hotel_detail_iv_breakfast.setVisibility(View.VISIBLE);
					hotel_detail_iv_breakfast.setImageResource(R.drawable.breakfast);
				}
				else if(services[i] == 91)
				{
					hotel_detail_iv_wifi.setVisibility(View.VISIBLE);
					hotel_detail_iv_wifi.setImageResource(R.drawable.wifi);
				}
			}
			
			// 顶部图片导航显示
			if(null != hotel.getImages() && hotel.getImages().length > 0)
			{
				// 图片ViewPager处理
				
				// 图片圆点加载
				 addPonit(hotel);
			}
			
			// 酒店房间信息设置
			hotelRooms = hotel.getRooms();
			
			// 设置详情信息
			hotelDesc = hotel.getDescription();
		}
	}
	
	private ImageView[] imageViews;
	private ImageView imageView;
	
	/**
	 * 添加圆点
	 */
	public void addPonit(ResponseData hotel)
	{
		int size = hotel.getImages().length;
		
		imageViews = new ImageView[size];
		
		for (int i = 0; i < imageViews.length; i++)
		{
			imageView = new ImageView(this);
			
			// 设置ImageView布局属性
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
			hotel_detail_view_point.addView(imageView);
		}
	}
	
	/**
	 * 
	 */
	private void loadHotelMessage()
	{
		NetworkHttpUtils.getHotelDetailMessage(getParamMap(), new RequestCallBack<String>()
		{
			@Override
			public void onFailure(HttpException arg0, String arg1)
			{
				// TODO Auto-generated method stub
				
			}
			public void onSuccess(com.lidroid.xutils.http.ResponseInfo<String> response) 
			{
				parseHotelMessage(response.result);
			}
		});
	}
	
	/**
	 * 
	 * @param result
	 */
	private void parseHotelMessage(String result)
	{
		// 使用Gson解析
		Gson gson = new Gson();
		hotels = gson.fromJson(result, Hotels.class);
		
		if(hotels != null)
		{
			// 更新基本视图
			initView();
		}
	}
	
	private Map<String,String> getParamMap()
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("hotelId", hotelId+"");
		return params;
		
	}
	
	/**
	 * 获取参数
	 */
	private void initData()
	{
		Bundle bundle = getIntent().getExtras();
		hotelId = bundle.getString("hotelId");
	}
}
