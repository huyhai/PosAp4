package com.pos.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pos.CustomFragmentActivity;
import com.pos.MainActivity;
import com.pos.PosApp;
import com.pos.R;
import com.pos.common.Utilities;
import com.pos.libs.ComDDUtilities;
import com.pos.libs.DatePickerDialogFragment;
import com.pos.libs.SessionManager;
import com.pos.libs.ViewUtils;
import com.pos.model.ExportInventoryModel;
import com.pos.model.ListOrderModel;
import com.pos.print.PrinterFunctions;
import com.pos.db.CompanyDataSource;
import com.pos.db.InventoryDataSource;
import com.pos.db.SaleReportDataSource;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.AlertDialog.Builder;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

public class DialogInventoryReport extends Activity implements OnClickListener,
		OnTimeSetListener, OnDateSetListener {

	private Button btnStockin;
	private Button btnStockin2;
	private Button btnStockout;
	public static Button btnbalance;
	private Button btnAu;
	private Button btnStockValue;
	private EditText btnmoving1;
	private EditText btnmoving2;

	private Button edFromday;
	private Button edFromTime;
	private Button edToday;
	private Button edToTime;
	private boolean checkpick = true;
	private Button btnSearch;
	private String todate;
	private String fromdate;
	private String totime;
	private String formdtime;
	private Button btnPrintA4;
	private Button btnPrint;
	private ImageView imgBack;
	CompanyDataSource company;
	// public static int blance=0;
	private static SessionManager ss;
	private ArrayList<ExportInventoryModel> list = new ArrayList<ExportInventoryModel>();
	// private ArrayList<ExportInventoryModel> list2 = new
	// ArrayList<ExportInventoryModel>();
	private ArrayList<ExportInventoryModel> listPrint = new ArrayList<ExportInventoryModel>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inventory_report);
		ss = new SessionManager(this);
		btnStockin = (Button) findViewById(R.id.btnOpenValue);
		btnStockin2 = (Button) findViewById(R.id.btnGrossValue);
		btnStockout = (Button) findViewById(R.id.btnItemSaleValue);
		btnbalance = (Button) findViewById(R.id.btnTotalCashValue);
		btnAu = (Button) findViewById(R.id.btnNumSaleValue);
		btnStockValue = (Button) findViewById(R.id.btnBillDisValue);
		btnmoving1 = (EditText) findViewById(R.id.btnItemi);
		btnmoving2 = (EditText) findViewById(R.id.btnItem2i);
		edFromday = (Button) findViewById(R.id.edFromday);
		edFromTime = (Button) findViewById(R.id.edFromTime);
		edToday = (Button) findViewById(R.id.edToday);
		edToTime = (Button) findViewById(R.id.edToTime);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnPrintA4 = (Button) findViewById(R.id.btnPrintA4);
		btnPrint = (Button) findViewById(R.id.btnPrint);
		imgBack = (ImageView) findViewById(R.id.imgBack);

		SaleReportDataSource a = new SaleReportDataSource(this, this);
		company = new CompanyDataSource(this, this);
		String stockin = a.loadStockin();

		String stockin2 = a.loadStockin2();
		String stockout = a.loadStockout();
		// String stockinhand = Double.parseDouble(stockin) +
		// Double.parseDouble(btnbalance.getText().toString())+"";
		String stockinhand = Double.parseDouble(stockin) + ss.getEND() + "";
		// String stockinhand = ((Double.parseDouble(stockin) + Double
		// .parseDouble(stockin2)) - Double.parseDouble(stockout)) + "";
		// String au = a.loadAu();
		String stockvalue = a.loadstockvalue();
		String moving1 = a.loadItemIName();
		String moving2 = a.loadItemIqty();

		btnStockin.setText(stockinhand.replace(".0", ""));
		btnStockin2.setText(stockin2.replace(".0", ""));
		btnStockout.setText(stockout.replace(".0", ""));

		int a1 = Integer.parseInt(stockinhand.replace(".0", ""));
		int b = Integer.parseInt(stockout.replace(".0", ""));
		double c = (a1 + Double.parseDouble(stockin2)) - b;

		btnbalance.setText("" + String.valueOf(c).replace(".0", ""));

		// btnStockValue
		// .setText(String.valueOf(c * Double.parseDouble(stockvalue)));
		btnStockValue.setText(stockvalue);
		DecimalFormat df = new DecimalFormat("0.00");
		double num = Double.parseDouble(stockvalue) / c;
		String avg = (df.format(num) + "");
		if (!avg.equals("NaN")) {
			// btnAu.setText(avg);
			btnAu.setText(df.format(Double.parseDouble(stockvalue) / c));
		} else {
			btnAu.setText("0");
		}

		Spanned s = Html.fromHtml(moving1);

		btnmoving1.setText(s);

		Spanned s1 = Html.fromHtml(moving2);

		btnmoving2.setText(s1);

		edFromday.setOnClickListener(this);
		edFromTime.setOnClickListener(this);
		btnPrint.setOnClickListener(this);
		btnPrintA4.setOnClickListener(this);
		edToday.setOnClickListener(this);
		edToTime.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		imgBack.setOnClickListener(this);
	}

	private int mdate;
	private int mmonth;
	private int myear;
	int hour;
	int minute;

	@Override
	public void onClick(View v) {
		if (v == btnSearch) {
			if (edFromday.getText().toString().equals("")
					|| edToday.getText().toString().equals("")
					|| edToday.getText().toString().equals("")
					|| edToTime.getText().toString().equals("")) {
				Toast.makeText(this, "Please input date and time !!!",
						Toast.LENGTH_LONG).show();

			} else if (!edFromday.getText().toString().equals("")
					&& !edToday.getText().toString().equals("")
					&& !edToday.getText().toString().equals("")
					&& !edToTime.getText().toString().equals("")) {
				// SaleReportDataSource a = new SaleReportDataSource(this,
				// this);
				String form = fromdate + " " + formdtime;
				String to = todate + " " + totime;

				SaleReportDataSource a = new SaleReportDataSource(this, this);
				String stockin = a.loadStockind(form, to);
				String stockin2 = a.loadStockin2d(form, to);
				String stockout = a.loadStockoutd(form, to);
				String stockinhand = Double.parseDouble(stockin) + "";
				// String stockinhand = ((Double.parseDouble(stockin) + Double
				// .parseDouble(stockin2)) - Double.parseDouble(stockout)) + "";
				// String au = a.loadAu();
				String stockvalue = a.loadstockvalued(form, to);
				String moving1 = a.loadItemIName();
				String moving2 = a.loadItemIqty();

				btnStockin.setText(stockinhand.replace(".0", ""));
				btnStockin2.setText(stockin2.replace(".0", ""));
				btnStockout.setText(stockout.replace(".0", ""));

				int a1 = Integer.parseInt(stockinhand.replace(".0", ""));
				int b = Integer.parseInt(stockout.replace(".0", ""));
				double c = (a1 + Double.parseDouble(stockin2)) - b;

				btnbalance.setText("" + String.valueOf(c).replace(".0", ""));

				btnStockValue.setText(stockvalue);
				DecimalFormat df = new DecimalFormat("0.00");
				double num = Double.parseDouble(stockvalue) / c;
				String avg = (df.format(num) + "");
				btnAu.setText(avg);
				if (!avg.equals("NaN")) {
					// btnAu.setText(avg);
					btnAu.setText(avg);
					// btnAu.setText(df.format(Double.parseDouble(stockvalue) /
					// c));
				} else {
					btnAu.setText("0");
				}

				Spanned s = Html.fromHtml(moving1);

				btnmoving1.setText(s);

				Spanned s1 = Html.fromHtml(moving2);

				btnmoving2.setText(s1);
				// String stockin = a.loadStockind(form, to);
				// String stockin2 = a.loadStockin2d(form, to);
				// String stockout = a.loadStockoutd(form, to);
				// String au = a.loadAud(form, to);
				// String stockvalue = a.loadstockvalued(form, to);
				// String moving1 = a.loadItemIName();
				// String moving2 = a.loadItemIqty();
				//
				// btnStockin.setText(stockin.replace(".0", ""));
				// btnStockin2.setText(stockin2.replace(".0", ""));
				// btnStockout.setText(stockout.replace(".0", ""));
				//
				// int a1 = Integer.parseInt(stockin.replace(".0", ""));
				// int b = Integer.parseInt(stockout.replace(".0", ""));
				// int c = a1 - b;
				//
				// btnbalance.setText("" + c);
				// btnAu.setText(au);
				// btnStockValue.setText(stockvalue);
				//
				// Spanned s = Html.fromHtml(moving1);
				//
				// btnmoving1.setText(s);
				//
				// Spanned s1 = Html.fromHtml(moving2);
				//
				// btnmoving2.setText(s1);
			}
		}

		if (v == btnPrint) {
			// a2.PrintPosSaleEndShift(this, this, "USB:", "","1");
			PrinterFunctions.PrintItemSaleEndShift(this, this, "USB:", "", "1");
		}
		if (v == btnPrintA4) {
			InventoryDataSource da = new InventoryDataSource(
					DialogInventoryReport.this, DialogInventoryReport.this);
			String form = fromdate + " " + formdtime;
			String to = todate + " " + totime;

			list = da.loadInventoryReport(form, to);
			if (form.equals("null null") || to.equals("null null")) {
				SaleReportDataSource a = new SaleReportDataSource(
						DialogInventoryReport.this, DialogInventoryReport.this);

				final Calendar c = Calendar.getInstance();
				int mdate = c.get(Calendar.DATE);
				int mmonth = c.get(Calendar.MONTH);
				int myear = c.get(Calendar.YEAR);
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int minute = c.get(Calendar.MINUTE);
				// form = a.loadShiftDate();
				form = ss.getENDDATE();
				to = myear + "-" + mmonth + "-" + mdate + " " + hour + ":"
						+ minute + ":" + "00";
			}
			generatePDF(form, to);
		}
		if (v == edFromday) {
			checkpick = true;
			final Calendar c = Calendar.getInstance();
			mdate = c.get(Calendar.DATE);
			mmonth = c.get(Calendar.MONTH);
			myear = c.get(Calendar.YEAR);
			DatePickerDialog d = new DatePickerDialog(this, this, myear,
					mmonth, mdate);
			d.show();
		}
		if (v == edFromTime) {
			checkpick = true;
			final Calendar c = Calendar.getInstance();
			hour = c.get(Calendar.HOUR_OF_DAY);
			minute = c.get(Calendar.MINUTE);
			mdate = c.get(Calendar.DATE);
			mmonth = c.get(Calendar.MONTH);
			myear = c.get(Calendar.YEAR);
			TimePickerDialog tm = new TimePickerDialog(this, this, hour,
					minute, true);
			tm.show();
		}
		if (v == edToday) {
			checkpick = false;
			final Calendar c1 = Calendar.getInstance();
			mdate = c1.get(Calendar.DATE);
			mmonth = c1.get(Calendar.MONTH);
			myear = c1.get(Calendar.YEAR);
			DatePickerDialog d1 = new DatePickerDialog(this, this, myear,
					mmonth, mdate);
			d1.show();
		}
		if (v == edToTime) {
			checkpick = false;
			final Calendar c1 = Calendar.getInstance();
			hour = c1.get(Calendar.HOUR_OF_DAY);
			minute = c1.get(Calendar.MINUTE);
			mdate = c1.get(Calendar.DATE);
			mmonth = c1.get(Calendar.MONTH);
			myear = c1.get(Calendar.YEAR);
			TimePickerDialog tm1 = new TimePickerDialog(this, this, hour,
					minute, true);
			tm1.show();
		}
		if (v == imgBack) {
			DialogInventoryReport.this.finish();
		}

	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		String month = String.valueOf(monthOfYear + 1);
		String month2;
		if (month.length() == 1) {
			month2 = "0" + month;
		} else {
			month2 = String.valueOf(monthOfYear + 1);
		}
		if (checkpick) {
			edFromday.setText(dayOfMonth + "/" + month2 + "/" + year);
			fromdate = year + "-" + month2 + "-" + dayOfMonth;
		} else {
			edToday.setText(dayOfMonth + "/" + month2 + "/" + year);
			todate = year + "-" + month2 + "-" + dayOfMonth;
		}

	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		if (checkpick) {
			formdtime = "";
			if (hourOfDay < 10) {

				formdtime += "0" + hourOfDay + ":";
			} else {

				formdtime += hourOfDay + ":";
			}
			if (minute < 10) {

				formdtime += "0" + minute + ":00";
			} else {

				formdtime += minute + ":00";
			}
			edFromTime.setText(formdtime);
		} else {
			totime = "";
			if (hourOfDay < 10) {

				totime += "0" + hourOfDay + ":";
			} else {

				totime += hourOfDay + ":";
			}
			if (minute < 10) {

				totime += "0" + minute + ":00";
			} else {

				totime += minute + ":00";
			}
			edFromTime.setText(formdtime);

			edToTime.setText(totime);

		}

	}

	PdfPTable table;
	private int num1 = 0;
	private boolean oneTime = false;
	private BaseFont bfBold;

	private void initializeFonts() {

		try {
			bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD,
					BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean isOne=false;
	private  String stockInhand="0";
	private void generatePDF(String form, String to) {
		num1 = 0;
		initializeFonts();
		final String APPLICATION_PACKAGE_NAME = this.getBaseContext()
				.getPackageName();
		File path = new File(Environment.getExternalStorageDirectory(),
				APPLICATION_PACKAGE_NAME);
		if (!path.exists()) {
			path.mkdir();
		}
		File file = new File(path, "InventoryReport.pdf");
		// create a new document
		Document document = new Document(PageSize.A4.rotate());
		SaleReportDataSource a = new SaleReportDataSource(this, this);
		try {

			PdfWriter docWriter = PdfWriter.getInstance(document,
					new FileOutputStream(file));
			document.open();

			PdfContentByte cb = docWriter.getDirectContent();
			// initialize fonts for text printing
			initializeFonts();

			// the company logo is stored in the assets which is read only
			// get the logo and print on the document
			// InputStream inputStream = getAssets().open("a.jpg");
			// Bitmap bmp = BitmapFactory.decodeStream(inputStream);
			// ByteArrayOutputStream stream = new ByteArrayOutputStream();
			// bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			// Image companyLogo = Image.getInstance(stream.toByteArray());
			// companyLogo.setAbsolutePosition(25, 200);
			// companyLogo.scalePercent(25);
			// document.add(companyLogo);

			// creating a sample invoice with some customer data
			createTitle(cb, 350, 550, company.loadCName());

			createHeadings(cb, 30, 535, "ADD: " + company.loadCAddress());
			createHeadings(cb, 30, 520, "TEL: " + company.loadCPhone());
			createHeadings(cb, 30, 505, "WEB: " + company.loadCWebsite());
			createHeadings(cb, 30, 490, "GENERAL INVENTORY REPORT");
			createHeadings(cb, 30, 475, "FROM " + form + " TO " + to);
			createHeadings(cb, 500, 520, "");
			createHeadings(cb, 500, 520, ComDDUtilities.getDateTimePrint());
			createHeadings(cb, 500, 505, a.loadUserName());

			// list all the products sold to the customer
			float[] columnWidths = { 1.5f, 1.5f, 2f, 4f, 1.5f, 1.5f, 1.5f,
					1.5f, 1.5f, 1.5f };
			// create PDF table with the given widths

			table = new PdfPTable(columnWidths);
			// set table width a percentage of the page width
			table.setTotalWidth(700f);

			PdfPCell cell = new PdfPCell(new Phrase("DATE"));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("GROUP"));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("ITEM CODE"));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("ITEM NAME"));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("STOCK IN HAND"));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("TOTAL IN"));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("TOTAL OUT"));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("BALANCE"));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("USERS"));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("COST PRICE"));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			// cell = new PdfPCell(new Phrase("UOM"));
			// cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			// table.addCell(cell);
			table.setHeaderRows(1);

			// DecimalFormat df = new DecimalFormat("0.00");
			for (int i = 0; i < list.size(); i++) {
				table.addCell(list.get(i).getDate());
				table.addCell(list.get(i).getGroup());
				table.addCell(list.get(i).getItemcode());
				table.addCell(list.get(i).getItemname());
		
				if (ss.getENDDATE().equals("")) {
					stockInhand = list.get(i).getStockinhand();
					// table.addCell(list.get(i).getStockinhand());
				} else {
						stockInhand = (Integer.parseInt(list.get(i).getStockinhand())
								+ Integer.parseInt(list.get(i).getBalance()) + "");
//						isOne=true;
//					}
//					else{
//						stockInhand = list.get(i).getStockinhand();
//					}
					
				}
				table.addCell(stockInhand);
				table.addCell(list.get(i).getTotalin());
				table.addCell(list.get(i).getTotalout());

				String blance = Integer.parseInt(stockInhand)
						+ Integer.parseInt(list.get(i).getTotalin())
						- Integer.parseInt(list.get(i).getTotalout()) + "";
				table.addCell(blance);
				// table.addCell(list.get(i).getBalance());
				table.addCell(list.get(i).getUsername());
				table.addCell(list.get(i).getCostprice());
				// table.addCell(list.get(i).getUom());

				num1++;
				if (num1 == 10) {

					num1 = 0;
					if (oneTime) {
						document.newPage();
						table.writeSelectedRows(0, -1, document.leftMargin(),
								550, docWriter.getDirectContent());
						table.deleteBodyRows();
					} else {
						table.writeSelectedRows(0, -1, document.leftMargin(),
								450, docWriter.getDirectContent());
						table.deleteBodyRows();

					}

					oneTime = true;
				}
				if (i == list.size() - 1) {
					if (i < 10) {
						table.writeSelectedRows(0, -1, document.leftMargin(),
								450, docWriter.getDirectContent());
						table.deleteBodyRows();
					} else {
						document.newPage();
						table.writeSelectedRows(0, -1, document.leftMargin(),
								550, docWriter.getDirectContent());
						table.deleteBodyRows();
					}

				}
			}

			// absolute location to print the PDF table from
			// document.newPage();
			// table.writeSelectedRows(0, -1, document.leftMargin(), 450,
			// docWriter.getDirectContent());

			// print the signature image along with the persons name
			// inputStream = getAssets().open("b.jpg");
			// bmp = BitmapFactory.decodeStream(inputStream);
			// stream = new ByteArrayOutputStream();
			// bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			// Image signature = Image.getInstance(stream.toByteArray());
			// signature.setAbsolutePosition(400f, 150f);
			// signature.scalePercent(25f);
			// document.add(signature);

			// createHeadings(cb, 450, 135, personName);
			// document.setPageSize(PageSize.A4.rotate());

			Intent target = new Intent(Intent.ACTION_VIEW);
			target.setDataAndType(Uri.fromFile(file), "application/pdf");
			target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

			Intent intent = Intent.createChooser(target, "Open File");
			try {
				startActivity(intent);
			} catch (ActivityNotFoundException e) {
				// Instruct the user to install a PDF reader here, or something
			}
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createTitle(PdfContentByte cb, float x, float y, String text) {

		cb.beginText();
		cb.setFontAndSize(bfBold, 15);
		cb.setTextMatrix(x, y);
		cb.showText(text.trim());
		cb.endText();

	}

	private void createHeadings(PdfContentByte cb, float x, float y, String text) {

		cb.beginText();
		cb.setFontAndSize(bfBold, 10);
		cb.setTextMatrix(x, y);
		cb.showText(text.trim());
		cb.endText();

	}

}
