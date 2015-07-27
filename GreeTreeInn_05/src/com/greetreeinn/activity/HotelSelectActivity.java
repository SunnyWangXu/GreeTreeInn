package com.greetreeinn.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.greetreeinn.R;
import com.greetreeinn.adapter.HotelListAdapter;
import com.greetreeinn.utils.NetworkHttpUtils;
import com.greetreeinn.view.DropDownListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 查询酒店
 * @author Administrator
 *
 */
@ContentView(R.layout.hotel_select)
public class HotelSelectActivity extends Activity
{
	@ViewInject(R.id.hotel_iv_home)
	private ImageView hotel_iv_home;
	
	@ViewInject(R.id.hotel_tv_title)
	private ImageView hotel_tv_title;
	
	@ViewInject(R.id.select_lv)
	private DropDownListView select_lv;
	
	@ViewInject(R.id.select_radio)
	private RadioGroup select_radio;
	
	// 价格选择
	@ViewInject(R.id.select_radio_price)
	private RadioButton select_radio_price;
	
	// 酒店数据
	private List<Map<String,String>> datas;
	
	private HotelListAdapter mAdapter;
	
	// 分页
	private int pageIndex,pageSize;
	
	// 按价格导航点击次数, count%2
	private int count;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// 1,初始化数据
		initData();
		
		// 初始化
		datas = new ArrayList<Map<String,String>>();
		
		// 2,初始化视图
		ViewUtils.inject(this);
		
		// 3,适配器
		mAdapter = new HotelListAdapter(this, datas);
		
		// 列表控件加入适配器
		select_lv.setAdapter(mAdapter);
		
		// 设置列表组件的选项监听
		select_lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id)
			{
				String hotelId = datas.get(position).get("id");
				
				// 处理跳转到酒店详情界面
				Intent intent = new Intent(HotelSelectActivity.this,HotelDetailActivity.class);
				intent.putExtra("hotelId", hotelId);
				startActivity(intent);
			}
		});
		
		// 设置选择查询类别的事件监听
		select_radio.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				// 清理之前查询的数据
				datas.clear();
				
				// 设置类型请求
				
				switch (checkedId)
				{
				// 查询推荐
				case R.id.select_radio_referee:
					orderType = 0+"";
					break;
				case R.id.select_radio_distance:
					orderType = 1+"";
					break;
				case R.id.select_radio_price:
					count++;
					if(count % 2 == 0)
					{
						orderType = 2+"";
						select_radio_price.setText(R.string.price_s);
					}
					else
					{
						orderType = 3+"";
						select_radio_price.setText(R.string.price_x);
					}
					
					orderType = 2+"";
					break;
				case R.id.select_radio_score:
					orderType = 4+"";
					
					break;

				default:
					break;
				}
				
				if(checkedId != R.id.select_radio_filter)
				{
					// 加载数据
					loadData();
				}
			}
		});
		
		// 第一次进入Activity,getData()
		loadData();
	}
	

	/**
	 * 查询酒店列表数据
	 */
	private void loadData()
	{
		
		NetworkHttpUtils.getHotels(getParamMap(), new RequestCallBack<String>()
		{

			@Override
			public void onFailure(HttpException arg0, String arg1)
			{
				
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> response)
			{
				getJson(response.result);
				
			}

		});
	}

	private void getJson(String result)
	{
		// 解析字符串

		// 解析图片内容
		try
		{
			JSONObject o1 = new JSONObject(result);
			
			JSONObject o2 = o1.getJSONObject("responseData");
			
			JSONArray arrsy_items = o2.getJSONArray("items");
			
			JSONObject hotelItem = null;
			
			for (int i = 0; i < arrsy_items.length(); i++)
			{
				hotelItem = arrsy_items.getJSONObject(i);
				
				Map<String, String> map = new HashMap<String, String>();

				map.put("id", hotelItem.getString("id"));
				map.put("name", hotelItem.getString("name"));
				map.put("imageUrl", hotelItem.getString("imageUrl"));
				map.put("score", hotelItem.getString("score"));
				map.put("distance", hotelItem.getString("distance"));
				map.put("price", hotelItem.getString("price"));
				
				JSONArray services = hotelItem.getJSONArray("service");
				// 41 park 51 breakfast 91 WIFI
				String service = null;
				for (int j = 0; j < services.length(); j++)
				{
					service = services.getString(i);
					if(service.equals("41"))
					{
						map.put("isPark", "true");
					}
					if(service.equals("51"))
					{
						map.put("isBreakfast", "true");
					}
					if(service.equals("91"))
					{
						map.put("isWIFI", "true");
					}
					
				}
				
				datas.add(map);
			}
			
			// 通过适配器更新列表
			mAdapter.notifyDataSetChanged();
			
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		
	
	}
	
	/**
	 * 获取参数
	 * @return
	 */
	private Map<String,String> getParamMap()
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("pagesize", pageSize+"");
		params.put("pageindex", pageIndex+"");
		params.put("ordertype", orderType+"");
		params.put("checkintime", checkInTime);
		params.put("cityId", cityId);
		params.put("keyword", keyName);
		return params;
		
	}
	
	public String cityId;
	public String cityName;
	public String keyId;
	public String keyName;
	public String checkInTime;
	public String endDate;
	public String days;
	private String orderType; // 推荐查询
	
	/**
	//跳转到酒店查询的Activity
	intent = new Intent(getActivity(),HotelSelectActivity.class);
	intent.putExtra("cityId", activity.cityId);
	intent.putExtra("keyId", activity.keyId);
	intent.putExtra("checkInTime", activity.startDate);
	intent.putExtra("days", activity.days);
	intent.putExtra("cityName", activity.cityName);
	intent.putExtra("endDate", activity.endDate);
	startActivity(intent);
	**/
	
	private void initData()
	{
		pageSize = 10;
		pageIndex = 1;
		orderType = 0+""; // 推荐查询
		Intent intent = getIntent();
		cityId = intent.getStringExtra("cityId");
		keyId = intent.getStringExtra("keyId");
		keyName = intent.getStringExtra("keyName");
		checkInTime = intent.getStringExtra("checkInTime");
		endDate = intent.getStringExtra("endDate");
		days = intent.getStringExtra("days");
	}

}










