package com.pos.ui;

import java.util.ArrayList;

import com.pos.MainActivity;
import com.pos.R;
import com.pos.adapter.MainCateAdapter;
import com.pos.db.MainCateDataSource;
import com.pos.model.MainCateModel;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class Home extends Fragment {
	private static GridView gvItem;
	public static ArrayList<MainCateModel> listMainCateModel = new ArrayList<MainCateModel>();
	public static MainCateAdapter dataAdapter;
	private static Activity ac;

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home, container, false);
		ac=getActivity();
		initField(view);
		initData();
		return view;
	}

	public static void initData() {
		dataAdapter = new MainCateAdapter(ac, listMainCateModel);
		gvItem.setAdapter(dataAdapter);
		loadVattu();

	}

	private void initField(View view) {
		gvItem = (GridView) view.findViewById(R.id.gvItem);

	}

	public static void loadVattu() {

		if (listMainCateModel != null)
			listMainCateModel.clear();

		MainCateDataSource dataSource = new MainCateDataSource(ac);
		listMainCateModel = dataSource.loadMainCate();
		dataAdapter.setItemList(listMainCateModel);
		dataAdapter.notifyDataSetChanged();
	}
}
