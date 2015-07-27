package com.greetreeinn.fragment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.greetreeinn.R;
import com.greetreeinn.utils.NetworkHttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 酒店评价
 * @author Administrator
 *
 */
public class HotelTopicFragment extends Fragment
{
	@ViewInject(R.id.avg_score)
	private TextView avg_score;

	@ViewInject(R.id.all_topic)
	private TextView all_topic;

	@ViewInject(R.id.sleep_score)
	private TextView sleep_score;

	@ViewInject(R.id.sleep_score_bar)
	private ProgressBar sleep_score_bar;

	@ViewInject(R.id.service_score)
	private TextView service_score;
	@ViewInject(R.id.service_score_bar)
	private ProgressBar service_score_bar;

	@ViewInject(R.id.lave_score)
	private TextView lave_score;
	@ViewInject(R.id.lave_score_bar)
	private ProgressBar lave_score_bar;

	@ViewInject(R.id.health_score)
	private TextView health_score;
	@ViewInject(R.id.health_score_bar)
	private ProgressBar health_score_bar;
	
	
	private String hotelId;
	public HotelTopicFragment(String hotelId)
	{
		this.hotelId = hotelId;
	}
	// 要通过酒店id，获取对应评价信息
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.hotel_detail_topic, container,false);
		
		ViewUtils.inject(this, view);
		
		// 获取酒店评分数据
		getTopicData();
		
		return view;
	}

	/**
	 * "responseData": {
        "totalItems": "19", 
        "totalPage": "2", 
        "currentPage": 1, 
        "score": {
            "HealthPoint": "3.9", 
            "LavePoint": "3.8", 
            "ServicePoint": "4.0", 
            "SleepPoint": "3.9", 
            "AvgPoint": "4"
        }, 
	 */
	private void getTopicData()
	{
		Map<String,String> params = new HashMap<String,String>();
		
		params.put("hotelId", this.hotelId);
		
		NetworkHttpUtils.getHotelMessageTopic(params, new RequestCallBack<String>()
		{
			@Override
			public void onFailure(HttpException arg0, String arg1)
			{
			}
			// UI线程中执行
			@Override
			public void onSuccess(ResponseInfo<String> response)
			{
				if(!"".equals(response.result))
				{
					try
					{
						JSONObject o1 = new JSONObject(response.result);
						
						JSONObject responseDate = o1.getJSONObject("responseData");
						
						String total_topic= responseDate.getString("totalItems");
						
						JSONObject score = responseDate.getJSONObject("score");
						
						// 获取分数
						String healthScore = score.getString("HealthPoint");
						String laveScore = score.getString("LavePoint");
						String serviceScore = score.getString("ServicePoint");
						String sleepScore = score.getString("SleepPoint");
						String avgScore = score.getString("AvgPoint");
						
						// 分数设置
						avg_score.setText(avgScore);
						
						all_topic.setText("("+total_topic+" 条评论)");
						
						sleep_score.setText(sleepScore);
						service_score.setText(serviceScore);
						lave_score.setText(laveScore);
						health_score.setText(healthScore);
						
						// 设置进度条
						sleep_score_bar.setProgress((int)Float.parseFloat(sleepScore)*10);
						service_score_bar.setProgress((int)Float.parseFloat(serviceScore)*10);
						lave_score_bar.setProgress((int)Float.parseFloat(laveScore)*10);
						health_score_bar.setProgress((int)Float.parseFloat(healthScore)*10);
						
					}
					catch (JSONException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			}
		});
		
	}
	
	

}















