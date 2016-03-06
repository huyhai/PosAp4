package com.pos.ui;

import java.math.BigDecimal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.pos.CustomFragmentActivity;
import com.pos.MainActivity;
import com.pos.PosApp;
import com.pos.R;
import com.pos.common.Utilities;
import com.pos.db.ItemsDataSource;
import com.pos.db.MainCateDataSource;
import com.pos.model.ListOrderModel;
import com.pos.model.MainCateModel;

public class DialogEditSale {
	private EditText edItemCode;
	private EditText edQty;
	private EditText edItemname;
	private EditText edUnitPrice;
	private EditText edTotal;
	private int pos;

	public void showDialogEditSale(final Context context, final Activity ac,
			int _pos) {
		pos = _pos;
		Builder builder = new Builder(context);
		builder.setTitle("Edit Sale");
		builder.setIcon(R.drawable.ic_launcher);
		LayoutInflater inflater = ac.getLayoutInflater();
		View v = inflater.inflate(R.layout.edit_sale, null);
		builder.setView(v);
		initView(v);
		initData();

		builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface dialog, final int id) {
				ItemsDataSource a=new ItemsDataSource(ac, ac);
				String str=a.Checkquantity(edQty.getText().toString(), PosApp.listOrderData.get(pos).getItemCode());
				
				
				ListOrderModel md = new ListOrderModel();

				md.setDiscount(PosApp.listOrderData.get(pos).getDiscount());
				md.setItemCode(PosApp.listOrderData.get(pos).getItemCode());
				md.setItemName(PosApp.listOrderData.get(pos).getItemName());
				md.setUnitPrice(edUnitPrice.getText().toString());
				md.setQualyti(edQty.getText().toString());
				md.setPrice2(PosApp.listOrderData.get(pos).getPrice2());
				md.setSpecialPrice(PosApp.listOrderData.get(pos)
						.getSpecialPrice());
				md.setAmount(String.valueOf(Double.parseDouble(md
						.getUnitPrice())
						* Double.parseDouble(md.getQualyti())
						- Double.parseDouble(md.getDiscount())));
				PosApp.listOrderData.set(pos, md);
				MainActivity main = new MainActivity();
				main.notifidataOrderList();
				
			}
		});

		builder.setPositiveButton("Cancel",
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

	private void initData() {
		edItemCode.setText(PosApp.listOrderData.get(pos).getItemCode());
		edQty.setText(PosApp.listOrderData.get(pos).getQualyti());
		edItemname.setText(PosApp.listOrderData.get(pos).getItemName());
		edUnitPrice.setText(PosApp.listOrderData.get(pos).getUnitPrice());
		edTotal.setText(PosApp.listOrderData.get(pos).getAmount());

	}

	private void initView(View v) {
		edItemCode = (EditText) v.findViewById(R.id.edItemCode);
		edQty = (EditText) v.findViewById(R.id.edQty);
		edItemname = (EditText) v.findViewById(R.id.edItemname);
		edUnitPrice = (EditText) v.findViewById(R.id.edUnitPrice);
		edTotal = (EditText) v.findViewById(R.id.edTotal);
		
		edQty.addTextChangedListener(new TextWatcher() {

	        @Override
	        public void afterTextChanged(Editable s) {
	        	if (!TextUtils.isEmpty(edUnitPrice.getText().toString())&&!TextUtils.isEmpty(edQty.getText().toString())&&TextUtils.isDigitsOnly(edQty.getText().toString())) {
				   Double a = Double.valueOf(edUnitPrice.getText().toString());
				   Double b = Double.parseDouble(edQty.getText().toString());
				   Double c = a * b;
				   
				   //BigDecimal a = new BigDecimal(edUnitPrice.getText().toString());
				   //BigDecimal b = new BigDecimal(edQty.getText().toString());
				   
				   //BigDecimal c= a.multiply(b);
				   edTotal.setText(""+c);
				}
	        	
	        }

	        @Override
	        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void onTextChanged(CharSequence s, int start, int before, int count) {

	            



	        } 

	    });
		
		edUnitPrice.addTextChangedListener(new TextWatcher() {

	        @Override
	        public void afterTextChanged(Editable s) {
	        	if (!TextUtils.isEmpty(edUnitPrice.getText().toString())&&!TextUtils.isEmpty(edQty.getText().toString())&&TextUtils.isDigitsOnly(edQty.getText().toString())) {
				   Double a = Double.valueOf(edUnitPrice.getText().toString());
				   Double b = Double.parseDouble(edQty.getText().toString());
				   Double c = a * b;
				   
				   //BigDecimal a = new BigDecimal(edUnitPrice.getText().toString());
				   //BigDecimal b = new BigDecimal(edQty.getText().toString());
				   
				   //BigDecimal c= a.multiply(b);
				   edTotal.setText(""+c);
				}
	        	
	        }

	        @Override
	        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void onTextChanged(CharSequence s, int start, int before, int count) {

	            



	        } 

	    });

	}

}
