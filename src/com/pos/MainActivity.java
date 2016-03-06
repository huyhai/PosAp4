package com.pos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.pos.R;
import com.pos.adapter.ListOrderAdapter;
import com.pos.adapter.MainCateAdapter;
import com.pos.common.ConstantValue;
import com.pos.common.Utilities;
import com.pos.db.CompanyDataSource;
import com.pos.db.ItemsDataSource;
import com.pos.db.MainCateDataSource;
import com.pos.db.PayDataSource;
import com.pos.db.RefundDataSource;
import com.pos.db.SaleReportDataSource;
import com.pos.db.ScanerDataSource;
import com.pos.db.SearchBillDataSource;
import com.pos.db.SettingDataSource;
import com.pos.db.UnpaidDataSource;
import com.pos.interfaceapp.onImageSet;
import com.pos.libs.CalculationUtils;
import com.pos.libs.ComDDUtilities;
import com.pos.libs.SessionManager;
import com.pos.libs.ViewUtils;
import com.pos.model.ItemsModel;
import com.pos.model.ListOrderModel;
import com.pos.model.MainCateModel;
import com.pos.model.PayModel;
import com.pos.model.SettingModel;
import com.pos.model.UnpaidModel;
import com.pos.print.PrinterFunctions;
import com.pos.ui.DialogBillDiscount;
import com.pos.ui.DialogCustomItem;
import com.pos.ui.DialogEditItems;
import com.pos.ui.DialogGroup;
import com.pos.ui.DialogInventoryCheck;
import com.pos.ui.DialogInventoryReport;
import com.pos.ui.DialogItemDiscount;
import com.pos.ui.DialogItems;
import com.pos.ui.DialogNumPrint;
import com.pos.ui.DialogPayment;
import com.pos.ui.DialogRefund;
import com.pos.ui.DialogRefund2;
import com.pos.ui.DialogSaleReport;
import com.pos.ui.DialogSearchBill;
import com.pos.ui.DialogNote;
import com.pos.ui.DialogStockIn;
import com.pos.ui.DialogTicketReport;
import com.pos.ui.Header;
import com.pos.ui.Items;
import com.pos.ui.LoginDialog;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.cashdrawer.CashDrawer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.serialport.SerialPort;
import android.serialport.SerialPortFinder;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends CustomFragmentActivity implements
		OnClickListener {
	private RelativeLayout pdBar;
	private RelativeLayout rlTop;
	private RelativeLayout rlNumber;
	private RelativeLayout rlNumberButon;
	private RelativeLayout rlNumber2;
	private RelativeLayout rlLeft;
	private Button btnCustom;
	private Button btnSearchBill;
	private Button btnHoldBill;
	private Button btnChangeUser;
	private Button btnNote;
	private Button btnNum0;
	private Button btnNum1;
	private Button btnNum2;
	private Button btnNum3;
	private Button btnNum4;
	private Button btnNum5;
	private Button btnNum6;
	private Button btnNum7;
	private Button btnNum8;
	private Button btnNum9;
	private Button btnScan;
	private Button btnReprint;
	private Button btnVisa;
	private Button btnMasterCard;
	private Button btnNet;
	private Button btnDinner;
	private Button btnJBC;
	private Button btnArmet;
	private RelativeLayout rlPayment;
	private RelativeLayout rlOpen;
	private Button btnNettSale;
	private Button btnOpenCash;

	private Button btnPayment;
	private Button btnSearchItem;
	private static EditText edSearch;
	private RelativeLayout rlCard1;
	private RelativeLayout rlCard2;
	private RelativeLayout rlBottom;
	private RelativeLayout rlListLeft;
	private RelativeLayout rlListRight;
	public static ListView lvOrder;
	private RelativeLayout rlBelowList;
	private RelativeLayout rlgvItesm;
	private Button btnDelete;
	private Button btnManagerItem;
	private Button btnStockInItem;
	private Button btnInventoryCheck;
	private Button btnViewReport;
	private Button btnPrinter;
	private Button btnRefund;
	private Button btnPOC;
	private Button btnBillDiscount;
	// private Button btnEditSale;
	private Button btnUnpaid;
	private Button btnCancelBill;
	public static String endshift = "false";
	public static String note = "";
	public static Boolean refund = false;
	public static String searchstring = "";
	public static Button btnSubTotalValue;
	private Button btnSubTotal;
	public static Button btnGSTValue;
	private Button btnGST;
	public static Button btnDisCountValue;
	private Button btnDisCount;
	private RelativeLayout rlTotalAmout;

	private RelativeLayout rlItemCode;
	private RelativeLayout rlItemName;
	private RelativeLayout rlUnitPrice;
	private RelativeLayout rlQty;
	private RelativeLayout rlDiscount;
	private RelativeLayout rlAmount;
	private LinearLayout rlAll;
	private Button btnPlus;
	private Button btnMinus;
	private static ListOrderAdapter adapter;
	public static TextView tvTotalAmount;
	public static int qly = -1;
	private static SessionManager ss;
	private static int returnla = 0;
	private Application mApplication;
	private SerialPort mSerialPort_V;
	private OutputStream mOutputStream_V;
	public static CashDrawer mCashDrawer = null;
	private static String creaditType;
	public static String modepay;
	private static Activity activity;
	private TextView btnPrintNoSave;

	// public static ArrayList<SettingModel> listThongtin = new
	// ArrayList<SettingModel>();

	public MainActivity() {
		super(R.string.app_name);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		this.setContentView(R.layout.content_frame);
		activity = MainActivity.this;
		SaleReportDataSource a1 = new SaleReportDataSource(activity, activity);
		String str = a1.CheckEndshift();
		if (str.equals("ok")) {
			LoginDialog.isLogAgain = false;
			LoginDialog a = new LoginDialog();
			a.showDialogSelectImg(activity);
		} else {
			LoginDialog.isLogAgain = true;
			LoginDialog a = new LoginDialog();
			a.showDialogSelectImg2(activity);
		}
		mCashDrawer = new CashDrawer();
		ComDDUtilities.showWelcome();
		ss = new SessionManager(activity);
		this.pdBar = (RelativeLayout) this.findViewById(R.id.pdBar);
		rlTop = (RelativeLayout) this.findViewById(R.id.rlTop);
		rlNumber = (RelativeLayout) this.findViewById(R.id.rlNumber);
		rlNumberButon = (RelativeLayout) this.findViewById(R.id.rlNumberButon);
		rlNumber2 = (RelativeLayout) this.findViewById(R.id.rlNumber2);
		// rlRight = (RelativeLayout) this.findViewById(R.id.rlRight);
		rlLeft = (RelativeLayout) this.findViewById(R.id.rlLeft);
		btnReprint = (Button) this.findViewById(R.id.btnEditSale);
		btnCustom = (Button) this.findViewById(R.id.btnCustom);
		btnSearchBill = (Button) this.findViewById(R.id.btnSearchBill);
		btnHoldBill = (Button) this.findViewById(R.id.btnHoldBill);
		btnChangeUser = (Button) this.findViewById(R.id.btnChangeUser);
		btnNote = (Button) this.findViewById(R.id.btnNote);
		btnNum0 = (Button) this.findViewById(R.id.btnNum0);
		btnNum1 = (Button) this.findViewById(R.id.btnNum1);
		btnNum2 = (Button) this.findViewById(R.id.btnNum2);
		btnNum3 = (Button) this.findViewById(R.id.btnNum3);
		btnNum4 = (Button) this.findViewById(R.id.btnNum4);
		btnNum5 = (Button) this.findViewById(R.id.btnNum5);
		btnNum6 = (Button) this.findViewById(R.id.btnNum6);
		btnNum7 = (Button) this.findViewById(R.id.btnNum7);
		btnNum8 = (Button) this.findViewById(R.id.btnNum8);
		btnNum9 = (Button) this.findViewById(R.id.btnNum9);
		btnScan = (Button) this.findViewById(R.id.btnScan);
		btnDelete = (Button) this.findViewById(R.id.btnDelete);

		btnVisa = (Button) this.findViewById(R.id.btnVisa);
		btnMasterCard = (Button) this.findViewById(R.id.btnMasterCard);
		btnNet = (Button) this.findViewById(R.id.btnNet);
		btnJBC = (Button) this.findViewById(R.id.btnJBC);
		btnArmet = (Button) this.findViewById(R.id.btnArmet);
		rlPayment = (RelativeLayout) this.findViewById(R.id.rlPayment);
		rlOpen = (RelativeLayout) this.findViewById(R.id.rlOpen);
		btnNettSale = (Button) this.findViewById(R.id.btnNettSale);
		btnOpenCash = (Button) this.findViewById(R.id.btnOpenCash);
		btnPayment = (Button) this.findViewById(R.id.btnPayment);
		btnSearchItem = (Button) this.findViewById(R.id.btnSearchItem);
		edSearch = (EditText) this.findViewById(R.id.edSearch);
		rlCard1 = (RelativeLayout) this.findViewById(R.id.rlCard1);
		rlCard2 = (RelativeLayout) this.findViewById(R.id.rlCard2);
		rlBottom = (RelativeLayout) this.findViewById(R.id.rlBottom);
		btnDinner = (Button) this.findViewById(R.id.btnDinner);
		rlListLeft = (RelativeLayout) this.findViewById(R.id.rlListLeft);
		rlListRight = (RelativeLayout) this.findViewById(R.id.rlListRight);
		rlBelowList = (RelativeLayout) this.findViewById(R.id.rlBelowList);
		rlgvItesm = (RelativeLayout) this.findViewById(R.id.rlgvItesm);
		lvOrder = (ListView) this.findViewById(R.id.lvOrder);
		btnManagerItem = (Button) this.findViewById(R.id.btnManagerItem);
		btnStockInItem = (Button) this.findViewById(R.id.btnStockInItem);
		btnInventoryCheck = (Button) this.findViewById(R.id.btnInventoryCheck);
		btnViewReport = (Button) this.findViewById(R.id.btnViewReport);
		btnPrinter = (Button) this.findViewById(R.id.btnPrinter);

		btnRefund = (Button) this.findViewById(R.id.btnRefund);
		btnPOC = (Button) this.findViewById(R.id.btnPOC);
		btnBillDiscount = (Button) this.findViewById(R.id.btnBillDiscount);
		// btnEditSale = (Button) this.findViewById(R.id.btnEditSale);
		btnUnpaid = (Button) this.findViewById(R.id.btnUnpaid);
		btnCancelBill = (Button) this.findViewById(R.id.btnCancelBill);

		btnSubTotalValue = (Button) this.findViewById(R.id.btnSubTotalValue);
		btnSubTotal = (Button) this.findViewById(R.id.btnSubTotal);

		btnGSTValue = (Button) this.findViewById(R.id.btnGSTValue);
		btnGST = (Button) this.findViewById(R.id.btnGST);
		btnDisCountValue = (Button) this.findViewById(R.id.btnDisCountValue);
		btnDisCount = (Button) this.findViewById(R.id.btnDisCount);
		rlTotalAmout = (RelativeLayout) this.findViewById(R.id.rlTotalAmout);
		rlBelowList.requestFocus();

		rlAmount = (RelativeLayout) findViewById(R.id.rlAmount);
		rlDiscount = (RelativeLayout) findViewById(R.id.rlDiscount);
		rlItemCode = (RelativeLayout) findViewById(R.id.rlItemCode);
		rlItemName = (RelativeLayout) findViewById(R.id.rlItemName);
		rlQty = (RelativeLayout) findViewById(R.id.rlQty);
		rlUnitPrice = (RelativeLayout) findViewById(R.id.rlUnitPrice);
		rlAll = (LinearLayout) findViewById(R.id.rlAll);
		btnMinus = (Button) this.findViewById(R.id.btnMinus);
		btnPlus = (Button) this.findViewById(R.id.btnPlus);
		tvTotalAmount = (TextView) this.findViewById(R.id.tvTotalAmount);
		btnPrintNoSave = (Button) this.findViewById(R.id.btnPrintNoSave);

		initLayout();
		initData();

		if (!isOnActivityResult) {
			if (this.mListView.size() > 0) {
				this.startFragment(this.mListView.get(this.mListView.size() - 1));
			} else {
				this.startFragment(ConstantValue.HOME_FRAGMENT);

			}
		}

	}

	private void initData() {

		btnGSTValue.setText("0.00");
		btnReprint.setOnClickListener(this);
		btnNum0.setOnClickListener(this);
		btnNum1.setOnClickListener(this);
		btnNum2.setOnClickListener(this);
		btnNum3.setOnClickListener(this);
		btnNum4.setOnClickListener(this);
		btnNum5.setOnClickListener(this);
		btnNum6.setOnClickListener(this);
		btnNum7.setOnClickListener(this);
		btnNum8.setOnClickListener(this);
		btnNum9.setOnClickListener(this);
		btnPlus.setOnClickListener(this);
		btnMinus.setOnClickListener(this);
		btnPOC.setOnClickListener(this);
		btnCancelBill.setOnClickListener(this);
		btnBillDiscount.setOnClickListener(this);
		btnUnpaid.setOnClickListener(this);
		btnHoldBill.setOnClickListener(this);
		btnSearchBill.setOnClickListener(this);
		btnPayment.setOnClickListener(this);
		btnOpenCash.setOnClickListener(this);
		btnPrinter.setOnClickListener(this);
		btnNettSale.setOnClickListener(this);
		btnNote.setOnClickListener(this);
		btnRefund.setOnClickListener(this);
		btnVisa.setOnClickListener(this);
		btnDinner.setOnClickListener(this);
		btnMasterCard.setOnClickListener(this);
		btnNet.setOnClickListener(this);
		btnJBC.setOnClickListener(this);
		btnArmet.setOnClickListener(this);
		btnChangeUser.setOnClickListener(this);
		btnCustom.setOnClickListener(this);
		btnInventoryCheck.setOnClickListener(this);
		btnStockInItem.setOnClickListener(this);
		adapter = new ListOrderAdapter(activity, PosApp.listOrderData);
		SaleReportDataSource ac = new SaleReportDataSource(activity, activity);

		lvOrder.setAdapter(adapter);
		// lvOrder.setOnItemClickListener(this);
		lvOrder.setLongClickable(true);

		// lvOrder.setOnLongClickListener(new OnLongClickListener() {
		//
		// @Override
		// public boolean onLongClick(View v) {
		// DialogEditItems ab = new DialogEditItems();
		// ab.showDialogSelectImg(MainActivity.this);
		// return false;
		// }
		// });
		btnManagerItem.setOnClickListener(this);
		btnSearchItem.setOnClickListener(this);
		btnPrinter.setOnClickListener(this);
		btnViewReport.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnPrintNoSave.setOnClickListener(this);
		// edSearch.setText("Updated New Text");
		// edSearch.setText(1, "");
		// int position = edSearch.getText().length();
		// Editable editObj= edSearch.getText();
		// Selection.setSelection(editObj, position);

		// SettingDataSource data = new SettingDataSource(activity,
		// activity);
		// listThongtin = data.loadItems();

		edSearch.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// Toast.makeText(activity, "aaaaaa",Toast.LENGTH_SHORT).show();
				// Log.v("HAI", "aaaaaaaaaaaa");
				if (event.getAction() != KeyEvent.ACTION_DOWN) {
					if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
						String text = edSearch.getText().toString()
								.replace("\n", "");
						addList(text);

						return true;
					}
				}
				return false;
			}

		});

	}

	// private static ScanerDataSource scan = new ScanerDataSource(activity,
	// activity);
	private static ArrayList<ItemsModel> listScan = new ArrayList<ItemsModel>();

	private void addList(String text) {
		ScanerDataSource scan = new ScanerDataSource(activity, activity);
		listScan = scan.getitemBarcode(text);
		try {
			ListOrderModel md = new ListOrderModel();
			String amount;
			if (Items.num != "") {
				amount = String.valueOf(Double.parseDouble(listScan.get(0)
						.getSelling_price_1()) * Double.parseDouble(Items.num));
				md.setQualyti(Items.num);
			} else {
				amount = String.valueOf(Double.parseDouble(listScan.get(0)
						.getSelling_price_1()) * 1);
				md.setQualyti("1");
			}
			DecimalFormat df = new DecimalFormat("0.00");
			amount1 = Double.valueOf(amount);
			md.setAmount(df.format(amount1) + "");
			md.setDiscount("0.00");
			md.setItemCode(listScan.get(0).getItem_code());
			md.setItemName(listScan.get(0).getName());
			md.setPrice2(listScan.get(0).getSelling_price_2());
			md.setSpecialPrice(listScan.get(0).getSpecial_price());
			md.setUnitPrice(listScan.get(0).getSelling_price_1());

			PosApp.listOrderData.add(md);

			MainActivity main = new MainActivity();
			main.notifidataOrderList();
			// Items.num = "";
			MainActivity.qly = -1;

			try {
				int baudrate = 9600;
				int databits = 8;
				int parity = 0;
				int stopbits = 1;
				int flowctl = 0;
				String path = "/dev/ttymxc2";
				mSerialPort_V = new SerialPort(new File(path), baudrate,
						databits, parity, stopbits, flowctl);
				mOutputStream_V = mSerialPort_V.getOutputStream();
				mOutputStream_V.write(0x1b);
				mOutputStream_V.write(0x40);

				String itemName = listScan.get(0).getName().toString();
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
				// double amount;
				mOutputStream_V.write(itemName.getBytes());
				mOutputStream_V.write(spaceName.getBytes());
				String space = "      ";
				// mOutputStream_V.write(space.getBytes());
				if (Items.num != "") {
					changequantity = Integer.parseInt(Items.num);
					// amount = Double.parseDouble(listScan.get(0)
					// .getSelling_price_1())
					// * Double.parseDouble(Items.num);
				} else {
					changequantity = Integer.parseInt("1");
					// amount = Double.parseDouble(listScan.get(0)
					// .getSelling_price_1())
					// * Double.parseDouble("1");
				}

				int len1 = listScan.get(0).getSelling_price_1().toString()
						.length();
				String strtemp = "" + amount;
				int len2 = strtemp.length();
				double spaceneed = 14 - (len1 + len2);
				// mOutputStream_V.write(space.getBytes());
				for (int i = 0; i < spaceneed; i++) {
					space += " ";
				}
				// DecimalFormat df = new DecimalFormat("0.00");
				// String am = df.format(amount1);
				String quantity = "" + changequantity;
				String amountc = "" + amount1;
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

		} catch (Exception e) {
			Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show();
		}
		Items.num = "";
		edSearch.setText("");

	}

	private int currentPosition;

	public void notifidataOrderList() {

		adapter.setItemList(PosApp.listOrderData);
		// lvOrder.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		scrollMyListViewToBottom();
		if (!Utilities.getGlobalVariable(activity).isHoldPaid) {
			btnDisCountValue.setText("0");
		}

		btnSubTotalValue.setText(CalculationUtils
				.calculateSubAmout(PosApp.listOrderData) + "");
		btnGSTValue.setText(CalculationUtils.calculateGST(
				Double.valueOf(btnSubTotalValue.getText().toString()),
				Utilities.getGlobalVariable(activity).GST) + "");
		// tvTotalAmount.setText(Double.valueOf(btnSubTotalValue.getText()
		// .toString())
		// - Double.valueOf(btnDisCountValue.getText().toString())

		// + Double.valueOf(btnGSTValue.getText().toString()) + "");
		double temp1 = Double
				.parseDouble(btnSubTotalValue.getText().toString());
		double temp2 = Double.parseDouble(btnGSTValue.getText().toString());
		double total = temp1 + temp2;
		double tempsub = Double.parseDouble(btnSubTotalValue.getText()
				.toString());
		String tempsubstr = String.format("%.2f", tempsub);
		btnSubTotalValue.setText(tempsubstr);

		double tempGST = Double.parseDouble(btnGSTValue.getText().toString());
		String tempGSTstr = String.format("%.2f", tempGST);
		btnGSTValue.setText(tempGSTstr);

		double tempdc = Double.parseDouble(btnDisCountValue.getText()
				.toString());
		String tempdcstr = String.format("%.2f", tempdc);
		btnDisCountValue.setText(tempdcstr);
		total = total - tempdc;
		String stemp = String.format("%.2f", total);
		String stemp1 = stemp.substring(stemp.length() - 2, stemp.length());
		String stemp2 = stemp.substring(0, stemp.length() - 3);
		int i1 = -1;
		try {
			i1 = Integer.parseInt(stemp2);
		} catch (Exception e) {
			// TODO: handle exception
		}

		int i2 = -1;
		try {
			i2 = Integer.parseInt(stemp1);
		} catch (Exception e) {
			// TODO: handle exception
		}

		if ((i2 > 0) & (i2 <= 5)) {
			i2 = 5;
		}
		if ((i2 > 5) & (i2 <= 10)) {
			i2 = 10;
		}
		if ((i2 > 10) & (i2 <= 15)) {
			i2 = 15;
		}
		if ((i2 > 15) & (i2 <= 20)) {
			i2 = 20;
		}
		if ((i2 > 20) & (i2 <= 25)) {
			i2 = 25;
		}
		if ((i2 > 25) & (i2 <= 30)) {
			i2 = 30;
		}
		if ((i2 > 30) & (i2 <= 35)) {
			i2 = 35;
		}
		if ((i2 > 35) & (i2 <= 40)) {
			i2 = 40;
		}
		if ((i2 > 40) & (i2 <= 45)) {
			i2 = 45;
		}
		if ((i2 > 45) & (i2 <= 50)) {
			i2 = 50;
		}
		if ((i2 > 50) & (i2 <= 55)) {
			i2 = 55;
		}
		if ((i2 > 55) & (i2 <= 60)) {
			i2 = 60;
		}
		if ((i2 > 60) & (i2 <= 65)) {
			i2 = 65;
		}
		if ((i2 > 65) & (i2 <= 70)) {
			i2 = 70;
		}
		if ((i2 > 70) & (i2 <= 75)) {
			i2 = 75;
		}
		if ((i2 > 75) & (i2 <= 80)) {
			i2 = 80;
		}
		if ((i2 > 80) & (i2 <= 85)) {
			i2 = 85;
		}
		if ((i2 > 85) & (i2 <= 90)) {
			i2 = 90;
		}
		if ((i2 > 90) & (i2 <= 95)) {
			i2 = 95;
		}
		if ((i2 > 95) & (i2 <= 99)) {
			i1++;
			i2 = 0;
		}
		if (i2 == 0) {
			tvTotalAmount.setText("" + i1 + "." + i2 + "0");
		} else {
			tvTotalAmount.setText("" + i1 + "." + i2);
		}
	}

	public void notifidataOrderList2() {
		returnla = 1;
		currentPosition = lvOrder.getLastVisiblePosition();
		for (int i = 0; i < PosApp.listOrderData.size(); i++) {
			Double dtemp = Double.parseDouble(PosApp.listOrderData.get(i)
					.getUnitPrice().toString());
			if (dtemp > 0) {
				PosApp.listOrderData.remove(i);
			}
		}
		for (int i = 0; i < PosApp.listOrderData.size(); i++) {
			Double dtemp = Double.parseDouble(PosApp.listOrderData.get(i)
					.getAmount().toString());
			if (dtemp > 0) {
				PosApp.listOrderData.remove(i);
			}
		}
		adapter.setItemList(PosApp.listOrderData);
		// lvOrder.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		scrollMyListViewToBottom();
		if (!Utilities.getGlobalVariable(activity).isHoldPaid) {
			btnDisCountValue.setText("0");
		}

		btnSubTotalValue.setText(CalculationUtils
				.calculateSubAmout(PosApp.listOrderData) + "");
		btnGSTValue.setText("0");
		// tvTotalAmount.setText(Double.valueOf(btnSubTotalValue.getText()
		// .toString())
		// - Double.valueOf(btnDisCountValue.getText().toString())

		// + Double.valueOf(btnGSTValue.getText().toString()) + "");
		double temp1 = Double
				.parseDouble(btnSubTotalValue.getText().toString());
		double temp2 = Double.parseDouble(btnGSTValue.getText().toString());
		double total = temp1 + temp2;
		double tempsub = Double.parseDouble(btnSubTotalValue.getText()
				.toString());
		String tempsubstr = String.format("%.2f", tempsub);
		btnSubTotalValue.setText(tempsubstr);

		double tempGST = Double.parseDouble(btnGSTValue.getText().toString());
		String tempGSTstr = String.format("%.2f", tempGST);
		btnGSTValue.setText(tempGSTstr);

		double tempdc = Double.parseDouble(btnDisCountValue.getText()
				.toString());
		String tempdcstr = String.format("%.2f", tempdc);
		btnDisCountValue.setText(tempdcstr);

		total = total - tempdc;
		String stemp = String.format("%.2f", total);
		String stemp1 = stemp.substring(stemp.length() - 2, stemp.length());
		String stemp2 = stemp.substring(0, stemp.length() - 3);

		int i1 = Integer.parseInt(stemp2);
		int i2 = Integer.parseInt(stemp1);
		if ((i2 > 0) & (i2 <= 5)) {
			i2 = 5;
		}
		if ((i2 > 5) & (i2 <= 10)) {
			i2 = 10;
		}
		if ((i2 > 10) & (i2 <= 15)) {
			i2 = 15;
		}
		if ((i2 > 15) & (i2 <= 20)) {
			i2 = 20;
		}
		if ((i2 > 20) & (i2 <= 25)) {
			i2 = 25;
		}
		if ((i2 > 25) & (i2 <= 30)) {
			i2 = 30;
		}
		if ((i2 > 30) & (i2 <= 35)) {
			i2 = 35;
		}
		if ((i2 > 35) & (i2 <= 40)) {
			i2 = 40;
		}
		if ((i2 > 40) & (i2 <= 45)) {
			i2 = 45;
		}
		if ((i2 > 45) & (i2 <= 50)) {
			i2 = 50;
		}
		if ((i2 > 50) & (i2 <= 55)) {
			i2 = 55;
		}
		if ((i2 > 55) & (i2 <= 60)) {
			i2 = 60;
		}
		if ((i2 > 60) & (i2 <= 65)) {
			i2 = 65;
		}
		if ((i2 > 65) & (i2 <= 70)) {
			i2 = 70;
		}
		if ((i2 > 70) & (i2 <= 75)) {
			i2 = 75;
		}
		if ((i2 > 75) & (i2 <= 80)) {
			i2 = 80;
		}
		if ((i2 > 80) & (i2 <= 85)) {
			i2 = 85;
		}
		if ((i2 > 85) & (i2 <= 90)) {
			i2 = 90;
		}
		if ((i2 > 90) & (i2 <= 95)) {
			i2 = 95;
		}
		if ((i2 > 95) & (i2 <= 99)) {
			i1++;
			i2 = 0;
		}
		if (i2 == 0) {
			tvTotalAmount.setText("" + i1 + "." + i2 + "0");
		} else {
			tvTotalAmount.setText("" + i1 + "." + i2);
		}
	}

	public void notifidataSubAndTotal(String dis) {
		Double realPrice;
		currentPosition = lvOrder.getLastVisiblePosition();
		adapter.setItemList(PosApp.listOrderData);
		// lvOrder.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		scrollMyListViewToBottom();
		btnDisCountValue.setText(dis);
		realPrice = Double.parseDouble(btnSubTotalValue.getText().toString())
				- Double.parseDouble(dis);
		btnSubTotalValue.setText(CalculationUtils
				.calculateSubAmout(PosApp.listOrderData) + "");

		// tvTotalAmount.setText(realPrice
		// + CalculationUtils.calculateGST(realPrice,
		// Utilities.getGlobalVariable(MainActivity.this).GST)
		// + "");
		double temp1 = Double
				.parseDouble(btnSubTotalValue.getText().toString());
		double temp2 = Double.parseDouble(btnGSTValue.getText().toString());
		double total = temp1 + temp2;
		double tempsub = Double.parseDouble(btnSubTotalValue.getText()
				.toString());
		String tempsubstr = String.format("%.2f", tempsub);
		btnSubTotalValue.setText(tempsubstr);

		double tempGST = Double.parseDouble(btnGSTValue.getText().toString());
		String tempGSTstr = String.format("%.2f", tempGST);
		btnGSTValue.setText(tempGSTstr);

		double tempdc = Double.parseDouble(btnDisCountValue.getText()
				.toString());
		String tempdcstr = String.format("%.2f", tempdc);
		btnDisCountValue.setText(tempdcstr);
		total = total - tempdc;
		String stemp = String.format("%.2f", total);
		String stemp1 = stemp.substring(stemp.length() - 2, stemp.length());
		String stemp2 = stemp.substring(0, stemp.length() - 3);

		int i1 = Integer.parseInt(stemp2);
		int i2 = Integer.parseInt(stemp1);
		if ((i2 > 0) & (i2 <= 5)) {
			i2 = 5;
		}
		if ((i2 > 5) & (i2 <= 10)) {
			i2 = 10;
		}
		if ((i2 > 10) & (i2 <= 15)) {
			i2 = 15;
		}
		if ((i2 > 15) & (i2 <= 20)) {
			i2 = 20;
		}
		if ((i2 > 20) & (i2 <= 25)) {
			i2 = 25;
		}
		if ((i2 > 25) & (i2 <= 30)) {
			i2 = 30;
		}
		if ((i2 > 30) & (i2 <= 35)) {
			i2 = 35;
		}
		if ((i2 > 35) & (i2 <= 40)) {
			i2 = 40;
		}
		if ((i2 > 40) & (i2 <= 45)) {
			i2 = 45;
		}
		if ((i2 > 45) & (i2 <= 50)) {
			i2 = 50;
		}
		if ((i2 > 50) & (i2 <= 55)) {
			i2 = 55;
		}
		if ((i2 > 55) & (i2 <= 60)) {
			i2 = 60;
		}
		if ((i2 > 60) & (i2 <= 65)) {
			i2 = 65;
		}
		if ((i2 > 65) & (i2 <= 70)) {
			i2 = 70;
		}
		if ((i2 > 70) & (i2 <= 75)) {
			i2 = 75;
		}
		if ((i2 > 75) & (i2 <= 80)) {
			i2 = 80;
		}
		if ((i2 > 80) & (i2 <= 85)) {
			i2 = 85;
		}
		if ((i2 > 85) & (i2 <= 90)) {
			i2 = 90;
		}
		if ((i2 > 90) & (i2 <= 95)) {
			i2 = 95;
		}
		if ((i2 > 95) & (i2 <= 99)) {
			i1++;
		}
		if (i2 == 0) {
			tvTotalAmount.setText("" + i1 + "." + i2 + "0");
		} else {
			tvTotalAmount.setText("" + i1 + "." + i2);
		}
	}

	public void scrollMyListViewToBottom() {
		currentPosition = lvOrder.getLastVisiblePosition();
		lvOrder.post(new Runnable() {
			@Override
			public void run() {
				lvOrder.setSelectionFromTop(currentPosition + 1, 0);
			}
		});
	}

	private void initLayout() {
		setWidthHeight(rlTop, 1, 9);
		setWidthHeight(rlNumber, 1, 8);
		setWidthHeight(rlNumberButon, 2.3, LayoutParams.FILL_PARENT);
		setWidthHeight(rlNumber2, 1, 7);
		setWidthHeight(rlLeft, 2.3, 8.5);
		// setWidthHeight(rlRight,1, 1);
		setWidthHeight(btnCustom, 18, 18);
		setWidthHeight(btnSearchBill, 18, 18);
		setWidthHeight(btnHoldBill, 18, 18);
		setWidthHeight(btnChangeUser, 18, 18);
		setWidthHeight(btnNote, 18, 18);
		setWidthHeight(btnPrintNoSave, 18, 18);

		int wbtn = 23;
		int hbtn = 15;
		setWidthHeight(btnNum0, wbtn, hbtn);
		setWidthHeight(btnNum1, wbtn, hbtn);
		setWidthHeight(btnNum2, wbtn, hbtn);
		setWidthHeight(btnNum3, wbtn, hbtn);
		setWidthHeight(btnNum4, wbtn, hbtn);
		setWidthHeight(btnNum5, wbtn, hbtn);
		setWidthHeight(btnNum6, wbtn, hbtn);
		setWidthHeight(btnNum7, wbtn, hbtn);
		setWidthHeight(btnNum8, wbtn, hbtn);
		setWidthHeight(btnNum9, wbtn, hbtn);
		setWidthHeight(btnScan, wbtn - 2, 18);
		setWidthHeight(btnDelete, wbtn, hbtn);

		int wbtbtnVisan = 19;
		int hbtbtnVisan = 19;
		setWidthHeight(btnVisa, wbtbtnVisan, hbtbtnVisan);
		setWidthHeight(btnDinner, wbtbtnVisan, hbtbtnVisan);
		setWidthHeight(btnMasterCard, wbtbtnVisan, hbtbtnVisan);
		setWidthHeight(btnNet, wbtbtnVisan, hbtbtnVisan);
		setWidthHeight(btnJBC, wbtbtnVisan, hbtbtnVisan);
		setWidthHeight(btnArmet, wbtbtnVisan, hbtbtnVisan);

		setWidthHeight(btnRefund, wbtbtnVisan, hbtbtnVisan);
		setWidthHeight(btnPOC, wbtbtnVisan, hbtbtnVisan);
		setWidthHeight(btnBillDiscount, wbtbtnVisan, hbtbtnVisan);
		setWidthHeight(btnReprint, wbtbtnVisan, hbtbtnVisan);
		setWidthHeight(btnUnpaid, wbtbtnVisan, hbtbtnVisan);
		setWidthHeight(btnCancelBill, wbtbtnVisan, hbtbtnVisan);
		setWidthHeight(btnMinus, wbtbtnVisan - 1, hbtbtnVisan);
		setWidthHeight(btnPlus, wbtbtnVisan - 1, hbtbtnVisan);

		setWidthHeight(btnNettSale, 8, 19);
		setWidthHeight(btnOpenCash, 8, 19);

		setWidthHeight(btnPayment, 10, 9);
		setWidthHeight(btnSearchItem, 9, 15);
		setWidthHeight(rlgvItesm, 1, 15);
		setWidthHeight(rlListLeft, 2.3, 1);
		// setWidthHeight(rlListRight, 1, 1);
		setWidthHeight(lvOrder, 1, 3.5);
		setWidthHeight(rlBottom, 1, 7);
		// setWidthHeight(rlBelowList, 1, 1);

		int subw = 16;
		int suvh = 20;
		setWidthHeight(btnSubTotalValue, subw, suvh);
		setWidthHeight(btnSubTotal, subw, suvh);

		setWidthHeight(btnGSTValue, subw, suvh);
		setWidthHeight(btnGST, subw, suvh);
		setWidthHeight(btnDisCountValue, subw, suvh);
		setWidthHeight(btnDisCount, subw, suvh);
		setWidthHeight(rlTotalAmout, 8, suvh);

		setWidth(rlDiscount, 14);
		setWidth(rlItemCode, 14);
		setWidth(rlQty, 30);
		setWidth(rlUnitPrice, 14);
		setWidth(rlItemName, 10);
		setWidthHeight(rlAll, 1, 15);

		ViewUtils.createEffectTouch(btnManagerItem, R.drawable.inventory_check,
				R.drawable.inventory_check_nguoc);
		ViewUtils.createEffectTouch(btnStockInItem, R.drawable.inventory_check,
				R.drawable.inventory_check_nguoc);
		ViewUtils.createEffectTouch(btnInventoryCheck,
				R.drawable.inventory_check, R.drawable.inventory_check_nguoc);
		ViewUtils.createEffectTouch(btnViewReport, R.drawable.inventory_check,
				R.drawable.inventory_check_nguoc);
		ViewUtils.createEffectTouch(btnPrinter, R.drawable.printer,
				R.drawable.printer_nguoc);

		ViewUtils.createEffectTouch(btnRefund, R.drawable.refund,
				R.drawable.refund_nguoc);
		ViewUtils.createEffectTouch(btnPOC, R.drawable.refund,
				R.drawable.refund_nguoc);
		ViewUtils.createEffectTouch(btnBillDiscount, R.drawable.refund,
				R.drawable.refund_nguoc);
		ViewUtils.createEffectTouch(btnReprint, R.drawable.refund,
				R.drawable.refund_nguoc);

		ViewUtils.createEffectTouch(btnUnpaid, R.drawable.refund,
				R.drawable.refund_nguoc);

		ViewUtils.createEffectTouch(btnCustom, R.drawable.hold_bill,
				R.drawable.hold_bill_nguoc);
		ViewUtils.createEffectTouch(btnSearchBill, R.drawable.hold_bill,
				R.drawable.hold_bill_nguoc);
		ViewUtils.createEffectTouch(btnHoldBill, R.drawable.hold_bill,
				R.drawable.hold_bill_nguoc);
		ViewUtils.createEffectTouch(btnChangeUser, R.drawable.hold_bill,
				R.drawable.hold_bill_nguoc);
		ViewUtils.createEffectTouch(btnNote, R.drawable.hold_bill,
				R.drawable.hold_bill_nguoc);
		ViewUtils.createEffectTouch(btnSearchItem, R.drawable.search_item,
				R.drawable.search_item_nguoc);

		ViewUtils.createEffectTouch(btnNettSale, R.drawable.nett_sale,
				R.drawable.nett_sale_nguoc);
		ViewUtils.createEffectTouch(btnOpenCash, R.drawable.open_cash_drawer,
				R.drawable.open_cash_drawer_nguoc);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	public void showDialogOK(final String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(message)
				.setCancelable(false)
				.setPositiveButton("Delete",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
		builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.setCanceledOnTouchOutside(false);
		alert.show();
	}

	public void showProgressBar() {
		pdBar.setVisibility(View.VISIBLE);
	}

	public void hideProgressBar() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				pdBar.setVisibility(View.GONE);
			}
		});
	}

	double amount1;

	private void showToast(String a) {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.custom_toast,
				(ViewGroup) findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(a);

		final Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.LEFT | Gravity.TOP, 210, 310);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				toast.cancel();
			}
		}, 200);

	}

	@Override
	public void onClick(View v) {
		if (v == btnReprint) {
			Intent i = new Intent(this, DialogTicketReport.class);
			startActivity(i);
		}
		if (v == btnSearchItem) {
			searchstring = edSearch.getText().toString();
			((CustomFragmentActivity) activity).replaceFragment(
					ConstantValue.ITEMS, false);
			edSearch.setText("");

		}
		if (v == btnRefund) {
			Utilities.getGlobalVariable(activity).isRefund = true;
			refund = true;
			// DialogRefund a = new DialogRefund();
			// a.showDialogSelectImg(activity);
		}
		if (v == btnManagerItem) {
			registerForContextMenu(btnManagerItem);
			openContextMenu(btnManagerItem);
		}
		if (v == btnPrinter) {
			// DialogGroup alert = new DialogGroup();
			// alert.showDialogAddCate(activity, activity,fa);
			// exportDB("/data/data/com.pos/databases/pos.sqlite",
			// "pos.sqlite");
			DialogNumPrint a = new DialogNumPrint();
			a.showNoteDialog(activity, activity);
		}
		if (v == btnViewReport) {
			registerForContextMenu(btnViewReport);
			openContextMenu(btnViewReport);
		}
		if (v == btnNum0) {
			Items.num = Items.num + "0";
			showToast(Items.num);
		}
		if (v == btnNum1) {
			Items.num = Items.num + "1";
			showToast(Items.num);
		}
		if (v == btnNum2) {
			Items.num = Items.num + "2";
			showToast(Items.num);
		}
		if (v == btnNum3) {
			Items.num = Items.num + "3";
			showToast(Items.num);
		}
		if (v == btnNum4) {
			Items.num = Items.num + "4";
			showToast(Items.num);
		}
		if (v == btnNum5) {
			Items.num = Items.num + "5";
			showToast(Items.num);
		}
		if (v == btnNum6) {
			Items.num = Items.num + "6";
			showToast(Items.num);
		}
		if (v == btnNum7) {
			Items.num = Items.num + "7";
			showToast(Items.num);
		}
		if (v == btnNum8) {
			Items.num = Items.num + "8";
			showToast(Items.num);
		}
		if (v == btnNum9) {
			Items.num = Items.num + "9";
			showToast(Items.num);
		}
		if (v == btnPlus) {
			try {
				ItemsDataSource a = new ItemsDataSource(activity, activity);
				int temp = Integer.parseInt(PosApp.listOrderData.get(qly)
						.getQualyti());
				int temp2 = temp + 1;
				String tempquan = "" + temp2;

				ListOrderModel md = new ListOrderModel();
				md.setPrice2(PosApp.listOrderData.get(qly).getPrice2());
				md.setSpecialPrice(PosApp.listOrderData.get(qly)
						.getSpecialPrice());
				md.setDiscount(PosApp.listOrderData.get(qly).getDiscount());
				md.setItemCode(PosApp.listOrderData.get(qly).getItemCode());
				md.setItemName(PosApp.listOrderData.get(qly).getItemName());
				md.setUnitPrice(PosApp.listOrderData.get(qly).getUnitPrice());
				md.setQualyti(String.valueOf(Integer
						.parseInt(PosApp.listOrderData.get(qly).getQualyti()) + 1));
				amount1 = Double.valueOf(Double
						.parseDouble(PosApp.listOrderData.get(qly)
								.getUnitPrice())
						* Double.parseDouble(md.getQualyti())
						- Double.parseDouble(md.getDiscount()));
				// amount1 = Math.round(amount1);
				DecimalFormat df = new DecimalFormat("0.00");
				amount1 = Double.valueOf(amount1);
				md.setAmount(df.format(amount1) + "");

				// md.setAmount(String.valueOf(amount1));
				PosApp.listOrderData.set(qly, md);
				// VFD
				try {
					int baudrate = 9600;
					int databits = 8;
					int parity = 0;
					int stopbits = 1;
					int flowctl = 0;
					String path = "/dev/ttymxc2";
					mSerialPort_V = new SerialPort(new File(path), baudrate,
							databits, parity, stopbits, flowctl);
					mOutputStream_V = mSerialPort_V.getOutputStream();
					mOutputStream_V.write(0x1b);
					mOutputStream_V.write(0x40);

					String itemName = PosApp.listOrderData.get(qly)
							.getItemName().toString();
					String spaceName = "";
					if (itemName.length() > 20) {
						itemName = itemName.substring(0, 20);

					} else {
						int tempvar = 20 - itemName.length();
						for (int i = 0; i < tempvar; i++) {
							spaceName += " ";

						}

					}
					mOutputStream_V.write(itemName.getBytes());
					mOutputStream_V.write(spaceName.getBytes());
					String space = "      ";
					// mOutputStream_V.write(space.getBytes());
					int changequantity = Integer.parseInt(PosApp.listOrderData
							.get(qly).getQualyti());
					double amount = Double.parseDouble(PosApp.listOrderData
							.get(qly).getUnitPrice())
							* Double.parseDouble(md.getQualyti())
							- Double.parseDouble(md.getDiscount());
					int len1 = PosApp.listOrderData.get(qly).getQualyti()
							.toString().length();
					String strtemp = "" + amount;
					int len2 = strtemp.length();
					double spaceneed = 14 - (len1 + len2);
					// mOutputStream_V.write(space.getBytes());
					for (int i = 0; i < spaceneed; i++) {
						space += " ";
					}
					String quantity = "" + changequantity;
					DecimalFormat df1 = new DecimalFormat("0.00");
					amount1 = Double.valueOf(amount1);
					String amountc = "" + df1.format(amount1);
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
				// EO VFD
				notifidataOrderList();
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		if (v == btnMinus) {
			if (qly >= 0) {

				try {
					ListOrderModel md = new ListOrderModel();
					int temp = Integer.parseInt(PosApp.listOrderData.get(qly)
							.getQualyti());
					if (temp == 1) {
						ListOrderAdapter a = new ListOrderAdapter(activity);
						PosApp.listOrderData.remove(qly);
					} else {
						md.setPrice2(PosApp.listOrderData.get(qly).getPrice2());
						md.setSpecialPrice(PosApp.listOrderData.get(qly)
								.getSpecialPrice());
						md.setDiscount(PosApp.listOrderData.get(qly)
								.getDiscount());
						md.setItemCode(PosApp.listOrderData.get(qly)
								.getItemCode());
						md.setItemName(PosApp.listOrderData.get(qly)
								.getItemName());
						md.setUnitPrice(PosApp.listOrderData.get(qly)
								.getUnitPrice());
						md.setQualyti(String.valueOf(Integer
								.parseInt(PosApp.listOrderData.get(qly)
										.getQualyti()) - 1));
						amount1 = Double.valueOf(Double
								.parseDouble(PosApp.listOrderData.get(qly)
										.getUnitPrice())
								* Double.parseDouble(md.getQualyti())
								- Double.parseDouble(md.getDiscount()));
						DecimalFormat df = new DecimalFormat("0.00");
						amount1 = Double.valueOf(amount1);
						md.setAmount(df.format(amount1) + "");
						// amount1 = Math.round(amount1);
						// md.setAmount(String.valueOf(amount1));
						PosApp.listOrderData.set(qly, md);

					}

					// VFD
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

						String itemName = PosApp.listOrderData.get(qly)
								.getItemName().toString();
						String spaceName = "";
						if (itemName.length() > 20) {
							itemName = itemName.substring(0, 20);
						} else {
							int tempvar = 20 - itemName.length();
							for (int i = 0; i < tempvar; i++) {
								spaceName += " ";

							}

						}
						mOutputStream_V.write(itemName.getBytes());
						mOutputStream_V.write(spaceName.getBytes());
						String space = "      ";
						// mOutputStream_V.write(space.getBytes());
						int changequantity = Integer
								.parseInt(PosApp.listOrderData.get(qly)
										.getQualyti());
						double amount = Double.parseDouble(PosApp.listOrderData
								.get(qly).getUnitPrice())
								* Double.parseDouble(md.getQualyti())
								- Double.parseDouble(md.getDiscount());
						int len1 = PosApp.listOrderData.get(qly).getQualyti()
								.toString().length();
						String strtemp = "" + amount;
						int len2 = strtemp.length();
						double spaceneed = 14 - (len1 + len2);
						// mOutputStream_V.write(space.getBytes());
						for (int i = 0; i < spaceneed; i++) {
							space += " ";
						}
						String quantity = "" + changequantity;
						DecimalFormat df = new DecimalFormat("0.00");
						amount1 = Double.valueOf(amount1);
						String amountc = "" + df.format(amount1);
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
					// EO VFD

					notifidataOrderList();

				} catch (Exception e) {
					// TODO: handle exception
				}

			}
			if (PosApp.listOrderData.size() == 0) {
				ComDDUtilities.showWelcome();
			}
		}
		if (v == btnCancelBill) {
			if (Utilities.getGlobalVariable(activity).isHoldPaid) {
				SearchBillDataSource data = new SearchBillDataSource(activity,
						activity);
				int a = data.deleteItem(DialogSearchBill.list.get(
						DialogSearchBill.a).getSaleID());
				int ak = data.deleteItemAll(DialogSearchBill.list.get(
						DialogSearchBill.a).getSaleID());
				Utilities.getGlobalVariable(activity).isHoldPaid = false;
			}
			btnDisCountValue.setText("0");
			PosApp.listOrderData.clear();
			Utilities.getGlobalVariable(activity).isRefund = false;
			notifidataOrderList();
			ComDDUtilities.showWelcome();

		}
		if (v == btnPOC) {
			Utilities.getGlobalVariable(activity).isPOC = true;

		}
		if (v == btnBillDiscount) {
			if (PosApp.listOrderData.size() != 0) {
				DialogBillDiscount a = new DialogBillDiscount();
				a.showDialogBillDiscount(activity, activity, btnSubTotalValue
						.getText().toString());
			}

		}
		if (v == btnUnpaid) {
			if (PosApp.listOrderData.size() != 0) {
				UnpaidModel up = new UnpaidModel();
				up.setTotal_amount(tvTotalAmount.getText().toString());
				up.setStatus("1");
				up.setNote(note);
				up.setDiscount(btnDisCountValue.getText().toString());
				int stt = ss.getStt() + 1;
				Log.v("HAI", stt + "");
				up.setReceipt_no(ComDDUtilities.getDateTime() + "-" + stt);
				ss.saveStt(stt);
				UnpaidDataSource dataSource = new UnpaidDataSource(activity,
						activity);
				long id = dataSource.insert(up);
				// Log.v("HAI", id + "");
				for (int i = 0; i < PosApp.listOrderData.size(); i++) {
					UnpaidModel up1 = new UnpaidModel();
					up1.setAmount(PosApp.listOrderData.get(i).getAmount());
					up1.setDiscount(PosApp.listOrderData.get(i).getDiscount());
					up1.setItemCode(PosApp.listOrderData.get(i).getItemCode());
					up1.setItemName(PosApp.listOrderData.get(i).getItemName());
					up1.setQuantity(PosApp.listOrderData.get(i).getQualyti());
					up1.setSaleID(String.valueOf(id));
					up1.setUnitPrice(PosApp.listOrderData.get(i).getUnitPrice());

					up1.setPrice2(PosApp.listOrderData.get(i).getPrice2());
					up1.setSpecialPrice(PosApp.listOrderData.get(i)
							.getSpecialPrice());
					long a = dataSource.insertItems(up1);
					// Log.v("HAI", a + "");
				}
				PosApp.listOrderData.clear();
				Utilities.getGlobalVariable(activity).note = "";
				notifidataOrderList();

				btnDisCountValue.setText("0");
			}
			ComDDUtilities.showWelcome();

		}
		if (v == btnHoldBill) {
			if (PosApp.listOrderData.size() != 0) {
				UnpaidModel up = new UnpaidModel();
				up.setTotal_amount(tvTotalAmount.getText().toString());
				up.setStatus("0");
				up.setNote(note);
				up.setDiscount(btnDisCountValue.getText().toString());
				int stt = ss.getStt() + 1;
				Log.v("HAI", stt + "");
				up.setReceipt_no(ComDDUtilities.getDateTime() + "-" + stt);
				ss.saveStt(stt);
				UnpaidDataSource dataSource = new UnpaidDataSource(activity,
						activity);
				long id = dataSource.insert(up);
				for (int i = 0; i < PosApp.listOrderData.size(); i++) {
					UnpaidModel up1 = new UnpaidModel();
					up1.setAmount(PosApp.listOrderData.get(i).getAmount());
					up1.setDiscount(PosApp.listOrderData.get(i).getDiscount());
					up1.setItemCode(PosApp.listOrderData.get(i).getItemCode());
					up1.setItemName(PosApp.listOrderData.get(i).getItemName());
					up1.setQuantity(PosApp.listOrderData.get(i).getQualyti());
					up1.setSaleID(String.valueOf(id));
					up1.setUnitPrice(PosApp.listOrderData.get(i).getUnitPrice());
					up1.setPrice2(PosApp.listOrderData.get(i).getPrice2());
					up1.setSpecialPrice(PosApp.listOrderData.get(i)
							.getSpecialPrice());
					long a = dataSource.insertItems(up1);
				}
				PosApp.listOrderData.clear();
				Utilities.getGlobalVariable(activity).note = "";
				notifidataOrderList();

				btnDisCountValue.setText("0");
			}
			ComDDUtilities.showWelcome();
		}
		if (v == btnSearchBill) {
			DialogSearchBill a = new DialogSearchBill();
			a.showDialogEditSale(activity, activity);

		}
		if (v == btnPayment) {
			if (PosApp.listOrderData.size() != 0) {
				DialogPayment a = new DialogPayment();
				a.showDialogSelectImg(activity, tvTotalAmount.getText()
						.toString());
			}

		}
		if (v == btnOpenCash) {
			mCashDrawer.openCashDrawer();

		}
		if (v == btnPrinter) {
			// printer
			// String commandType = "Raster";
			// PrinterFunctions.PrintSampleReceipt(MainActivity.this, "USB:",
			// "",
			// "Raster", getResources(), "3inch (78mm)");

			// end of printer
		}
		if (v == btnNettSale) {
			if (note.equals("null")) {
				note = "";
			}
			if (PosApp.listOrderData.size() != 0) {
				if (Utilities.getGlobalVariable(activity).isHoldPaid) {
					PayModel up = new PayModel();
					up.setTotal_amount(tvTotalAmount.getText().toString());
					up.setStatus("0");
					up.setNote(note);
					up.setPercentDis(DialogBillDiscount.percentDis);
					up.setDollarDis(btnDisCountValue.getText().toString());
					up.setPaymentMode("1");
					up.setCreaditType("");
					up.setSubTotal(btnSubTotalValue.getText().toString());
					up.setGST(Utilities.getGlobalVariable(activity).GST + "");
					up.setReceipt_no(DialogSearchBill.recepit);
					up.setIdUser(LoginDialog.idUser);
					PayDataSource dataSource = new PayDataSource(activity,
							activity);
					// SearchBillDataSource seardata = new SearchBillDataSource(
					// MainActivity.this, MainActivity.this);
					// long ad = seardata.update(up.getReceipt_no());
					long id = dataSource.insert(up, "CASH");
					for (int i = 0; i < PosApp.listOrderData.size(); i++) {
						PayModel up1 = new PayModel();
						up1.setAmount(PosApp.listOrderData.get(i).getAmount());
						up1.setDiscount(PosApp.listOrderData.get(i)
								.getDiscount());
						up1.setItemCode(PosApp.listOrderData.get(i)
								.getItemCode());
						up1.setItemName(PosApp.listOrderData.get(i)
								.getItemName());
						up1.setQuantity(PosApp.listOrderData.get(i)
								.getQualyti());
						up1.setSaleID(String.valueOf(id));
						up1.setUnitPrice(PosApp.listOrderData.get(i)
								.getUnitPrice());
						up1.setPrice2(PosApp.listOrderData.get(i).getPrice2());
						up1.setSpecialPrice(PosApp.listOrderData.get(i)
								.getSpecialPrice());
						up1.setUom(dataSource.loadUom(up1.getItemCode()));
						long a = dataSource.insertItems(up1);
						if (!up1.getQuantity().equals("-1")) {
							long a11 = dataSource.insertInventoryReport(up1,
									activity);
						}
						ItemsDataSource a1 = new ItemsDataSource(activity,
								activity);
						String debug = "";
						String temp = PosApp.listOrderData.get(i).getItemCode();
						if (temp != "") {
							debug = a1.Updatequantity(
									PosApp.listOrderData.get(i).getItemCode(),
									PosApp.listOrderData.get(i).getQualyti());
						}
					}
					SearchBillDataSource data = new SearchBillDataSource(
							activity, activity);
					int a = data.deleteItem(DialogSearchBill.idSale);
					int ak = data.deleteItemAll(DialogSearchBill.idSale);
					DialogSearchBill di = new DialogSearchBill();
					di.notifi();

					Utilities.getGlobalVariable(activity).note = "";
					DialogBillDiscount.percentDis = "0";
					DialogBillDiscount.dollarDis = "0";

					openAndPrint(up.getReceipt_no());
					PosApp.listOrderData.clear();
					notifidataOrderList();
					Utilities.getGlobalVariable(activity).isHoldPaid = false;
				} else {
					PayModel up = new PayModel();
					up.setTotal_amount(tvTotalAmount.getText().toString());
					up.setStatus("0");
					up.setNote(note);
					up.setPercentDis(DialogBillDiscount.percentDis);
					up.setDollarDis(btnDisCountValue.getText().toString());
					up.setPaymentMode("1");
					up.setCreaditType("");
					up.setSubTotal(btnSubTotalValue.getText().toString());
					up.setGST(btnGSTValue.getText().toString());
					up.setIdUser(LoginDialog.idUser);

					int stt = ss.getStt() + 1;
					up.setReceipt_no(ComDDUtilities.getDateTime() + "-" + stt);
					ss.saveStt(stt);
					PayDataSource dataSource = new PayDataSource(activity,
							activity);
					long id = dataSource.insert(up, "CASH");
					String debug = "";
					for (int i = 0; i < PosApp.listOrderData.size(); i++) {
						PayModel up1 = new PayModel();
						up1.setAmount(PosApp.listOrderData.get(i).getAmount());
						up1.setDiscount(PosApp.listOrderData.get(i)
								.getDiscount());
						up1.setItemCode(PosApp.listOrderData.get(i)
								.getItemCode());
						up1.setItemName(PosApp.listOrderData.get(i)
								.getItemName());
						up1.setQuantity(PosApp.listOrderData.get(i)
								.getQualyti());
						up1.setSaleID(String.valueOf(id));
						up1.setUnitPrice(PosApp.listOrderData.get(i)
								.getUnitPrice());
						up1.setPrice2(PosApp.listOrderData.get(i).getPrice2());
						up1.setSpecialPrice(PosApp.listOrderData.get(i)
								.getSpecialPrice());
						long a = dataSource.insertItems(up1);
						if (!up1.getQuantity().equals("-1")) {
							long a11 = dataSource.insertInventoryReport(up1,
									activity);
						}

						ItemsDataSource a1 = new ItemsDataSource(activity,
								activity);
						String temp = PosApp.listOrderData.get(i).getItemCode();
						if (temp != "") {
							debug = a1.Updatequantity(
									PosApp.listOrderData.get(i).getItemCode(),
									PosApp.listOrderData.get(i).getQualyti());

						}
					}

					DialogBillDiscount.percentDis = "0";
					DialogBillDiscount.dollarDis = "0";

					openAndPrint(up.getReceipt_no());
					PosApp.listOrderData.clear();
					Utilities.getGlobalVariable(activity).isRefund = false;
					refund = false;
					notifidataOrderList();
				}
				// btnDisCountValue.setText("0");
			}
			modepay = "CASH " + tvTotalAmount.getText().toString();
		}
		if (v == btnNote) {
			DialogNote dlnote = new DialogNote();
			dlnote.showNoteDialog(activity, activity);

		}

		if (v == btnDinner) {
			creaditType = "DinnerCard";
			if (PosApp.listOrderData.size() != 0) {
				payCreaditCard("DINERS");
			}
		}
		if (v == btnVisa) {
			creaditType = "VisaCard";
			if (PosApp.listOrderData.size() != 0) {
				payCreaditCard("VISA");
			}
		}
		if (v == btnMasterCard) {
			creaditType = "MasterCard";
			if (PosApp.listOrderData.size() != 0) {
				payCreaditCard("MASTER");
			}
		}
		if (v == btnNet) {
			creaditType = "NETSCard";
			if (PosApp.listOrderData.size() != 0) {
				payCreaditCard("NETS");
			}
		}
		if (v == btnJBC) {
			creaditType = "JBCCard";
			if (PosApp.listOrderData.size() != 0) {
				payCreaditCard("JCB");
			}
		}
		if (v == btnArmet) {
			creaditType = "AmericanCard";
			if (PosApp.listOrderData.size() != 0) {
				payCreaditCard("AMEX");
			}
		}
		if (v == btnCustom) {
			DialogCustomItem a = new DialogCustomItem();
			a.showDialogDefineCustomItem(activity, activity);

		}
		if (v == btnInventoryCheck) {
			DialogInventoryCheck a = new DialogInventoryCheck();
			a.showDialogInventory(activity, activity);
		}
		if (v == btnStockInItem) {
			DialogStockIn a = new DialogStockIn();
			a.showDialogStockIn(activity, activity);
		}
		if (v == btnChangeUser) {
			LoginDialog a = new LoginDialog();
			a.showDialogSelectImg2(activity);
		}
		if (v == btnDelete) {
			Items.num = "";
			showToast("0");
		}
		if (v == btnPrintNoSave) {
			isPrintNoSave = true;
			if (PosApp.isHoldPaid) {
				holdbillUpdate(DialogSearchBill.idSale);
			} else {
				holdbill("0");
			}
		}

	}

	public static boolean isPrintNoSave = false;

	public void printWithoutRec(String recep) {
		PrinterFunctions.PrintPos(activity, activity, "USB:", "",
				activity.getResources(), recep);

	}

	public void holdbill(String ishole) {
		try {
			UnpaidModel up = new UnpaidModel();
			up.setTotal_amount(tvTotalAmount.getText().toString());
			up.setStatus(ishole);
			up.setNote(note);
			up.setDiscount(btnDisCountValue.getText().toString());
			int stt = ss.getStt() + 1;
			Log.v("HAI", stt + "");
			up.setReceipt_no(ComDDUtilities.getDateTime() + "-" + stt);
			if (isPrintNoSave) {
				printWithoutRec(up.getReceipt_no());

			}
			ss.saveStt(stt);
			UnpaidDataSource dataSource = new UnpaidDataSource(activity,
					activity);
			long id = dataSource.insert(up);
			for (int i = 0; i < PosApp.listOrderData.size(); i++) {
				UnpaidModel up1 = new UnpaidModel();
				up1.setAmount(PosApp.listOrderData.get(i).getAmount());
				up1.setDiscount(PosApp.listOrderData.get(i).getDiscount());
				up1.setItemCode(PosApp.listOrderData.get(i).getItemCode());
				up1.setItemName(PosApp.listOrderData.get(i).getItemName());
				up1.setQuantity(PosApp.listOrderData.get(i).getQualyti());
				up1.setSaleID(String.valueOf(id));
				up1.setUnitPrice(PosApp.listOrderData.get(i).getUnitPrice());
				up1.setPrice2(PosApp.listOrderData.get(i).getPrice2());
				up1.setSpecialPrice(PosApp.listOrderData.get(i)
						.getSpecialPrice());
				long a = dataSource.insertItems(up1);
			}
			PosApp.listOrderData.clear();
			Utilities.getGlobalVariable(activity).note = "";
			notifidataOrderList();

			btnDisCountValue.setText("0");
			ComDDUtilities.showWelcome();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void holdbillUpdate(String saleid) {
		try {

			UnpaidModel up = new UnpaidModel();
			up.setTotal_amount(tvTotalAmount.getText().toString());
			// up.setNote(note);
			up.setDiscount(btnDisCountValue.getText().toString());
			UnpaidDataSource dataSource1 = new UnpaidDataSource(activity,
					activity);
			long id = dataSource1.updateTotal(saleid, up.getTotal_amount(),
					up.getDiscount());

			if (isPrintNoSave) {
				int stt = ss.getStt() + 1;
				printWithoutRec(ComDDUtilities.getDateTime() + "-" + stt);
				// isPrintNoSave=false;
			}
			SearchBillDataSource data = new SearchBillDataSource(activity,
					activity);
			int ak = data.deleteItemAll(saleid);
			UnpaidDataSource dataSource = new UnpaidDataSource(activity,
					activity);
			for (int i = 0; i < PosApp.listOrderData.size(); i++) {
				UnpaidModel up1 = new UnpaidModel();
				up1.setAmount(PosApp.listOrderData.get(i).getAmount());
				up1.setDiscount(PosApp.listOrderData.get(i).getDiscount());
				up1.setItemCode(PosApp.listOrderData.get(i).getItemCode());
				up1.setItemName(PosApp.listOrderData.get(i).getItemName());
				up1.setQuantity(PosApp.listOrderData.get(i).getQualyti());
				up1.setSaleID(saleid);
				up1.setUnitPrice(PosApp.listOrderData.get(i).getUnitPrice());
				up1.setPrice2(PosApp.listOrderData.get(i).getPrice2());
				up1.setSpecialPrice(PosApp.listOrderData.get(i)
						.getSpecialPrice());
				long a = dataSource.insertItems(up1);
			}
			PosApp.listOrderData.clear();
			Utilities.getGlobalVariable(activity).note = "";
			notifidataOrderList();

			btnDisCountValue.setText("0");
			ComDDUtilities.showWelcome();
			Utilities.getGlobalVariable(activity).isHoldPaid = false;
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void PayNet(String tablenum) {
		PayModel up = new PayModel();
		up.setTotal_amount(tvTotalAmount.getText().toString());
		up.setStatus("0");
		up.setNote(note);
		up.setPercentDis(DialogBillDiscount.percentDis);
		up.setDollarDis(btnDisCountValue.getText().toString());
		up.setPaymentMode("1");
		up.setCreaditType("");
		up.setSubTotal(btnSubTotalValue.getText().toString());
		up.setGST(btnGSTValue.getText().toString());
		up.setIdUser(LoginDialog.idUser);
		up.setTableNumber(tablenum);

		int stt = ss.getStt() + 1;
		up.setReceipt_no(ComDDUtilities.getDateTime() + "-" + stt);
		ss.saveStt(stt);
		PayDataSource dataSource = new PayDataSource(activity, activity);
		long id = dataSource.insert(up, "CASH");
		String debug = "";
		for (int i = 0; i < PosApp.listOrderData.size(); i++) {
			PayModel up1 = new PayModel();
			up1.setAmount(PosApp.listOrderData.get(i).getAmount());
			up1.setDiscount(PosApp.listOrderData.get(i).getDiscount());
			up1.setItemCode(PosApp.listOrderData.get(i).getItemCode());
			up1.setItemName(PosApp.listOrderData.get(i).getItemName());
			up1.setQuantity(PosApp.listOrderData.get(i).getQualyti());
			up1.setSaleID(String.valueOf(id));
			up1.setUnitPrice(PosApp.listOrderData.get(i).getUnitPrice());
			up1.setPrice2(PosApp.listOrderData.get(i).getPrice2());
			up1.setSpecialPrice(PosApp.listOrderData.get(i).getSpecialPrice());
			up1.setUom(dataSource.loadUom(up1.getItemCode()));
			long a = dataSource.insertItems(up1);
			if (!up1.getQuantity().equals("-1")) {
				long a11 = dataSource.insertInventoryReport(up1, activity);
			}

			ItemsDataSource a1 = new ItemsDataSource(activity, activity);
			String temp = PosApp.listOrderData.get(i).getItemCode();
			if (temp != "") {
				debug = a1.Updatequantity(PosApp.listOrderData.get(i)
						.getItemCode(), PosApp.listOrderData.get(i)
						.getQualyti());

			}
		}

		DialogBillDiscount.percentDis = "0";
		DialogBillDiscount.dollarDis = "0";
		// if (up.getTableNumber() == null) {
		// up.setTableNumber("0");
		// }
		openAndPrint(up.getReceipt_no());
		PosApp.listOrderData.clear();
		Utilities.getGlobalVariable(activity).isRefund = false;
		refund = false;
		notifidataOrderList();
		modepay = "CASH " + tvTotalAmount.getText().toString();
	}

	private void payCreaditCard(String credit) {
		if (PosApp.listOrderData.size() != 0) {
			if (Utilities.getGlobalVariable(activity).isHoldPaid) {
				PayModel up = new PayModel();

				up.setTotal_amount(tvTotalAmount.getText().toString());
				up.setStatus("0");
				up.setNote(note);
				up.setPercentDis(DialogBillDiscount.percentDis);
				up.setDollarDis(btnDisCountValue.getText().toString());
				up.setPaymentMode("2");
				up.setCreaditType(credit);
				up.setSubTotal(btnSubTotalValue.getText().toString());
				up.setGST(btnGSTValue.getText().toString());
				up.setIdUser(LoginDialog.idUser);
				up.setReceipt_no(DialogSearchBill.recepit);
				PayDataSource dataSource = new PayDataSource(activity, activity);
				// SearchBillDataSource seardata = new SearchBillDataSource(
				// MainActivity.this, MainActivity.this);
				// long ad = seardata.update(up.getReceipt_no());
				long id = dataSource.insert(up, credit);

				for (int i = 0; i < PosApp.listOrderData.size(); i++) {
					PayModel up1 = new PayModel();
					up1.setAmount(PosApp.listOrderData.get(i).getAmount());
					up1.setDiscount(PosApp.listOrderData.get(i).getDiscount());
					up1.setItemCode(PosApp.listOrderData.get(i).getItemCode());
					up1.setItemName(PosApp.listOrderData.get(i).getItemName());
					up1.setQuantity(PosApp.listOrderData.get(i).getQualyti());
					up1.setSaleID(String.valueOf(id));
					up1.setUnitPrice(PosApp.listOrderData.get(i).getUnitPrice());
					up1.setPrice2(PosApp.listOrderData.get(i).getPrice2());
					up1.setSpecialPrice(PosApp.listOrderData.get(i)
							.getSpecialPrice());
					up1.setUom(dataSource.loadUom(up1.getItemCode()));
					ItemsDataSource a1 = new ItemsDataSource(activity, activity);
					String str = PosApp.listOrderData.get(i).getItemCode();
					if (str != "") {
						String debug = a1.Updatequantity(PosApp.listOrderData
								.get(i).getItemCode(), PosApp.listOrderData
								.get(i).getQualyti());
					}
					long a = dataSource.insertItems(up1);
					if (!up1.getQuantity().equals("-1")) {
						long a11 = dataSource.insertInventoryReport(up1,
								activity);
					}
				}
				SearchBillDataSource data = new SearchBillDataSource(activity,
						activity);
				int a = data.deleteItem(DialogSearchBill.idSale);
				int ak = data.deleteItemAll(DialogSearchBill.idSale);
				DialogSearchBill di = new DialogSearchBill();
				di.notifi();
				Utilities.getGlobalVariable(activity).note = "";
				DialogBillDiscount.percentDis = "0";
				DialogBillDiscount.dollarDis = "0";

				// Toast.makeText(MainActivity.this, "Data Saved Payyyyy",
				// Toast.LENGTH_SHORT).show();
				openAndPrint(up.getReceipt_no());
				PosApp.listOrderData.clear();
				notifidataOrderList();
				Utilities.getGlobalVariable(activity).isHoldPaid = false;
			} else {

				PayModel up = new PayModel();
				up.setTotal_amount(tvTotalAmount.getText().toString());
				up.setStatus("0");
				up.setNote(note);
				up.setPercentDis(DialogBillDiscount.percentDis);
				up.setDollarDis(btnDisCountValue.getText().toString());
				up.setPaymentMode("2");
				up.setCreaditType(credit);
				up.setIdUser(LoginDialog.idUser);
				up.setSubTotal(btnSubTotalValue.getText().toString());
				up.setGST(btnGSTValue.getText().toString());
				int stt = ss.getStt() + 1;
				up.setReceipt_no(ComDDUtilities.getDateTime() + "-" + stt);
				ss.saveStt(stt);
				PayDataSource dataSource = new PayDataSource(activity, activity);
				long id = dataSource.insert(up, credit);
				for (int i = 0; i < PosApp.listOrderData.size(); i++) {
					PayModel up1 = new PayModel();
					up1.setAmount(PosApp.listOrderData.get(i).getAmount());
					up1.setDiscount(PosApp.listOrderData.get(i).getDiscount());
					up1.setItemCode(PosApp.listOrderData.get(i).getItemCode());
					up1.setItemName(PosApp.listOrderData.get(i).getItemName());
					up1.setQuantity(PosApp.listOrderData.get(i).getQualyti());
					up1.setSaleID(String.valueOf(id));
					up1.setUnitPrice(PosApp.listOrderData.get(i).getUnitPrice());
					up1.setPrice2(PosApp.listOrderData.get(i).getPrice2());
					up1.setSpecialPrice(PosApp.listOrderData.get(i)
							.getSpecialPrice());
					long a = dataSource.insertItems(up1);
					if (!up1.getQuantity().equals("-1")) {
						long a11 = dataSource.insertInventoryReport(up1,
								activity);
					}
					ItemsDataSource a1 = new ItemsDataSource(activity, activity);
					String str = PosApp.listOrderData.get(i).getItemCode();
					if (str != "") {
						String debug = a1.Updatequantity(PosApp.listOrderData
								.get(i).getItemCode(), PosApp.listOrderData
								.get(i).getQualyti());
					}
				}
				openAndPrint(up.getReceipt_no());
				PosApp.listOrderData.clear();
				note = "";
				DialogBillDiscount.percentDis = "0";
				DialogBillDiscount.dollarDis = "0";
				notifidataOrderList();
				Utilities.getGlobalVariable(activity).isRefund = false;
				refund = false;
				// Toast.makeText(MainActivity.this, note, Toast.LENGTH_LONG)
				// .show();
			}

		}
		modepay = "CREDIT CARD";
	}

	public void updatequantity(String itemid, String qty) {
		ItemsDataSource a = new ItemsDataSource(activity, activity);
		a.Updatequantity(itemid, qty);
	}

	public void openAndPrint(String recep) {
		mCashDrawer.openCashDrawer();
		if (MainActivity.returnla == 0) {
			PrinterFunctions.PrintPos(activity, activity, "USB:", "",
					activity.getResources(), recep);
		} else {
			PrinterFunctions.PrintPosReturn(activity, activity, "USB:", "",
					activity.getResources(), recep);
			MainActivity.returnla = 0;
		}

		// PrinterFunctions.PrintSampleReceiptbyDotPrinter(MainActivity.this,
		// "USB:", "");
	}

	public void openCashDrawer() {
		mCashDrawer.openCashDrawer();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenu.ContextMenuInfo menuInfo) {
		// Context menu
		if (v == btnManagerItem) {
			menu.setHeaderTitle("Choose Mode");
			menu.add(Menu.NONE, CONTEXT_MENU_VIEW, Menu.NONE, "Item List");
			menu.add(Menu.NONE, CONTEXT_MENU_EDIT, Menu.NONE, "Group List");
			Utilities.getGlobalVariable(activity).isReport = false;
		}
		if (v == btnViewReport) {
			menu.setHeaderTitle("Choose Report");
			menu.add(Menu.NONE, CONTEXT_MENU_VIEW, Menu.NONE, "Sale Report");
			menu.add(Menu.NONE, CONTEXT_MENU_EDIT, Menu.NONE,
					"Inventory Report");
			Utilities.getGlobalVariable(activity).isReport = true;
		}

	}

	final int CONTEXT_MENU_VIEW = 1;
	final int CONTEXT_MENU_EDIT = 2;
	final int CONTEXT_MENU_WAlk = 3;

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (!Utilities.getGlobalVariable(activity).isReport) {
			switch (item.getItemId()) {

			case CONTEXT_MENU_VIEW: {
				DialogItems a = new DialogItems();
				a.showDialogAddItem(activity, activity, false, "");

			}
				break;
			case CONTEXT_MENU_EDIT: {
				DialogGroup alert = new DialogGroup();
				alert.showDialogAddCate(activity, activity, false, "-1");

			}
				break;

			}
		} else {
			switch (item.getItemId()) {

			case CONTEXT_MENU_VIEW: {
				Intent i = new Intent(this, DialogSaleReport.class);
				startActivity(i);

			}
				break;
			case CONTEXT_MENU_EDIT: {
				Intent i = new Intent(this, DialogInventoryReport.class);
				startActivity(i);

			}
				break;

			}
		}

		return super.onContextItemSelected(item);
	}

	private void exportDB(final String a, final String name) {
		final String inFileName = a;
		java.io.File outFileName = new java.io.File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
						+ "/" + name);
		// final String outFileName = "/data/app/chiettinh.png";

		// File file = new File(outFileName);
		// if (file != null && !file.exists())
		{
			InputStream myInput = null; // MainApplication.getAppContext().getAssets().open(DB_ASSETS_NAME);
			// Path to the just created empty db
			// Open the empty db as the output stream
			OutputStream myOutput = null; // new FileOutputStream(outFileName);

			try {
				myInput = new FileInputStream(inFileName);
				myOutput = new FileOutputStream(outFileName);

				// transfer bytes from the inputfile to the outputfile
				byte[] buffer = new byte[1024];
				int length;

				while ((length = myInput.read(buffer)) > 0) {
					myOutput.write(buffer, 0, length);
				}

				// Close the streams
				myOutput.flush();
				myOutput.close();
				myInput.close();
				// DialogGroup.image = outFileName.toString();
				// DialogItems.image = outFileName.toString();
				// LoginDialog.linkImage = outFileName.toString();
				// db.execSQL(TABLE_CHECK_EXIST);
				Log.i("Export DB", "Done copy");
				// Utils.showMess("Sao lÆ°u cÆ¡ sÆ¡ dá»¯ liá»‡u thĂ nh cĂ´ng!");
			} catch (Exception e) {
				Log.i("Export DB", e.toString());

			} finally {

			}
		}
	}
}
