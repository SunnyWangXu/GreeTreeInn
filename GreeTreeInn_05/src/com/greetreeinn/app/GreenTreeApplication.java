package com.greetreeinn.app;

import java.io.File;

import android.app.Application;
import android.graphics.Bitmap.Config;
import android.os.Environment;

import com.lidroid.xutils.HttpUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class GreenTreeApplication extends Application
{
	
	private static GreenTreeApplication application;
	private HttpUtils mHttpUtils;
	private ImageLoader mImageLoader;
	private DisplayImageOptions mOptions;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		application = this;
		
		initXUtils();
		
		initUniversalImageLoader();
		
	}

	/**
	 * xUtils初始化
	 */
	private void initXUtils()
	{
		mHttpUtils = new HttpUtils("utf-8");
		
		mHttpUtils.configRequestThreadPoolSize(5);
		
		mHttpUtils.configRequestRetryCount(3);
		
		mHttpUtils.configResponseTextCharset("utf-8");
		
		mHttpUtils.configSoTimeout(30000);
	}

	/**
	 * Universal_image_loader初始化
	 */
	private void initUniversalImageLoader()
	{
		mImageLoader = ImageLoader.getInstance();
		
		String diskCachePath = null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			diskCachePath = new StringBuffer()
			.append(Environment.getExternalStorageDirectory())
			.append(File.separator)
			.append("greetree/imageCache").toString();
		}
		else {
				diskCachePath = new StringBuffer()
				.append(Environment.getDataDirectory())
				.append(File.separator)
				.append("greetree/imageCache").toString();
		}
		
		int cacheSize = (int)Runtime.getRuntime().maxMemory() / 8;
		
		ImageLoaderConfiguration configuration = 
				new ImageLoaderConfiguration.Builder(getApplicationContext())
					.threadPoolSize(3)
					.diskCache(new UnlimitedDiskCache(new File(diskCachePath)))
					.diskCacheFileCount(200)
					.memoryCacheSize(cacheSize)
					.build();
		
		ImageLoader.getInstance().init(configuration);
		
		mOptions = new DisplayImageOptions.Builder()
		   .cacheOnDisk(true)
		   .cacheInMemory(true)
		   .bitmapConfig(Config.ARGB_8888)
		   .build();
	}
	
	public static GreenTreeApplication getApp()
	{
		return application;
	}
	
	public HttpUtils getHttpUtils()
	{
		return mHttpUtils;
	}
	
	public ImageLoader getImageLoader()
	{
		return mImageLoader;
	}
	
	public DisplayImageOptions getOptions()
	{
		return mOptions;
	}
	
	
	
	
}
