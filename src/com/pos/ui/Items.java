package com.pos.ui;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;

import com.pos.Application;
import com.pos.R;
import com.pos.adapter.ItemsAdapter;
import com.pos.db.ItemsDataSource;
import com.pos.model.ItemsModel;

import android.app.Activity;
import android.os.Bundle;
import android.serialport.SerialPort;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class Items extends Fragment  {
	private static GridView gvItem;
	public static ArrayList<ItemsModel> listItemModel = new ArrayList<ItemsModel>();
	public static ItemsAdapter dataAdapter;
	public static String num = "";
	private static Activity ac;

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.items, container, false);
		ac = getActivity();
		initField(view);
		initData(getActivity());
		return view;
	}

	public static void initData(Activity ac) {
		try {
			dataAdapter = new ItemsAdapter(ac, listItemModel);
			gvItem.setAdapter(dataAdapter);
		} catch (Exception e) {
			// TODO: handle exception
		}

		loadVattu();

	}

	private void initField(View view) {
		gvItem = (GridView) view.findViewById(R.id.gvItem1);

	}

	public static void loadVattu() {

		if (listItemModel != null)
			listItemModel.clear();

		ItemsDataSource dataSource = new ItemsDataSource(ac, ac);
		listItemModel = dataSource.loadItems();
		dataAdapter.setItemList(listItemModel);
		dataAdapter.notifyDataSetChanged();
	}

}
