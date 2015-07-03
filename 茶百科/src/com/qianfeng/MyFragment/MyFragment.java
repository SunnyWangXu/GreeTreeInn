package com.qianfeng.MyFragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jack.pullrefresh.ui.PullToRefreshBase;
import com.jack.pullrefresh.ui.PullToRefreshBase.OnRefreshListener;
import com.jack.pullrefresh.ui.PullToRefreshListView;
import com.qianfeng.Adapter.mAdapter;
import com.qianfeng.Enum.ContentType;
import com.qianfeng.Task.MyTask;
import com.qianfeng.bean.DataNews;
import com.qianfeng.tea_cyclopedia.R;

public class MyFragment extends Fragment {
	private ListView listView;

	// fragment展示的内容类型
	protected ContentType mType;

	public MyFragment(ContentType type) {
		mType = type;
	}

	public static MyFragment getInstance(ContentType type) {
		// 新建一个对象
		MyFragment myFragment = new MyFragment(type);
		return myFragment;
	}

	List<DataNews> datas = new ArrayList<DataNews>();

	private PullToRefreshListView mPullRefreshView;

	private ListView mListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout, null);

		// listView = (ListView) view.findViewById(R.id.fragment_listView);
		// mAdapter adapter = new mAdapter(datas);
		// listView.setAdapter(adapter);
		//
		// /**
		// * 异步任务加载解析数据
		// */
		// MyTask myTask = new MyTask(datas, adapter);
		// myTask.execute(new String());

		return view;
	}

	// 下拉刷新
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		mPullRefreshView = (PullToRefreshListView) view
				.findViewById(R.id.items_listview);

		mPullRefreshView.setPullLoadEnabled(true);

		mPullRefreshView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {

					}
				});

		// 可下拉刷新
		mPullRefreshView.setPullRefreshEnabled(true);

		// 下拉刷新使用这个getRefreshableView方法可当作ListView使用
		mListView = mPullRefreshView.getRefreshableView();
		// 适配器中 适配 并下载缓存图片
		mAdapter adapter = new mAdapter(datas);
		mListView.setAdapter(adapter);

		/**
		 * 异步任务加载解析数据
		 */
		MyTask myTask = new MyTask(mPullRefreshView, datas, adapter, mType);
		myTask.execute(new String());

		super.onViewCreated(view, savedInstanceState);
		// 下拉刷新之后收回加载条
		// mPullRefreshView.onPullDownRefreshComplete();
	}

}
