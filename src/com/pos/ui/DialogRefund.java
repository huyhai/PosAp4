package com.pos.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pos.CustomFragmentActivity;
import com.pos.MainActivity;
import com.pos.PosApp;
import com.pos.R;
import com.pos.adapter.ListOrderAdapter;
import com.pos.adapter.ListRefundAdapter;
import com.pos.adapter.MainCateAdapter;
import com.pos.common.Utilities;
import com.pos.db.MainCateDataSource;
import com.pos.db.RefundDataSource;
import com.pos.model.ListOrderModel;
import com.pos.model.MainCateModel;

public class DialogRefund {
	private RelativeLayout rlItemCode;
	private RelativeLayout rlItemName;
	private RelativeLayout rlUnitPrice;
	private RelativeLayout rlQty;
	private RelativeLayout rlDiscount;
	private RelativeLayout rlAmount;
	private RelativeLayout rlLeft;
	private RelativeLayout rlRight;
	private RelativeLayout rlALLRL;
	private EditText edSearch;
	private Button btn;
	private ListRefundAdapter adapter;
	private ListView lvRefund;
	public static int qly = -1;
	private EditText edQty;
	private EditText edReasion;
	// public static ArrayList<ListOrderModel> listRe=new
	// ArrayList<ListOrderModel>();

