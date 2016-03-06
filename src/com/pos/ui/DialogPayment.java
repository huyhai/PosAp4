package com.pos.ui;

import java.io.File;
import java.io.OutputStream;
import java.text.DecimalFormat;

import com.pos.Application;
import com.pos.CustomFragmentActivity;
import com.pos.MainActivity;
import com.pos.PosApp;
import com.pos.R;
import com.pos.common.Utilities;
import com.pos.db.ItemsDataSource;
import com.pos.db.PayDataSource;
import com.pos.db.SearchBillDataSource;
import com.pos.libs.CalculationUtils;
import com.pos.libs.ComDDUtilities;
import com.pos.libs.SessionManager;
import com.pos.model.ListOrderModel;
import com.pos.model.PayModel;
import com.pos.print.PrinterFunctions;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.drawable.ColorDrawable;
import android.serialport.SerialPort;

import android.serialport.SerialPortFinder;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnHoverListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class DialogPayment implements OnClickListener {
	private static Dialog dialogEditItems;
	private Boolean isShowDialog = false;
	private static Activity ac;
	private RelativeLayout rlAll;
	private RelativeLayout rlCash;
	private RelativeLayout rlMasterCash;
	private RelativeLayout rlChange;
	private Button btnNets;
	private Button btnAme;
	private Button btnMaster;
	private Button btnJCB;
	private Button btnVisa;
	private Button btnDinner;
	private Button btnCash;
	private Button btnConfirm;
	private Button btnConfirmAndRe;
	private TextView tvTotalValue;
	private static TextView tvCash;
	private static TextView tvCashValue;
	private static TextView tvMasterCash;
	private static TextView tvMasterCashValue;
	private static TextView tvChange;
	private static TextView tvrlChangeValue;

	private Button btnSeven;
	private Button btnEight;
	private Button btnNine;
	private Button btnFour;
	private Button btnFive;
	private Button btnSix;
	private Button btnOne;
	private Button btnTwo;
	private Button btnThree;
	private Button btnZero;
	private Button btnDot;
	private Button btnDelete;
	private static String totala;
	private static SessionManager ss;
	private SerialPort mSerialPort_V;
	private OutputStream mOutputStream_V;
	private Application mApplication;

	public void showDialogSelectImg(Activity _ac, String total) {
		ac = _ac;
		totala = total;
		ss = new SessionManager(ac);
		// custom dialog
		if (!isShowDialog) {
			isShowDialog = true;

			// imgSet = _imgSet;
			dialogEditItems = new Dialog(ac);
			dialogEditItems.getWindow();
			dialogEditItems.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialogEditItems.setContentView(R.layout.payment);
			dialogEditItems.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			// dialog.setTitle("Title...");
			rlAll = (RelativeLayout) dialogEditItems
					.findViewById(R.id.rlAppPayment);

			rlCash = (RelativeLayout) dialogEditItems.findViewById(R.id.rlCash);
			rlMasterCash = (RelativeLayout) dialogEditItems
					.findViewById(R.id.rlMasterCash);
			rlChange = (RelativeLayout) dialogEditItems
					.findViewById(R.id.rlChange);
			btnNets = (Button) dialogEditItems.findViewById(R.id.btnNets);
			btnAme = (Button) dialogEditItems.findViewById(R.id.btnAme);
			btnMaster = (Button) dialogEditItems.findViewById(R.id.btnMaster);
			btnJCB = (Button) dialogEditItems.findViewById(R.id.btnJCB);
			btnVisa = (Button) dialogEditItems.findViewById(R.id.btnVisa);
			btnDinner = (Button) dialogEditItems.findViewById(R.id.btnDinner);
			btnCash = (Button) dialogEditItems.findViewById(R.id.btnCash);
			btnConfirm = (Button) dialogEditItems.findViewById(R.id.btnConfirm);
			btnConfirmAndRe = (Button) dialogEditItems
					.findViewById(R.id.btnConfirmAndRe);
			tvTotalValue = (TextView) dialogEditItems
					.findViewById(R.id.tvTotalValue);
			tvCash = (TextView) dialogEditItems.findViewById(R.id.tvCash);
			tvCashValue = (TextView) dialogEditItems
					.findViewById(R.id.tvCashValue);
			tvMasterCash = (TextView) dialogEditItems
					.findViewById(R.id.tvMasterCash);
			tvMasterCashValue = (TextView) dialogEditItems
					.findViewById(R.id.tvMasterCashValue);
			tvChange = (TextView) dialogEditItems.findViewById(R.id.tvChange);
			tvrlChangeValue = (TextView) dialogEditItems
					.findViewById(R.id.tvrlChangeValue);
			btnSeven = (Button) dialogEditItems.findViewById(R.id.btnSeven);
			btnOne = (Button) dialogEditItems.findViewById(R.id.btnOne);
			btnTwo = (Button) dialogEditItems.findViewById(R.id.btnTwo);
			btnThree = (Button) dialogEditItems.findViewById(R.id.btnThree);
			btnFour = (Button) dialogEditItems.findViewById(R.id.btnFour);
			btnFive = (Button) dialogEditItems.findViewById(R.id.btnFive);
			btnSix = (Button) dialogEditItems.findViewById(R.id.btnSix);
			btnEight = (Button) dialogEditItems.findViewById(R.id.btnEight);
			btnNine = (Button) dialogEditItems.findViewById(R.id.btnNine);
			btnZero = (Button) dialogEditItems.findViewById(R.id.btnZero);
			btnDot = (Button) dialogEditItems.findViewById(R.id.btnDot);
			btnDelete = (Button) dialogEditItems.findViewById(R.id.btnDelete);

			btnNets.setOnClickListener(this);
			btnDelete.setOnClickListener(this);
			btnAme.setOnClickListener(this);
			btnMaster.setOnClickListener(this);
			btnJCB.setOnClickListener(this);
			btnVisa.setOnClickListener(this);
			btnDinner.setOnClickListener(this);
			btnCash.setOnClickListener(this);
			btnConfirm.setOnClickListener(this);
			btnConfirmAndRe.setOnClickListener(this);
			btnSeven.setOnClickListener(this);
			btnOne.setOnClickListener(this);
			btnTwo.setOnClickListener(this);
			btnThree.setOnClickListener(this);
			btnFour.setOnClickListener(this);
			btnFive.setOnClickListener(this);
			btnSix.setOnClickListener(this);
			btnEight.setOnClickListener(this);
			btnNine.setOnClickListener(this);
			btnZero.setOnClickListener(this);
			btnDot.setOnClickListener(this);
			tvTotalValue.setText(total);
			((CustomFragmentActivity) ac).setWidthHeight(btnConfirmAndRe, 13.5,
					13.5);

			tvCashValue.addTextChangedListener(new TextWatcher() {
				public void afterTextChanged(Editable s) {
					Double d = -1.0;
					try {
						d = Double.parseDouble(tvTotalValue.getText()
								.toString());
					} catch (Exception e) {
						// TODO: handle exception
					}
					Double d1 = -1.0;
					try {
						d1 = Double.parseDouble(tvCashValue.getText()
								.toString());
					} catch (Exception e) {
						// TODO: handle exception
					}

					Double d2 = d - d1;
					DecimalFormat df = new DecimalFormat("####0.00");

					tvMasterCashValue.setText("" + df.format(d2));

					if (d2 < 0) {
						tvChange.setText("" + df.format(d2));
						tvMasterCashValue.setText("0.00");
					}
				}

				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
				}

				public void onTextChanged(CharSequence s, int start,
						int before, int count) {

				}
			});
			((CustomFragmentActivity) ac).setWidthHeight(rlAll, 1.7, 1.6);
			// ((CustomFragmentActivity) ac).setWidth(rlAll, 1.7);
			// tvDeleteItem.setOnClickListener(this);

			// tvCash.setOnHoverListener(new OnHoverListener() {
			//
			// @Override
			// public boolean onHover(View arg0, MotionEvent arg1) {
			// Toast.makeText(ac, "aaaaa", Toast.LENGTH_SHORT).show();
			// return false;
			// }
			// });
			// tvCash.setOnDragListener(new OnDragListener() {
			//
			// @Override
			// public boolean onDrag(View arg0, DragEvent arg1) {
			// Toast.makeText(ac, "OnDragListener", Toast.LENGTH_SHORT).show();
			// return false;
			// }
			// });
			// tvCash.setOnTouchListener(new OnTouchListener() {
			//
			// @Override
			// public boolean onTouch(View arg0, MotionEvent arg1) {
			// Toast.makeText(ac, "OnTouchListener", Toast.LENGTH_SHORT).show();
			// return false;
			// }
			// });
			// tvCashValue.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View arg0) {
			// // TODO Auto-generated method stub
			// tvCashValue.setText("");
			// isCashValue = true;
			// isMasterValue = false;
			// numRed = "0";
			// }
			// });
			// tvMasterCashValue.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View arg0) {
			// // TODO Auto-generated method stub
			// tvMasterCashValue.setText("");
			// numRed = "0";
			// }
			// });

			dialogEditItems.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					isShowDialog = false;
				}
			});

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
				String space = "";
				int lentemp = total.length();
				int tempneed = 27 - lentemp;
				for (int i = 0; i < tempneed; i++) {
					space += " ";
				}
				String str1 = "TOTAL AMOUNT:";
				mOutputStream_V.write(str1.getBytes());
				mOutputStream_V.write(space.getBytes());
				mOutputStream_V.write(total.getBytes());

				mOutputStream_V.write('\r');
				mOutputStream_V.write('\n');

				mApplication.closeSerialPort();
			} catch (Exception e) {
				// Toast.makeText(MainActivity.this, e.toString(),
				// Toast.LENGTH_LONG).show();
				// throw new RuntimeException(e);
			}
			// EO VFD

			dialogEditItems.show();
		}

	}

	private String numRed = "";
	private int a = 0;
	private boolean isTv = false;

	@Override
	public void onClick(View v) {
		String text1 = tvCash.getText().toString();
		String text2 = tvMasterCash.getText().toString();
		if (v == btnNets) {
			if (!TextUtils.isEmpty(tvCashValue.getText().toString())) {
				numRed = "";
				if (a < 2) {
					if (!isTv) {
						tvCash.setText("NETS");
						isTv = true;
					} else {
						tvMasterCash.setText("NETS");
					}
					a++;
				}
				change();
			}
		}
		if (v == btnAme) {
			if (!TextUtils.isEmpty(tvCashValue.getText().toString())) {
				numRed = "";
				if (a < 2) {
					if (!isTv) {
						tvCash.setText("AMEX");
						isTv = true;
					} else {
						tvMasterCash.setText("AMEX");
					}
					a++;
				}
				change();
			}
		}
		if (v == btnMaster) {
			if (!TextUtils.isEmpty(tvCashValue.getText().toString())) {
				numRed = "";
				if (a < 2) {
					if (!isTv) {
						tvCash.setText("MASTER");
						isTv = true;
					} else {
						tvMasterCash.setText("MASTER");
					}
					a++;
				}
				change();
			}
		}
		if (v == btnJCB) {
			if (!TextUtils.isEmpty(tvCashValue.getText().toString())) {
				numRed = "";
				if (a < 2) {
					if (!isTv) {
						tvCash.setText("JCB");
						isTv = true;
					} else {
						tvMasterCash.setText("JCB");
					}
					a++;
				}
				change();
			}
		}
		if (v == btnVisa) {
			if (!TextUtils.isEmpty(tvCashValue.getText().toString())) {
				numRed = "";
				if (a < 2) {
					if (!isTv) {
						tvCash.setText("VISA");
						isTv = true;
					} else {
						tvMasterCash.setText("VISA");
					}
					a++;
				}
				change();
			}
		}
		if (v == btnDinner) {
			if (!TextUtils.isEmpty(tvCashValue.getText().toString())) {
				numRed = "";
				if (a < 2) {
					if (!isTv) {
						tvCash.setText("DINERS");
						isTv = true;
					} else {
						tvMasterCash.setText("DINERS");
					}
					a++;
				}
				change();
			}
		}
		if (v == btnCash) {
			if (!TextUtils.isEmpty(tvCashValue.getText().toString())) {

				numRed = "";
				if (a < 2) {
					if (!isTv) {
						tvCash.setText("CASH");
						isTv = true;
					} else {
						tvMasterCash.setText("CASH");
					}
					a++;
				}
				change();
			}
		}
		if (v == btnConfirm) {
			if (text1.length() < 1 && text2.length() <1) {
				Toast.makeText(ac, "Choosen Pay Mode", Toast.LENGTH_SHORT)
						.show();
			} else {
				if (PosApp.listOrderData.size() != 0) {
					if (Utilities.getGlobalVariable(ac).isHoldPaid) {
						PayModel up = new PayModel();
						up.setTotal_amount(totala);
						up.setStatus("0");
						up.setNote(MainActivity.note);
						up.setPercentDis(DialogBillDiscount.percentDis);
						up.setDollarDis(MainActivity.btnDisCountValue.getText()
								.toString());
						up.setPaymentMode("3");
						up.setCreaditType("");
						up.setType1(tvCash.getText().toString());
						up.setType2(tvMasterCash.getText().toString());
						up.setType1Amount(tvCashValue.getText().toString());
						up.setType2Amount(tvMasterCashValue.getText()
								.toString());
						up.setChange(tvrlChangeValue.getText().toString());
						up.setPrintReciept("0");
						up.setSubTotal(MainActivity.btnSubTotalValue.getText()
								.toString());
						up.setIdUser(LoginDialog.idUser);
						// up.setGST(MainActivity.btnGSTValue.getText().toString()
						// +
						// "");
						up.setGST("0");
						int stt = ss.getStt() + 1;
						up.setReceipt_no(ComDDUtilities.getDateTime() + "-"
								+ stt);
						ss.saveStt(stt);
						PayDataSource dataSource = new PayDataSource(ac, ac);
						long id = dataSource.insert(up, "");
						long idpayment = dataSource.insertPayment(up, id);
						for (int i = 0; i < PosApp.listOrderData.size(); i++) {
							PayModel up1 = new PayModel();
							up1.setAmount(PosApp.listOrderData.get(i)
									.getAmount());
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
							up1.setPrice2(PosApp.listOrderData.get(i)
									.getPrice2());
							up1.setSpecialPrice(PosApp.listOrderData.get(i)
									.getSpecialPrice());
							up1.setUom(dataSource.loadUom(up1.getItemCode()));
							long a = dataSource.insertItems(up1);
							if (!up1.getQuantity().equals("-1")) {
								long a11 = dataSource.insertInventoryReport(
										up1, ac);
							}
							ItemsDataSource a1 = new ItemsDataSource(ac, ac);
							String str = PosApp.listOrderData.get(i)
									.getItemCode();
							if (str != "") {
								String debug = a1.Updatequantity(
										PosApp.listOrderData.get(i)
												.getItemCode(),
										PosApp.listOrderData.get(i)
												.getQualyti());
							}
						}
						SearchBillDataSource data = new SearchBillDataSource(
								ac, ac);
						int a = data.deleteItem(DialogSearchBill.idSale);
						int ak = data.deleteItemAll(DialogSearchBill.idSale);
						DialogSearchBill di = new DialogSearchBill();
						di.notifi();
						MainActivity.note = "";
						DialogBillDiscount.percentDis = "0";
						DialogBillDiscount.dollarDis = "0";

						MainActivity ma = new MainActivity();
						ma.openCashDrawer();
						PosApp.listOrderData.clear();
						ma.notifidataOrderList();
						Utilities.getGlobalVariable(ac).isHoldPaid = false;
					} else {
						PayModel up = new PayModel();
						up.setTotal_amount(totala);
						up.setStatus("0");
						up.setNote(MainActivity.note);
						up.setPercentDis(DialogBillDiscount.percentDis);
						up.setDollarDis(MainActivity.btnDisCountValue.getText()
								.toString());
						up.setPaymentMode("3");
						up.setCreaditType("");
						up.setType1(tvCash.getText().toString());
						up.setType2(tvMasterCash.getText().toString());
						up.setType1Amount(tvCashValue.getText().toString());
						up.setType2Amount(tvMasterCashValue.getText()
								.toString());
						up.setChange(tvrlChangeValue.getText().toString());
						up.setPrintReciept("0");
						up.setSubTotal(MainActivity.btnSubTotalValue.getText()
								.toString());
						up.setIdUser(LoginDialog.idUser);
						// up.setGST(MainActivity.btnGSTValue.getText().toString()
						// +
						// "");
						up.setGST("0");
						int stt = ss.getStt() + 1;
						up.setReceipt_no(ComDDUtilities.getDateTime() + "-"
								+ stt);
						ss.saveStt(stt);
						PayDataSource dataSource = new PayDataSource(ac, ac);
						long id = dataSource.insert(up, "");
						long idpayment = dataSource.insertPayment(up, id);
						for (int i = 0; i < PosApp.listOrderData.size(); i++) {
							PayModel up1 = new PayModel();
							up1.setAmount(PosApp.listOrderData.get(i)
									.getAmount());
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
							up1.setPrice2(PosApp.listOrderData.get(i)
									.getPrice2());
							up1.setSpecialPrice(PosApp.listOrderData.get(i)
									.getSpecialPrice());
							long a = dataSource.insertItems(up1);
							if (!up1.getQuantity().equals("-1")) {
								long a11 = dataSource.insertInventoryReport(
										up1, ac);
							}
							ItemsDataSource a1 = new ItemsDataSource(ac, ac);
							String str = PosApp.listOrderData.get(i)
									.getItemCode();
							if (str != "") {
								String debug = a1.Updatequantity(
										PosApp.listOrderData.get(i)
												.getItemCode(),
										PosApp.listOrderData.get(i)
												.getQualyti());
							}
						}

						MainActivity.note = "";
						DialogBillDiscount.percentDis = "0";
						DialogBillDiscount.dollarDis = "0";

						MainActivity ma = new MainActivity();
						ma.openCashDrawer();
						PosApp.listOrderData.clear();
						ma.notifidataOrderList();
					}

				}
				dialogEditItems.dismiss();
				ComDDUtilities.showWelcome();
			}

		}
		if (v == btnConfirmAndRe) {
			if (text1.length() < 1 && text2.length() <1) {
				Toast.makeText(ac, "Choosen Pay Mode", Toast.LENGTH_SHORT)
						.show();
			} else {
				if (PosApp.listOrderData.size() != 0) {
					if (Utilities.getGlobalVariable(ac).isHoldPaid) {
						PayModel up = new PayModel();
						up.setTotal_amount(totala);
						up.setStatus("0");
						up.setNote(MainActivity.note);
						up.setPercentDis(DialogBillDiscount.percentDis);
						up.setDollarDis(MainActivity.btnDisCountValue.getText()
								.toString());
						up.setPaymentMode("3");
						up.setCreaditType("");
						up.setType1(tvCash.getText().toString());
						up.setType2(tvMasterCash.getText().toString());
						up.setType1Amount(tvCashValue.getText().toString());
						up.setType2Amount(tvMasterCashValue.getText()
								.toString());
						up.setChange(tvrlChangeValue.getText().toString());
						up.setPrintReciept("1");
						up.setSubTotal(MainActivity.btnSubTotalValue.getText()
								.toString());
						// up.setGST(MainActivity.btnGSTValue.getText().toString()
						// +
						// "");
						up.setGST("0");
						up.setIdUser(LoginDialog.idUser);
						int stt = ss.getStt() + 1;
						up.setReceipt_no(ComDDUtilities.getDateTime() + "-"
								+ stt);
						ss.saveStt(stt);
						PayDataSource dataSource = new PayDataSource(ac, ac);
						long id = dataSource.insert(up, "");
						long idpayment = dataSource.insertPayment(up, id);
						for (int i = 0; i < PosApp.listOrderData.size(); i++) {
							PayModel up1 = new PayModel();
							up1.setAmount(PosApp.listOrderData.get(i)
									.getAmount());
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
							up1.setPrice2(PosApp.listOrderData.get(i)
									.getPrice2());
							up1.setSpecialPrice(PosApp.listOrderData.get(i)
									.getSpecialPrice());
							up1.setUom(dataSource.loadUom(up1.getItemCode()));
							long a = dataSource.insertItems(up1);
							ItemsDataSource a1 = new ItemsDataSource(ac, ac);
							if (!up1.getQuantity().equals("-1")) {
								long a11 = dataSource.insertInventoryReport(
										up1, ac);
							}
							String str = PosApp.listOrderData.get(i)
									.getItemCode();
							if (str != "") {
								String debug = a1.Updatequantity(
										PosApp.listOrderData.get(i)
												.getItemCode(),
										PosApp.listOrderData.get(i)
												.getQualyti());
							}
						}
						SearchBillDataSource data = new SearchBillDataSource(
								ac, ac);
						int a = data.deleteItem(DialogSearchBill.idSale);
						int ak = data.deleteItemAll(DialogSearchBill.idSale);
						DialogSearchBill di = new DialogSearchBill();
						di.notifi();
						DialogBillDiscount.percentDis = "0";
						DialogBillDiscount.dollarDis = "0";

						MainActivity.mCashDrawer.openCashDrawer();
						PrinterFunctions.PrintSampleReceipt(ac, ac, "USB:", "",
								"Raster", ac.getResources(), "3inch (78mm)",
								up.getReceipt_no());
						PosApp.listOrderData.clear();
						MainActivity ma = new MainActivity();
						ma.notifidataOrderList();
						Utilities.getGlobalVariable(ac).isHoldPaid = false;
					} else {
						PayModel up = new PayModel();
						up.setTotal_amount(totala);
						up.setStatus("0");
						up.setNote(MainActivity.note);
						up.setPercentDis(DialogBillDiscount.percentDis);
						up.setDollarDis(MainActivity.btnDisCountValue.getText()
								.toString());
						up.setPaymentMode("3");
						up.setCreaditType("");
						up.setType1(tvCash.getText().toString());
						up.setType2(tvMasterCash.getText().toString());
						up.setType1Amount(tvCashValue.getText().toString());
						up.setType2Amount(tvMasterCashValue.getText()
								.toString());
						up.setChange(tvrlChangeValue.getText().toString());
						up.setPrintReciept("1");
						up.setSubTotal(MainActivity.btnSubTotalValue.getText()
								.toString());
						// up.setGST(MainActivity.btnGSTValue.getText().toString()
						// +
						// "");
						up.setGST("0");
						up.setIdUser(LoginDialog.idUser);
						int stt = ss.getStt() + 1;
						up.setReceipt_no(ComDDUtilities.getDateTime() + "-"
								+ stt);
						ss.saveStt(stt);
						PayDataSource dataSource = new PayDataSource(ac, ac);
						long id = dataSource.insert(up, "");
						long idpayment = dataSource.insertPayment(up, id);
						for (int i = 0; i < PosApp.listOrderData.size(); i++) {
							PayModel up1 = new PayModel();
							up1.setAmount(PosApp.listOrderData.get(i)
									.getAmount());
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
							up1.setPrice2(PosApp.listOrderData.get(i)
									.getPrice2());
							up1.setSpecialPrice(PosApp.listOrderData.get(i)
									.getSpecialPrice());
							long a = dataSource.insertItems(up1);
							ItemsDataSource a1 = new ItemsDataSource(ac, ac);
							if (!up1.getQuantity().equals("-1")) {
								long a11 = dataSource.insertInventoryReport(
										up1, ac);
							}
							String str = PosApp.listOrderData.get(i)
									.getItemCode();
							if (str != "") {
								String debug = a1.Updatequantity(
										PosApp.listOrderData.get(i)
												.getItemCode(),
										PosApp.listOrderData.get(i)
												.getQualyti());
							}
						}

						DialogBillDiscount.percentDis = "0";
						DialogBillDiscount.dollarDis = "0";

						MainActivity.mCashDrawer.openCashDrawer();
						PrinterFunctions.PrintSampleReceipt(ac, ac, "USB:", "",
								"Raster", ac.getResources(), "3inch (78mm)",
								up.getReceipt_no());
						PosApp.listOrderData.clear();
						MainActivity ma = new MainActivity();
						ma.notifidataOrderList();
					}

				}
				dialogEditItems.dismiss();
				ComDDUtilities.showWelcome();
			}
		}
		if (v == btnDot) {
			if (numRed.equals("")) {
				numRed = numRed + "0.";
			} else {
				numRed = numRed + ".";
			}
			change();
		}
		if (v == btnZero) {
			numRed = numRed + "0";
			if (!isTv) {
				tvCashValue.setText(numRed + "");
			} else {
				tvMasterCashValue.setText(numRed + "");
			}
			change();
		}
		if (v == btnNine) {
			numRed = numRed + "9";
			if (!isTv) {
				tvCashValue.setText(numRed + "");
			} else {
				tvMasterCashValue.setText(numRed + "");
			}
			change();
		}
		if (v == btnEight) {
			numRed = numRed + "8";
			if (!isTv) {
				tvCashValue.setText(numRed + "");
			} else {
				tvMasterCashValue.setText(numRed + "");
			}
			change();
		}
		if (v == btnSix) {
			numRed = numRed + "6";
			if (!isTv) {
				tvCashValue.setText(numRed + "");
			} else {
				tvMasterCashValue.setText(numRed + "");
			}
			change();
		}
		if (v == btnFive) {
			numRed = numRed + "5";
			if (!isTv) {
				tvCashValue.setText(numRed + "");
			} else {
				tvMasterCashValue.setText(numRed + "");
			}
			change();
		}
		if (v == btnFour) {
			numRed = numRed + "4";
			if (!isTv) {
				tvCashValue.setText(numRed + "");
			} else {
				tvMasterCashValue.setText(numRed + "");
			}
			change();
		}
		if (v == btnThree) {
			numRed = numRed + "3";
			if (!isTv) {
				tvCashValue.setText(numRed + "");
			} else {
				tvMasterCashValue.setText(numRed + "");
			}
			change();
		}
		if (v == btnTwo) {
			numRed = numRed + "2";
			if (!isTv) {
				tvCashValue.setText(numRed + "");
			} else {
				tvMasterCashValue.setText(numRed + "");
			}
			change();
		}

		if (v == btnOne) {
			numRed = numRed + "1";
			if (!isTv) {
				tvCashValue.setText(numRed + "");
			} else {
				tvMasterCashValue.setText(numRed + "");
			}
			change();
		}
		if (v == btnSeven) {
			numRed = numRed + "7";
			if (!isTv) {
				tvCashValue.setText(numRed + "");
			} else {
				tvMasterCashValue.setText(numRed + "");
			}
			change();
		}
		if (v == btnDelete) {
			tvCash.setText("");
			tvCashValue.setText("");
			tvMasterCash.setText("");
			tvMasterCashValue.setText("");
			tvChange.setText("");
			tvrlChangeValue.setText("");
			a = 0;
			isTv = false;
			numRed = "";

		}
	}

	public static double change = -1;
	public static double sumuser = -1;

	private void change() {
		double b;
		double c;
		try {
			if (TextUtils.isEmpty(tvCashValue.getText().toString())) {
				b = Double.parseDouble("0");

			} else {
				b = Double.parseDouble(tvCashValue.getText().toString());

			}
			if (TextUtils.isEmpty(tvMasterCashValue.getText().toString())) {
				c = Double.parseDouble("0");

			} else {
				c = Double.parseDouble(tvMasterCashValue.getText().toString());

			}
			sumuser = b + c;
			change = CalculationUtils.calculateChange(sumuser,
					Double.parseDouble(totala));
		} catch (Exception e) {
			change = -1;
		}

		tvChange.setText("Change:");
		tvrlChangeValue.setText(String.valueOf(change));
	}

	// private String cashAmount;
	// private String creditAmount;

	private String getCreadit() {
		return numRed;
		// if (tvCash.getText().toString().equals("Cash")) {
		// cashAmount = tvCashValue.getText().toString();
		// creditAmount = tvMasterCashValue.getText().toString();
		// return tvMasterCash.getText().toString();
		// } else {
		// cashAmount = tvMasterCashValue.getText().toString();
		// creditAmount = tvCashValue.getText().toString();
		// return tvCash.getText().toString();
		// }

	}

	private boolean check() {
		if (TextUtils.isEmpty(tvCashValue.getText().toString())) {
			return false;
		}
		if (TextUtils.isEmpty(tvCash.getText().toString())) {
			return false;
		}
		if (TextUtils.isEmpty(tvMasterCash.getText().toString())) {
			return false;
		}
		if (TextUtils.isEmpty(tvMasterCashValue.getText().toString())) {
			return false;
		}
		return true;
	}
}
