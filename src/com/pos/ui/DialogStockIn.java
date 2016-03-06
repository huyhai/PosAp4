package com.pos.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pos.CustomFragmentActivity;
import com.pos.MainActivity;
import com.pos.PosApp;
import com.pos.R;
import com.pos.common.Utilities;
import com.pos.libs.ViewUtils;
import com.pos.model.ListOrderModel;
import com.pos.model.PayModel;
import com.pos.db.PayDataSource;
import com.pos.db.StockinDataSource;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class DialogStockIn implements OnClickListener {
	private EditText edItemCode;
	private EditText edItemQty;
	private EditText edItemName;
	private EditText edItemUPrice;
	private EditText edItemPrice;
	private EditText edItemPrice2;
	private EditText edItemSPrice;
	private Button btniSearchas;
	private AutoCompleteTextView edsearch;
	private static String[] iadapter;
	private int ia = 0;
	private String[] st = null;
	StockinDataSource a;

	// private Button btnitest;

	public void showDialogStockIn(final Context context, final Activity ac) {
		Builder builder = new Builder(context);
		builder.setTitle("Stock In");
		builder.setIcon(R.drawable.ic_launcher);
		LayoutInflater inflater = ac.getLayoutInflater();
		View v = inflater.inflate(R.layout.dialog_stockin, null);
		builder.setView(v);
		a = new StockinDataSource(context, ac);
		this.edItemCode = (EditText) v.findViewById(R.id.ediItemCode);
		this.edItemName = (EditText) v.findViewById(R.id.ediItemname);
		this.edItemPrice = (EditText) v.findViewById(R.id.ediPrice);
		this.edItemPrice2 = (EditText) v.findViewById(R.id.ediPrice2);
		this.edItemQty = (EditText) v.findViewById(R.id.ediQty);
		this.edItemSPrice = (EditText) v.findViewById(R.id.ediSpecialPrice);
		this.edItemUPrice = (EditText) v.findViewById(R.id.ediUnitPrice);
		this.btniSearchas = (Button) v.findViewById(R.id.btniSearch);
		this.edsearch = (AutoCompleteTextView) v.findViewById(R.id.ediSearch);

		// iadapter=st.split("|");
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
		// android.R.layout.simple_dropdown_item_1line, iadapter);
		// edsearch.setAdapter(adapter);
		// this.btnitest.setOnClickListener(this);
		this.btniSearchas.setOnClickListener(this);
		builder.setPositiveButton("Confirm",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						PayDataSource dataSourcePay = new PayDataSource(ac, ac);
						if (!TextUtils.isEmpty(edsearch.getText().toString())
								&& !TextUtils.isEmpty(edItemUPrice.getText()
										.toString())
								&& !TextUtils.isEmpty(edItemQty.getText()
										.toString())
								&& !TextUtils.isEmpty(edItemPrice.getText()
										.toString())) {

							double old_costprice = Double.parseDouble(a
									.loadItemsSearch("unitprice", edsearch
											.getText().toString()));
							double old_qty = Double.parseDouble(a
									.loadItemsSearch("qty", edsearch.getText()
											.toString()));
							double new_costprice = Double
									.parseDouble(edItemUPrice.getText()
											.toString());
							double new_qty = Double.parseDouble(edItemQty
									.getText().toString());
							double totalB = old_qty + new_qty;
							double A = old_costprice * old_qty;
							double B = new_costprice * new_qty;

							double new_rqty = old_qty + new_qty;
							double new_rcostprice = (A + B) / totalB;
							double new_roundcostprice = Math
									.round(new_rcostprice * 100.0) / 100.0;
							String new_strprice = "" + new_roundcostprice;
							String new_strqty = "" + new_rqty;
							String idr = a.loadItemsSearch("itemcode",
									edItemCode.getText().toString());
							double pricestro = Double.parseDouble(edItemPrice
									.getText().toString());
							String pricestr = "" + pricestro;
							a.UpdateStockIn(new_strprice, new_strqty, idr,
									pricestr,
									edItemPrice2.getText().toString(),
									edItemSPrice.getText().toString());
							String error = a.UpdateStockIn3(new_strprice,
									edItemQty.getText().toString(), idr,
									pricestr,
									edItemPrice2.getText().toString(),
									edItemSPrice.getText().toString());
							PayDataSource data = new PayDataSource(ac, ac);
							PayModel up1 = new PayModel();
							up1.setItemCode(edItemCode.getText().toString());
							up1.setItemName(edItemName.getText().toString());
							up1.setQuantity(edItemQty.getText().toString());
							up1.setUnitPrice(edItemPrice.getText().toString());
							up1.setUom(dataSourcePay.loadUom(up1.getItemCode()));
							long a = data.insertInventoryReportStockIn(up1, ac,
									edItemUPrice.getText().toString());
						}
					}
				});

		builder.setNegativeButton("Close",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});

		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.setCanceledOnTouchOutside(false);
		alert.show();

	}

	@Override
	public void onClick(View v) {
		if (v == btniSearchas) {
			this.edItemCode.setText(a.loadItemsSearch("itemcode", edsearch
					.getText().toString()));
			this.edItemQty.setText(a.loadItemsSearch("qty", edsearch.getText()
					.toString()));
			this.edItemName.setText(a.loadItemsSearch("itemname", edsearch
					.getText().toString()));
			this.edItemUPrice.setText(a.loadItemsSearch("unitprice", edsearch
					.getText().toString()));
			this.edItemPrice.setText(a.loadItemsSearch("price", edsearch
					.getText().toString()));
			this.edItemPrice2.setText(a.loadItemsSearch("price2", edsearch
					.getText().toString()));
			this.edItemSPrice.setText(a.loadItemsSearch("specials", edsearch
					.getText().toString()));

			// this.edItemQty.setText(st[1].toString());

		}

	}

}