	// public void showDialogAddCate(final Context context, final Activity ac) {
	// Builder builder = new Builder(context);
	// // builder.setTitle(R.layout.define_custom_item);
	// builder.setIcon(R.drawable.ic_launcher);
	// LayoutInflater inflater = ac.getLayoutInflater();
	// View v = inflater.inflate(R.layout.refund_dialog, null);
	// builder.setView(v);
	// initView(v);
	// initData(ac);
	//
	// builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(final DialogInterface dialog, final int id) {
	// if (qly >= 0) {
	// ListOrderModel md = new ListOrderModel();
	// md.setPrice2(PosApp.listOrderData.get(qly).getPrice2());
	// md.setSpecialPrice(PosApp.listOrderData.get(qly)
	// .getSpecialPrice());
	// md.setDiscount(PosApp.listOrderData.get(qly).getDiscount());
	// md.setItemCode(PosApp.listOrderData.get(qly).getItemCode());
	// md.setItemName(PosApp.listOrderData.get(qly).getItemName());
	// md.setUnitPrice(PosApp.listOrderData.get(qly)
	// .getUnitPrice());
	// md.setQualyti(edQty.getText().toString());
	// md.setAmount(String.valueOf(Double
	// .parseDouble(PosApp.listOrderData.get(qly)
	// .getUnitPrice())
	// * Double.parseDouble(md.getQualyti())
	// - Double.parseDouble(md.getDiscount())));
	// PosApp.listOrderData.set(qly, md);
	// MainActivity main = new MainActivity();
	//
	// main.notifidataOrderList();
	// dialog.dismiss();
	// }
	//
	// }
	// });
	//
	// builder.setPositiveButton("Cancel",
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(final DialogInterface dialog,
	// final int which) {
	//
	// dialog.dismiss();
	// }
	// });
	// AlertDialog alert = builder.create();
	// alert.setCancelable(false);
	// alert.setCanceledOnTouchOutside(false);
	//
	// View view = inflater.inflate(R.layout.title_refund, null);
	// edSearch = (EditText) view.findViewById(R.id.edSearch);
	// alert.setCustomTitle(view);
	// alert.show();
	// edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener()
	// {
	// @Override
	// public boolean onEditorAction(TextView v, int actionId,
	// KeyEvent event) {
	// if (actionId == EditorInfo.IME_ACTION_SEARCH) {
	// RefundDataSource data = new RefundDataSource(ac, ac);
	// int a = data.getSaleID(edSearch.getText().toString());
	// if (PosApp.listOrderData != null)
	// PosApp.listOrderData.clear();
	// PosApp.listOrderData = data.loadItemsBill(String.valueOf(a));
	// adapter.setItemList(PosApp.listOrderData);
	// adapter.notifyDataSetChanged();
	// return true;
	// }
	// return false;
	// }
	//
	// });
	// }

	private Activity ac;
	private Dialog dialogEditItems;
	private Boolean isShowDialog = false;
	private RelativeLayout rlOk;
	private RelativeLayout rlCancel;

	public void showDialogSelectImg(Activity _ac) {
		ac = _ac;
		// custom dialog
		if (!isShowDialog) {
			isShowDialog = true;
			// imgSet = _imgSet;
			dialogEditItems = new Dialog(ac);
			dialogEditItems.getWindow();
			dialogEditItems.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialogEditItems.setContentView(R.layout.refund_dialog);
			dialogEditItems.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			// dialogEditItems.setTitle("Title...");

			// set the custom dialog components - text, image and button

			rlAmount = (RelativeLayout) dialogEditItems
					.findViewById(R.id.rlAmount);
			rlDiscount = (RelativeLayout) dialogEditItems
					.findViewById(R.id.rlDiscount);
			rlItemCode = (RelativeLayout) dialogEditItems
					.findViewById(R.id.rlItemCode);
			rlItemName = (RelativeLayout) dialogEditItems
					.findViewById(R.id.rlItemName);
			rlQty = (RelativeLayout) dialogEditItems.findViewById(R.id.rlQty);
			rlUnitPrice = (RelativeLayout) dialogEditItems
					.findViewById(R.id.rlUnitPrice);
			rlLeft = (RelativeLayout) dialogEditItems.findViewById(R.id.rlLeft);
			rlRight = (RelativeLayout) dialogEditItems
					.findViewById(R.id.rlRight);
			rlALLRL = (RelativeLayout) dialogEditItems.findViewById(R.id.rlBu);
			btn = (Button) dialogEditItems.findViewById(R.id.btn);
			lvRefund = (ListView) dialogEditItems.findViewById(R.id.lvRefund);
			edQty = (EditText) dialogEditItems.findViewById(R.id.edQty);
			edSearch = (EditText) dialogEditItems.findViewById(R.id.edSearch);
			rlOk = (RelativeLayout) dialogEditItems.findViewById(R.id.rlOk);
			rlCancel = (RelativeLayout) dialogEditItems
					.findViewById(R.id.rlCancel);
			if (PosApp.listOrderData != null) {
				PosApp.listOrderData.clear();
			}
			adapter = new ListRefundAdapter(ac, PosApp.listOrderData);
			lvRefund.setAdapter(adapter);
			rlOk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (qly >= 0) {
						ListOrderModel md = new ListOrderModel();
						md.setPrice2(PosApp.listOrderData.get(qly).getPrice2());
						md.setSpecialPrice(PosApp.listOrderData.get(qly)
								.getSpecialPrice());
						md.setDiscount(PosApp.listOrderData.get(qly)
								.getDiscount());
						md.setItemCode(PosApp.listOrderData.get(qly)
								.getItemCode());
						md.setItemName(PosApp.listOrderData.get(qly)
								.getItemName());
						md.setUnitPrice("-"
								+ PosApp.listOrderData.get(qly).getUnitPrice());
						md.setQualyti(edQty.getText().toString());
						md.setAmount("-"
								+ String.valueOf(Double
										.parseDouble(PosApp.listOrderData.get(
												qly).getUnitPrice())
										* Double.parseDouble(md.getQualyti())
										- Double.parseDouble(md.getDiscount())));
						PosApp.listOrderData.set(qly, md);
						MainActivity main = new MainActivity();

						main.notifidataOrderList2();

						dialogEditItems.dismiss();
					}

				}
			});
			rlCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialogEditItems.dismiss();

				}
			});
			((CustomFragmentActivity) ac).setWidth(rlDiscount, 10);
			((CustomFragmentActivity) ac).setWidth(rlItemCode, 9);
			((CustomFragmentActivity) ac).setWidth(rlQty, 25);
			((CustomFragmentActivity) ac).setWidth(rlUnitPrice, 10);
			((CustomFragmentActivity) ac).setWidth(rlItemName, 6);
			((CustomFragmentActivity) ac).setWidth(rlLeft, 1.5);
			((CustomFragmentActivity) ac).setWidthHeight(rlALLRL, 1, 1.3);
			((CustomFragmentActivity) ac).setWidth(rlRight, 1);

			edSearch.addTextChangedListener(new TextWatcher() {
				public void afterTextChanged(Editable s) {
					RefundDataSource data = new RefundDataSource(ac, ac);
					int a = data.getSaleID(edSearch.getText().toString());
					if (PosApp.listOrderData != null)
						PosApp.listOrderData.clear();
					PosApp.listOrderData = data.loadItemsBill(String.valueOf(a));
					adapter.setItemList(PosApp.listOrderData);
					adapter.notifyDataSetChanged();
				}

				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
				}

				public void onTextChanged(CharSequence s, int start,
						int before, int count) {

				}
			});

			dialogEditItems.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					isShowDialog = false;
				}
			});

			dialogEditItems.show();
		}

	}

	private void initData(final Activity context) {
		((CustomFragmentActivity) context).setWidth(rlDiscount, 10);
		((CustomFragmentActivity) context).setWidth(rlItemCode, 9);
		((CustomFragmentActivity) context).setWidth(rlQty, 25);
		((CustomFragmentActivity) context).setWidth(rlUnitPrice, 10);
		((CustomFragmentActivity) context).setWidth(rlItemName, 6);
		((CustomFragmentActivity) context).setWidth(rlLeft, 1.5);
		((CustomFragmentActivity) context).setWidth(rlALLRL, 3);
		((CustomFragmentActivity) context).setWidth(rlRight, 1);
		adapter = new ListRefundAdapter(context, PosApp.listOrderData);
		lvRefund.setAdapter(adapter);
		// btn.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// RefundDataSource data = new RefundDataSource(context, context);
		// int a = data.getSaleID(edSearch.getText().toString());
		// if (PosApp.listOrderData != null)
		// PosApp.listOrderData.clear();
		// PosApp.listOrderData = data.loadItemsBill(String.valueOf(a));
		// adapter.setItemList(PosApp.listOrderData);
		// adapter.notifyDataSetChanged();
		// }
		// });

	}

	// public void loadVattu(String id) {
	//
	// if (PosApp.listOrderData != null)
	// PosApp.listOrderData.clear();
	//
	// MainCateDataSource dataSource = new MainCateDataSource(ac);
	// listMainCateModel = dataSource.loadMainCate();
	// dataAdapter.setItemList(listMainCateModel);
	// dataAdapter.notifyDataSetChanged();
	// }
	private void initView(View v) {
		rlAmount = (RelativeLayout) v.findViewById(R.id.rlAmount);
		rlDiscount = (RelativeLayout) v.findViewById(R.id.rlDiscount);
		rlItemCode = (RelativeLayout) v.findViewById(R.id.rlItemCode);
		rlItemName = (RelativeLayout) v.findViewById(R.id.rlItemName);
		rlQty = (RelativeLayout) v.findViewById(R.id.rlQty);
		rlUnitPrice = (RelativeLayout) v.findViewById(R.id.rlUnitPrice);
		rlLeft = (RelativeLayout) v.findViewById(R.id.rlLeft);
		rlRight = (RelativeLayout) v.findViewById(R.id.rlRight);
		// rlALLRL = (RelativeLayout) v.findViewById(R.id.rlBu);
		btn = (Button) v.findViewById(R.id.btn);
		lvRefund = (ListView) v.findViewById(R.id.lvRefund);
		edQty = (EditText) v.findViewById(R.id.edQty);
		edReasion = (EditText) v.findViewById(R.id.edReason);
		edReasion.setVisibility(View.INVISIBLE);
	}

}
