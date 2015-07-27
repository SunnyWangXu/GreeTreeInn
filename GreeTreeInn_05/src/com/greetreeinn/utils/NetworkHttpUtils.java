package com.greetreeinn.utils;

import java.util.Map;
import java.util.Set;

import com.greetreeinn.app.GreenTreeApplication;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class NetworkHttpUtils
{

	/**
	 * 获取banner
	 * 
	 * @param map
	 * @param callBack
	 */
	public static void getBanner(Map<String, String> map,
			RequestCallBack<?> callBack)
	{
		getHttpRequest(Utilsurl.banner, map, callBack);
	}

	/**
	 * 获取酒店信息
	 * 
	 * @param map
	 * @param callBack
	 */
	public static void getHotels(Map<String, String> map,
			RequestCallBack<?> callBack)
	{
		postHttpRequest(Utilsurl.hotel_list, map, callBack);
	}
	
	/**
	 * 获取酒店详情
	 * @param map
	 * @param callBack
	 */
	public static void getHotelDetailMessage(
			Map<String, String> map,RequestCallBack<?> callBack)
	{
		postHttpRequest(Utilsurl.hotel_message,map,callBack);
	}

	/**
	 * 获取酒店评分详情
	 * @param map
	 * @param callBack
	 */
	public static void getHotelMessageTopic(
			Map<String, String> map,RequestCallBack<?> callBack)
	{
		postHttpRequest(Utilsurl.hotel_message_topic,map,callBack);
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @param map
	 * @param callBack
	 */
	public static void getHttpRequest(String url, Map<String, String> map,
			RequestCallBack<?> callBack)
	{
		RequestParams params = new RequestParams();
		if (null != map)
		{
			Set<String> keys = map.keySet();
			for (String key : keys)
			{
				params.addQueryStringParameter(key, map.get(key));
			}
		}

		GreenTreeApplication.getApp().getHttpUtils()
				.send(HttpMethod.GET, url, params, callBack);
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param map
	 * @param callBack
	 */
	public static void postHttpRequest(String url, Map<String, String> map,
			RequestCallBack<?> callBack)
	{
		RequestParams params = new RequestParams();
		if (null != map)
		{
			Set<String> keys = map.keySet();
			for (String key : keys)
			{
				params.addBodyParameter(key, map.get(key));
			}
		}

		GreenTreeApplication.getApp().getHttpUtils()
				.send(HttpMethod.POST, url, params, callBack);
	}

}
