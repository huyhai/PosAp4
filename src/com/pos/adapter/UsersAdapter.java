package com.pos.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.pos.CustomFragmentActivity;
import com.pos.MainActivity;
import com.pos.PosApp;
import com.pos.R;
import com.pos.common.Utilities;
import com.pos.model.ItemsModel;
import com.pos.model.ListOrderModel;
import com.pos.model.MainCateModel;
import com.pos.model.UsersModel;
import com.pos.ui.Items;
import com.pos.ui.LoginDialog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UsersAdapter extends BaseAdapter {
	private Activity ac;
	public ArrayList<UsersModel> itemList;
	private static int a=-1;

	static class ViewHolder {
		TextView tvName;
		ImageView row_icon;
		RelativeLayout rlAll;
	}

	public UsersAdapter(final Activity _ac, ArrayList<UsersModel> _list) {
		this.itemList = _list;
		this.ac = _ac;
		// imageLoader = new ImageLoader(context);
	}

	public UsersAdapter(final Activity _ac) {
		this.ac = _ac;
		// imageLoader = new ImageLoader(context);
	}

	public void setItemList(ArrayList<UsersModel> countryList) {
		this.itemList = new ArrayList<UsersModel>(countryList);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return itemList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(ac);
			convertView = inflater.inflate(R.layout.user_items, null);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.row_icon = (ImageView) convertView
					.findViewById(R.id.imgAvartar);
			holder.rlAll = (RelativeLayout) convertView
					.findViewById(R.id.rlAll);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		((CustomFragmentActivity) ac).setWidthHeight(holder.row_icon, 7, 4);
		holder.tvName.setText(itemList.get(position).getUserName());
		CustomFragmentActivity.imageLoader2.displayImage("file:///"
				+ itemList.get(position).getImage(), holder.row_icon,
				CustomFragmentActivity.options);
		if (a == position) {
			holder.rlAll.setBackgroundResource(R.color.milky);
		} else {
			holder.rlAll.setBackgroundResource(R.color.transparent);
		}
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LoginDialog lg = new LoginDialog();
				lg.getIDuser(position);
				a=position;
				notifyDataSetChanged();

			}
		});
		return convertView;
	}

}
