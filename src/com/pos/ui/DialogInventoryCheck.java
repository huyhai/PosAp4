package com.pos.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pos.CustomFragmentActivity;
import com.pos.R;
import com.pos.common.Utilities;
import com.pos.libs.ViewUtils;
import com.pos.db.InventoryDataSource;
import com.pos.db.ItemsDataSource;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class DialogInventoryCheck implements OnClickListener {
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
    private int ia=0;
    private String[] st= null;
    InventoryDataSource a; 
	private CheckBox checkbox_active;
	private CheckBox checkbox_inavtice;

	public void showDialogInventory(final Context context, final Activity ac) {
		Builder builder = new Builder(context);
		builder.setTitle("Inventory Check");
		builder.setIcon(R.drawable.ic_launcher);
		LayoutInflater inflater = ac.getLayoutInflater();
		View v = inflater.inflate(R.layout.dialog_inventorycheck, null);
		builder.setView(v);
		final ItemsDataSource ditemDS = new ItemsDataSource(ac, ac);
		a = new InventoryDataSource(context, ac);
		this.edItemCode = (EditText) v.findViewById(R.id.ediItemCode);
		this.edItemName = (EditText) v.findViewById(R.id.ediItemname);
		this.edItemPrice = (EditText) v.findViewById(R.id.ediPrice);
		this.edItemPrice2 = (EditText) v.findViewById(R.id.ediPrice2);
		this.edItemQty = (EditText) v.findViewById(R.id.ediQty);
		this.edItemSPrice = (EditText) v.findViewById(R.id.ediSpecialPrice);
		this.edItemUPrice = (EditText) v.findViewById(R.id.ediUnitPrice);
		this.btniSearchas = (Button) v.findViewById(R.id.btniSearch);
		this.edsearch = (AutoCompleteTextView) v.findViewById(R.id.ediSearch);
		checkbox_active = (CheckBox) v.findViewById(R.id.checkbox_active);
		checkbox_inavtice = (CheckBox) v.findViewById(R.id.checkbox_inavtice);
		checkbox_active.setChecked(false);
		checkbox_inavtice.setChecked(false);
		checkbox_active.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!checkbox_active.isChecked()) {
					checkbox_active.setChecked(false);
					checkbox_inavtice.setChecked(true);
				} else {
					checkbox_active.setChecked(true);
					checkbox_inavtice.setChecked(false);
				}

			}
		});
		checkbox_inavtice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!checkbox_inavtice.isChecked()) {
					checkbox_inavtice.setChecked(false);
					checkbox_active.setChecked(true);
				} else {
					checkbox_inavtice.setChecked(true);
					checkbox_active.setChecked(false);
				}

			}
		});
		// iadapter= a.loadItemsAdapter(iadapter);

		// iadapter=st.split("|");
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
		// android.R.layout.simple_dropdown_item_1line, iadapter);
		// edsearch.setAdapter(adapter);

		this.btniSearchas.setOnClickListener(this);

		builder.setPositiveButton("Confirm",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String isAc = null;
						if(checkbox_active.isChecked()){
							isAc="1";
						}
						if(checkbox_inavtice.isChecked()){
							isAc="0";
						}
						ditemDS.updateActice(isAc, edItemCode.getText().toString());
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

			if (a.loadItemsSearch("active", edsearch.getText().toString()).equals("1")) {
				checkbox_active.setChecked(true);
				checkbox_inavtice.setChecked(false);

			}else{
				checkbox_active.setChecked(false);
				checkbox_inavtice.setChecked(true);
			}

			// this.edItemQty.setText(st[1].toString());
		}
		
	}
	

}
