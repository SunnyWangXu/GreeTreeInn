package com.qianfeng.tea_cyclopedia.TabFragment;

import com.qianfeng.tea_cyclopedia.R;
import com.qianfeng.tea_cyclopedia.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ManageFregment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		inflater.inflate(R.layout.fragment_layout, container, false);

		return container;
	}
}
