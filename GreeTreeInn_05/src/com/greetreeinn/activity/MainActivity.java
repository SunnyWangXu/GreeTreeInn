package com.greetreeinn.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.greetreeinn.R;
import com.greetreeinn.fragment.HomePageFragment;
import com.greetreeinn.fragment.MenuPageFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * 首页布局
 * @author Administrator
 *
 */
public class MainActivity extends SlidingFragmentActivity
{
	private FragmentManager mFragmentManager;
	
	private Fragment mContent;
	
	// 默认参数
	public String cityId;
	public String cityName;
	public String keyId;
	public String keyName;
	public String startDate;
	public String endDate;
	public String days;
	
	private void initData()
	{
		// SharePreference
		cityId = "226";
		cityName = "上海";
		keyId = "";
		keyName = "全部";
		
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		
		startDate = format.format(date);
		
		// 待定
		//endDate = "226";
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// 设置需要显示右边主体部分的容器布局
		setContentView(R.layout.main_page);
		
		initData();
		
		//
		mFragmentManager = getSupportFragmentManager();
		
		if (mContent == null)
		{
			// 首页内容页Fragment
			mContent = new HomePageFragment();
		}
		
		// 使用FragmentManager管理器来装在Fragemnt
		mFragmentManager.beginTransaction()
			.add(R.id.main_page_container,
					mContent,
					"mContent").commit();
		
		// 设置侧边栏容器布局文件
		setBehindContentView(R.layout.main_menu);
		
		// 添加侧边栏Fragment
		mFragmentManager.beginTransaction()
						.replace(
								R.id.main_menu_container,
								new MenuPageFragment()).commit();
		
		// 获取SlidingMenu
		SlidingMenu menu = getSlidingMenu();
		
		menu.setMode(SlidingMenu.LEFT);
		
		// 设置阴影
		menu.setShadowDrawable(R.drawable.menu_shape);
		
		// 阴影宽度
		//menu.setShadowWidth(20);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		
		// 内容页面显示剩余宽度
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		
		// 菜单手势范围
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		
		
	}

}





