package com.pos.ui;

import com.pos.CustomFragmentActivity;
import com.pos.MainActivity;
import com.pos.PosApp;
import com.pos.R;
import com.pos.common.Utilities;
import com.pos.db.BillDataSource;
import com.pos.db.CompanyDataSource;
import com.pos.db.ItemsDataSource;
import com.pos.db.MySQLiteHelper;
import com.pos.db.SaleReportDataSource;
import com.pos.libs.CalculationUtils;
import com.pos.libs.ComDDUtilities;
import com.pos.model.ListOrderModel;
import com.pos.print.PrinterFunctions;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialogEditItems implements OnClickListener {
	private Dialog dialogEditItems;
	private Boolean isShowDialog = false;
	private TextView tvItemDiscount;
	private TextView tvItemDiscount2;
	private TextView tvEditsale;
	private TextView tvPrice2;
	private TextView tvSpecialPrice;
	private TextView tvDeleteItem;
	private LinearLayout llAll;
	private Activity ac;
	private int pos;

	public void showDialogSelectImg(Activity _ac, int _pos) {
		ac = _ac;
		pos = _pos;
		// custom dialog
		if (!isShowDialog) {
			isShowDialog = true;
			// imgSet = _imgSet;
			dialogEditItems = new Dialog(ac);
			dialogEditItems.getWindow();
			dialogEditItems.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialogEditItems.setContentView(R.layout.dialog_edit_item);
			dialogEditItems.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			// dialog.setTitle("Title...");

			// set the custom dialog components - text, image and button

			this.tvItemDiscount = (TextView) dialogEditItems
					.findViewById(R.id.tvItemDiscount);
			this.tvEditsale = (TextView) dialogEditItems
					.findViewById(R.id.tvEditsale);
			this.tvPrice2 = (TextView) dialogEditItems
					.findViewById(R.id.tvPrice2);
			this.tvSpecialPrice = (TextView) dialogEditItems
					.findViewById(R.id.tvSpecialPrice);

			tvDeleteItem = (TextView) dialogEditItems
					.findViewById(R.id.tvDeleteItem);
			llAll = (LinearLayout) dialogEditItems.findViewById(R.id.llAll);
			((CustomFragmentActivity) ac).setWidth(llAll, 4);

			if (PosApp.listOrderData.get(pos).getPrice2().equals("")) {
				tvPrice2.setVisibility(View.GONE);
			} else {
				tvPrice2.setVisibility(View.VISIBLE);
			}
			if (PosApp.listOrderData.get(pos).getSpecialPrice().equals("")) {
				tvSpecialPrice.setVisibility(View.GONE);
			} else {
				tvSpecialPrice.setVisibility(View.VISIBLE);
			}
			tvItemDiscount.setOnClickListener(this);
			tvEditsale.setOnClickListener(this);
			tvPrice2.setOnClickListener(this);
			tvSpecialPrice.setOnClickListener(this);
			tvDeleteItem.setOnClickListener(this);
			dialogEditItems.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					isShowDialog = false;
				}
			});

			dialogEditItems.show();
		}

	}

	public void showDialogSelectImg2(Activity _ac, String recep, String saleid) {
		ac = _ac;
		CompanyDataSource b = new CompanyDataSource(ac, ac);
		// custom dialog
		if (!isShowDialog) {
			isShowDialog = true;
			// imgSet = _imgSet;
			dialogEditItems = new Dialog(ac);
			dialogEditItems.getWindow();
			dialogEditItems.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialogEditItems.setContentView(R.layout.dialog_edit_item2);
			dialogEditItems.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			// dialog.setTitle("Title...");

			// set the custom dialog components - text, image and button

			this.tvItemDiscount = (TextView) dialogEditItems
					.findViewById(R.id.tvItemDiscount);
			this.tvItemDiscount2 = (TextView) dialogEditItems
					.findViewById(R.id.tvItemDiscount2);
			String address;
			String companyName;
			String phone;
			String Email;
			String web;
			String Gst;
			String fax;
			String companyNo;
			if (b.loadCName().equals("")) {
				companyName = "";
			} else {
				companyName = b.loadCName() + "\r\n";
			}
			if (b.loadCAddress().equals("")) {
				address = "";
				address.replace(" ", "");
			} else {
				address = "" + b.loadCAddress() + "\r\n";
			}
			if (b.loadCPhone().equals("")) {
				phone = "";
			} else {
				phone = "TEL: " + b.loadCPhone() + "\r\n";
			}
			if (b.loadCEmail().equals("")) {
				Email = "";
			} else {
				Email = "" + b.loadCEmail() + "\r\n";
			}
			if (b.loadCWebsite().equals("")) {
				web = "";
			} else {
				web = "" + b.loadCWebsite() + "\r\n";
			}
			if (b.loadGST().equals("")) {
				Gst = "";
			} else {
				Gst = "" + b.loadGST() + "\r\n";
			}
			if (b.loadFAX().equals("")) {
				fax = "";
			} else {
				fax = "" + b.loadFAX() + "\r\n";
			}
			if (b.loadCompanyNo().equals("")) {
				companyNo = "";
			} else {
				companyNo = "" + "COMPANY NO: " + b.loadCompanyNo() + "\r\n";
			}
			SaleReportDataSource a = new SaleReportDataSource(ac, ac);
			String HTMLCom = "<h1>" + companyName + "</h1>" + address + "<br>"
					+ phone + "<br>" + Email + "<br>" + web + "<br>" + Gst
					+ "<br>" + fax + "<br>" + companyNo;
			HTMLCom += "<br>Receipt No.\t\t\t\t\t\t\t\t:\t" + recep
					+ "\t\t\t\t\t\t\t\t\t\t\t";
			HTMLCom += "<br>You are served by \t\t\t\t\t:\t "
					+ a.loadUserName() + "\t\t\t\t\t\t\t\t\t\t\t\t\t";
			HTMLCom += "<br>-----------------------------------------------------------------";
			HTMLCom += "<br>Items\t\t\t\t |   \tQty\t   |   \tPrice\t   |   \tDiscount\t   |   \tAmount\t  ";
			HTMLCom += "<br>-----------------------------------------------------------------";
			String HTMLCom2 = "";
			int i = 0;
			Cursor c = null;
			MySQLiteHelper dbHelper = new MySQLiteHelper(ac);
			try {
				if (dbHelper != null && dbHelper.getDb() != null) {
					String query = "SELECT * FROM sale_details where SaleID='"
							+ saleid + "'";

					c = dbHelper.getDb().rawQuery(query, null);

					if (c != null && c.getCount() > 0) {

						c.moveToFirst();
						do {
							try {
								Double a1 = Double.parseDouble(c.getString(5)
										.toString());
								Double b1 = Double.parseDouble(c.getString(6)
										.toString());

								String stra = String.format("%.2f", a1);
								String strb = String.format("%.2f", b1);

								HTMLCom2 += c.getString(2).toString() + "<br>"

								+ "\t\t\t\t\t\t"
										+ RedoPrice(c.getString(3).toString())
										+ "\t" + "$"
										+ RedoPrice(c.getString(4).toString())
										+ "\t" + "$" + RedoPrice(stra)
										+ "\t\t\t" + "$" + RedoPrice(strb)
										+ "<br>";
								i++;
							} catch (Exception e) {
							}
						} while (c.moveToNext());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			} finally {
				if (c != null) {
					c.close();
					c = null;
				}
			}
			HTMLCom2 += "<br>-----------------------------------------------------------------";
			ItemsDataSource d = new ItemsDataSource(ac, ac);
			String svc = String.valueOf(CalculationUtils.calculateGST(
					Double.valueOf(MainActivity.btnSubTotalValue.getText().toString()),
					Utilities.getGlobalVariable(ac).GST) + "");

			HTMLCom2 += ""

					+ "Subtotal  : \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"
					+ "$"
					+ d.checkSubtotal(saleid)
					+ "<br>Discount :  \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"
					+ "$"
					+ d.checkdiscount(saleid)
					+ "<br>"
					+ "Inc. 11% Svc Charge    : \t\t\t\t\t\t\t\t\t\t\t\t\t"
					+ "$"
					+ svc
					+ "<br>"
					+ "TOTAL     : \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"
					+ "$"
					+ d.checkTotalamount(saleid)
					+ "<br>"
					+ "-----------------------------------------------------------------<br>"
					+ " \t\t\t\t\t\t\t\t\t\t\t                          Payment Detail   \t<br>                   "
					+ "-----------------------------------------------------------------<br><br>";
			BillDataSource ab = new BillDataSource(ac, ac);
			String str = ab.loadprinttype(recep);
			if (str.equals("1") || str.equals("2")) {
				HTMLCom2 += d.loadPaymentNettRe1(saleid)
						+ "<br>"
						+ "Change   : \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t $0.00 <br>";
			} else {
				HTMLCom2 += d.loadPaymentRe1(saleid) + "<br>";
			}

			tvItemDiscount2.setText(Html.fromHtml(HTMLCom2));
			tvItemDiscount.setText(Html.fromHtml(HTMLCom));
			dialogEditItems.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					isShowDialog = false;
				}
			});

			dialogEditItems.show();
		}

	}

	public static String RedoPrice(String s) {
		int leng = s.length();
		int lengvar = 4 - leng;
		for (int i = 0; i < lengvar; i++) {
			s += "\t";
		}
		return s;
	}

	@Override
	public void onClick(View v) {
		if (v == tvItemDiscount) {
			DialogItemDiscount a = new DialogItemDiscount();
			a.showDialogItemDiscount(ac, ac, pos);
			dialogEditItems.dismiss();

		}
		if (v == tvEditsale) {
			DialogEditSale a = new DialogEditSale();
			a.showDialogEditSale(ac, ac, pos);
			dialogEditItems.dismiss();

		}
		if (v == tvPrice2) {
			ListOrderModel md = new ListOrderModel();
			md.setDiscount(PosApp.listOrderData.get(pos).getDiscount());
			md.setItemCode(PosApp.listOrderData.get(pos).getItemCode());
			md.setItemName(PosApp.listOrderData.get(pos).getItemName());
			md.setUnitPrice(PosApp.listOrderData.get(pos).getPrice2());
			md.setQualyti(PosApp.listOrderData.get(pos).getQualyti());
			md.setPrice2(PosApp.listOrderData.get(pos).getPrice2());
			md.setSpecialPrice(PosApp.listOrderData.get(pos).getSpecialPrice());
			md.setAmount(String.valueOf(Double.parseDouble(PosApp.listOrderData
					.get(pos).getPrice2())
					* Double.parseDouble(md.getQualyti())));
			PosApp.listOrderData.set(pos, md);
			MainActivity main = new MainActivity();
			main.notifidataOrderList();
			dialogEditItems.dismiss();

		}
		if (v == tvSpecialPrice) {
			ListOrderModel md = new ListOrderModel();
			md.setDiscount(PosApp.listOrderData.get(pos).getDiscount());
			md.setItemCode(PosApp.listOrderData.get(pos).getItemCode());
			md.setItemName(PosApp.listOrderData.get(pos).getItemName());
			md.setUnitPrice(PosApp.listOrderData.get(pos).getSpecialPrice());
			md.setQualyti(PosApp.listOrderData.get(pos).getQualyti());
			md.setPrice2(PosApp.listOrderData.get(pos).getPrice2());
			md.setSpecialPrice(PosApp.listOrderData.get(pos).getSpecialPrice());
			md.setAmount(String.valueOf(Double.parseDouble(PosApp.listOrderData
					.get(pos).getSpecialPrice())
					* Double.parseDouble(md.getQualyti())));
			PosApp.listOrderData.set(pos, md);
			MainActivity main = new MainActivity();
			main.notifidataOrderList();
			dialogEditItems.dismiss();

		}
		if (v == tvDeleteItem) {
			PosApp.listOrderData.remove(pos);
			MainActivity main = new MainActivity();
			main.notifidataOrderList();
			dialogEditItems.dismiss();
			if (PosApp.listOrderData.size() == 0) {
				ComDDUtilities.showWelcome();
			}
		}

	}
}
