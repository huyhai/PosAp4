package com.pos.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.pos.CustomFragmentActivity;
import com.pos.MainActivity;
import com.pos.PosApp;
import com.pos.R;
import com.pos.adapter.SearchBillAdapter;
import com.pos.common.Utilities;
import com.pos.db.ItemsDataSource;
import com.pos.db.MainCateDataSource;
import com.pos.db.SearchBillDataSource;
import com.pos.model.ListOrderModel;
import com.pos.model.MainCateModel;
import com.pos.model.SearchBillModel;

public class DialogSearchBill {
	private ListView lvSearch;
	private RelativeLayout rlRecieptNo;
	private RelativeLayout rlDateTime;
	private static Activity ac;
	public static ArrayList<SearchBillModel> list = new ArrayList<SearchBillModel>();
	private static SearchBillAdapter adapter;
	public static int a = -1;
	public static String discountUn;
	public static String recepit;
	public static String idSale = "";

	public void showDialogEditSale(final Context context, final Activity _ac) {
		ac = _ac;
		Builder builder = new Builder(context);
		builder.setTitle("Search Bill");
		builder.setIcon(R.drawable.ic_launcher);
		LayoutInflater inflater = ac.getLayoutInflater();
		View v = inflater.inflate(R.layout.dialog_searchbill, null);
		builder.setView(v);
		initView(v);
		initData();

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface dialog, final int id) {
				if (DialogSearchBill.a != -1) {
					try {
						SearchBillDataSource dataSource = new SearchBillDataSource(
								ac, ac);

						if (PosApp.listOrderData.size() > 0) {
							PosApp.listOrderData.clear();
						}

						PosApp.listOrderData = dataSource.loadItemsBill(list
								.get(a).getSaleID());
						idSale = list.get(a).getSaleID();
						MainActivity ma = new MainActivity();
						ma.notifidataOrderList();
						ma.notifidataSubAndTotal(discountUn);
						Utilities.getGlobalVariable(ac).isHoldPaid = true;
					} catch (Exception e) {
						// TODO: handle exception
					}

				}

			}
		});

		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(final DialogInterface dialog,
							final int which) {

						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.setCanceledOnTouchOutside(false);
		alert.show();

	}

	public void initData() {
		// ((CustomFragmentActivity) ac).setWidth(rlRecieptNo, 7);
		// ((CustomFragmentActivity) ac).setWidth(rlDateTime, 5);
		adapter = new SearchBillAdapter(ac, list);
		lvSearch.setAdapter(adapter);

		notifi();

	}

	public void notifi() {
		if (list != null)
			list.clear();
		SearchBillDataSource dataSource = new SearchBillDataSource(ac, ac);
		list = dataSource.loadItems();
		adapter.setItemList(list);
		adapter.notifyDataSetChanged();
	}

	private void initView(View v) {
		lvSearch = (ListView) v.findViewById(R.id.lvSearch);
		rlRecieptNo = (RelativeLayout) v.findViewById(R.id.rlRecieptNo);
		rlDateTime = (RelativeLayout) v.findViewById(R.id.rlDateTime);

	}

}
