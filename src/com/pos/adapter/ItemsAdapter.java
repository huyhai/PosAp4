package com.pos.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.pos.Application;
import com.pos.CustomFragmentActivity;
import com.pos.MainActivity;
import com.pos.PosApp;
import com.pos.R;
import com.pos.common.ConstantValue;
import com.pos.common.Utilities;
import com.pos.db.CompanyDataSource;
import com.pos.db.ItemsDataSource;
import com.pos.model.ItemsModel;
import com.pos.model.ListOrderModel;
import com.pos.model.MainCateModel;
import com.pos.ui.DialogGroup;
import com.pos.ui.DialogItems;
import com.pos.ui.Home;
import com.pos.ui.Items;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData.Item;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.serialport.SerialPort;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ItemsAdapter extends BaseAdapter {
	private Activity ac;
	public ArrayList<ItemsModel> itemList;
	private SerialPort mSerialPort_V;
	private OutputStream mOutputStream_V;
	private Application mApplication;
	double amount1 = 0;

	static class ViewHolder {
		TextView tvName;
		ImageView row_icon;
		RelativeLayout rlBgIt;
		RelativeLayout rlBgIt1;
		TextView tvPrice;
	}

	public ItemsAdapter(final Activity _ac, ArrayList<ItemsModel> _list) {
		this.itemList = _list;
		this.ac = _ac;
		// imageLoader = new ImageLoader(context);
	}

	public ItemsAdapter(final Activity _ac) {
		this.ac = _ac;
		// imageLoader = new ImageLoader(context);
	}

	public void setItemList(ArrayList<ItemsModel> countryList) {
		this.itemList = new ArrayList<ItemsModel>(countryList);
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
			convertView = inflater.inflate(R.layout.items_item, null);
			holder.tvName = (TextView) convertView.findViewById(R.id.row_title);
			holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
			holder.row_icon = (ImageView) convertView
					.findViewById(R.id.row_icon);
			holder.rlBgIt = (RelativeLayout) convertView
					.findViewById(R.id.rlBgIt);
			holder.rlBgIt1 = (RelativeLayout) convertView
					.findViewById(R.id.rlBgIt1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		LayoutParams lpimgAbout = holder.rlBgIt.getLayoutParams();
		lpimgAbout.width = (int) (Utilities.getGlobalVariable(ac).device_width / 10);
		lpimgAbout.height = (int) (Utilities.getGlobalVariable(ac).device_height / 6);
		holder.rlBgIt.setLayoutParams(lpimgAbout);
		if (position == 0) {
			((CustomFragmentActivity) ac).setWidthHeight(holder.row_icon, 25,
					17);
			holder.row_icon.setBackgroundResource(R.drawable.icon_back);
			holder.rlBgIt.setBackgroundResource(R.color.transparent);
		} else {
			if (itemList.get(position).getItem_image().endsWith("red")) {
				holder.row_icon.setBackgroundColor(Color.RED);
			} else if (itemList.get(position).getItem_image()
					.endsWith("yellow")) {
				holder.row_icon.setBackgroundColor(Color.YELLOW);
			} else if (itemList.get(position).getItem_image().endsWith("blue")) {
				holder.row_icon.setBackgroundColor(Color.BLUE);
			} else if (itemList.get(position).getItem_image().endsWith("green")) {
				holder.row_icon.setBackgroundColor(Color.GREEN);
			} else if (itemList.get(position).getItem_image().endsWith("black")) {
				holder.row_icon.setBackgroundColor(Color.BLACK);
			} else if (itemList.get(position).getItem_image().endsWith("white")) {
				holder.row_icon.setBackgroundColor(Color.WHITE);
			} else if (itemList.get(position).getItem_image()
					.endsWith("magenta")) {
				holder.row_icon.setBackgroundColor(Color.MAGENTA);
			} else if (itemList.get(position).getItem_image().endsWith("cyan")) {
				holder.row_icon.setBackgroundColor(Color.CYAN);
			} else {
				CustomFragmentActivity.imageLoader2.displayImage("file:///"
						+ itemList.get(position).getItem_image(),
						holder.row_icon, CustomFragmentActivity.options);
				// File file = new File(itemList.get(position).getItem_image());
				// holder.row_icon.setImageBitmap(decodeFile(file));
			}
			holder.tvPrice.setBackgroundColor(Color.WHITE);
		}
		if (itemList.get(position).getTextSize() != null) {
			if (itemList.get(position).getTextSize().endsWith("small")) {
				holder.tvName.setTextAppearance(ac,
						android.R.style.TextAppearance_Small);
			} else if (itemList.get(position).getTextSize().endsWith("medium")) {
				holder.tvName.setTextAppearance(ac,
						android.R.style.TextAppearance_Medium);
			} else {
				holder.tvName.setTextAppearance(ac,
						android.R.style.TextAppearance_Large);
			}
		}else{
//			holder.tvName.setTextAppearance(ac,
//					android.R.style.TextAppearance_Medium);
		}

		holder.tvName.setText(itemList.get(position).getName());

		holder.tvPrice.setText(itemList.get(position).getSelling_price_1());
		try {

			double tempGST = Double.parseDouble(holder.tvPrice.getText()
					.toString());

			String tempGSTstr = String.format("%.2f", tempGST);
			holder.tvPrice.setText(tempGSTstr);
		} catch (Exception e) {
			String strtemp = holder.tvPrice.getText().toString() + ".00";
			double tempGST = Double.parseDouble(strtemp);

			String tempGSTstr = String.format("%.2f", tempGST);
			holder.tvPrice.setText(tempGSTstr);
		} finally {
		}

		holder.rlBgIt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (position == 0) {
					((CustomFragmentActivity) ac).onBackPressed();
				} else {
					MainActivity.btnDisCountValue.setText("0");
					if (Utilities.getGlobalVariable(ac).isPOC == true) {
						ListOrderModel md = new ListOrderModel();
						if (Items.num != "") {
							md.setQualyti(Items.num);
						} else {
							md.setQualyti("1");
						}

						md.setAmount("0");
						md.setDiscount("0");
						md.setItemCode(itemList.get(position).getItem_code());
						md.setItemName(itemList.get(position).getName()
								+ "(FOC)");
						md.setPrice2(itemList.get(position)
								.getSelling_price_2());
						md.setSpecialPrice(itemList.get(position)
								.getSpecial_price());
						md.setUnitPrice("0.00");
						PosApp.listOrderData.add(md);

						MainActivity main = new MainActivity();
						main.notifidataOrderList();
						// Items.num = "";
						MainActivity.qly = -1;
						Utilities.getGlobalVariable(ac).isPOC = false;

					} else if (Utilities.getGlobalVariable(ac).isRefund == true) {
						ListOrderModel md = new ListOrderModel();
						String amount;
						amount = String.valueOf(Double.parseDouble(itemList
								.get(position).getSelling_price_1()) * -1);
						md.setQualyti("-1");
						// DecimalFormat df = new DecimalFormat("####0.00");
						// String amount1 = df.format(amount);
						amount1 = Double.valueOf(amount);
						amount1 = Math.round(amount1);
						md.setAmount(amount1 + "");
						md.setDiscount("0.00");
						md.setItemCode(itemList.get(position).getItem_code());
						md.setItemName(itemList.get(position).getName());
						md.setPrice2(itemList.get(position)
								.getSelling_price_2());
						md.setSpecialPrice(itemList.get(position)
								.getSpecial_price());
						md.setUnitPrice(itemList.get(position)
								.getSelling_price_1());

						PosApp.listOrderData.add(md);

						MainActivity main = new MainActivity();
						main.notifidataOrderList();
						// Items.num = "";
						MainActivity.qly = -1;
						Utilities.getGlobalVariable(ac).isRefund = false;

					} else {
						ListOrderModel md = new ListOrderModel();
						String amount;
						if (Items.num != "") {
							amount = String.valueOf(Double.parseDouble(itemList
									.get(position).getSelling_price_1())
									* Double.parseDouble(Items.num));
							md.setQualyti(Items.num);
						} else {
							amount = String.valueOf(Double.parseDouble(itemList
									.get(position).getSelling_price_1()) * 1);
							md.setQualyti("1");
						}
						DecimalFormat df = new DecimalFormat("0.00");
						amount1 = Double.valueOf(amount);
						md.setAmount(df.format(amount1) + "");
						md.setDiscount("0.00");
						md.setItemCode(itemList.get(position).getItem_code());
						md.setItemName(itemList.get(position).getName());
						md.setPrice2(itemList.get(position)
								.getSelling_price_2());
						md.setSpecialPrice(itemList.get(position)
								.getSpecial_price());
						md.setUnitPrice(itemList.get(position)
								.getSelling_price_1());

						PosApp.listOrderData.add(md);

						MainActivity main = new MainActivity();
						main.notifidataOrderList();
						// Items.num = "";
						MainActivity.qly = -1;
					}
					try {
						int baudrate = 9600;
						int databits = 8;
						int parity = 0;
						int stopbits = 1;
						int flowctl = 0;
						String path = "/dev/ttymxc2";
						mSerialPort_V = new SerialPort(new File(path),
								baudrate, databits, parity, stopbits, flowctl);
						mOutputStream_V = mSerialPort_V.getOutputStream();
						mOutputStream_V.write(0x1b);
						mOutputStream_V.write(0x40);

						String itemName = itemList.get(position).getName()
								.toString();
						String spaceName = "";
						if (itemName.length() > 20) {
							itemName = itemName.substring(0, 20);

						} else {
							int tempvar = 20 - itemName.length();
							for (int i = 0; i < tempvar; i++) {
								spaceName += " ";

							}

						}
						int changequantity;
						double amount;
						mOutputStream_V.write(itemName.getBytes());
						mOutputStream_V.write(spaceName.getBytes());
						String space = "      ";
						// mOutputStream_V.write(space.getBytes());
						if (Items.num != "") {
							changequantity = Integer.parseInt(Items.num);
							amount = Double.parseDouble(itemList.get(position)
									.getSelling_price_1())
									* Double.parseDouble(Items.num);
						} else {
							changequantity = Integer.parseInt("1");
							amount = Double.parseDouble(itemList.get(position)
									.getSelling_price_1())
									* Double.parseDouble("1");
						}

						int len1 = itemList.get(position).getSelling_price_1()
								.toString().length();
						String strtemp = "" + amount;
						int len2 = strtemp.length();
						double spaceneed = 14 - (len1 + len2);
						// mOutputStream_V.write(space.getBytes());
						for (int i = 0; i < spaceneed; i++) {
							space += " ";
						}
						DecimalFormat df = new DecimalFormat("0.00");
						String am = df.format(amount1);
						String quantity = "" + changequantity;
						String amountc = "" + am;
						mOutputStream_V.write(quantity.getBytes());
						mOutputStream_V.write(space.getBytes());
						mOutputStream_V.write(amountc.getBytes());

						mOutputStream_V.write('\r');
						mOutputStream_V.write('\n');

						mApplication.closeSerialPort();
					} catch (Exception e) {
						// Toast.makeText(MainActivity.this, e.toString(),
						// Toast.LENGTH_LONG).show();
						// throw new RuntimeException(e);
					}
				}
				Items.num = "";
				MainActivity ac = new MainActivity();
				ac.scrollMyListViewToBottom();
			}
		});
		holder.rlBgIt1.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				showDialogOK("You choose " + itemList.get(position).getName(),
						position);
				return false;
			}
		});

		return convertView;
	}

	public void showDialogOK(final String message, final int pos) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ac);
		builder.setMessage(message)
				.setCancelable(false)
				.setPositiveButton("InActive",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								ItemsDataSource a = new ItemsDataSource(ac, ac);
								String s = a.DelelteItem(itemList.get(pos)
										.getItemID());

								((CustomFragmentActivity) ac).replaceFragment(
										ConstantValue.ITEMS, false);

							}
						});
		builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				DialogItems ditem = new DialogItems();
				ditem.showDialogAddItem(ac, ac, true, itemList.get(pos)
						.getItemID());
				dialog.dismiss();

			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					}
				});
		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.setCanceledOnTouchOutside(false);
		alert.show();
	}

}
