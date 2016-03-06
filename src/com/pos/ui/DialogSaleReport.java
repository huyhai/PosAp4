package com.pos.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import com.pos.CustomFragmentActivity;
import com.pos.MainActivity;
import com.pos.PosApp;
import com.pos.R;
import com.pos.common.Utilities;
import com.pos.libs.ComDDUtilities;
import com.pos.libs.DatePickerDialogFragment;
import com.pos.libs.ViewUtils;
import com.pos.model.ListOrderModel;
import com.pos.print.PrinterFunctions;
import com.pos.db.CompanyDataSource;
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

public class DialogSaleReport extends Activity implements OnClickListener,
		OnTimeSetListener, OnDateSetListener {
	private Button OpenningCash;
	private Button GrossSale;
	private Button ItemSale;
	private Button TotalCash;
	private Button NumberOfSale;
	private Button ItemDiscount;
	private Button BillDiscount;
	private Button CashDrawer;
	private Button CardSale;
	private Button Unpaid;

	private Button btnEnd;
	private Button btnPrintA4;
	private Button btnPrint;
	private Button edFromday;
	private Button edFromTime;
    private String strend;
    private ImageView imBack;
    private Button edToday;
    private Button edToTime;
    private boolean checkpick=true; 
    private Button btnSearch;
    private String todate;
    private String fromdate;
    private String totime;
    private String formdtime;
    private Button Salereturn;
    
    private BaseFont bfBold;
	private int num = 0;
	private boolean oneTime = false;
	PdfPTable table1;
	CompanyDataSource company;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sale_report);
		btnEnd = (Button) findViewById(R.id.btnEnd);
		btnPrintA4 = (Button) findViewById(R.id.btnPrintA4);
		btnPrint = (Button) findViewById(R.id.btnPrint);
		edFromday = (Button) findViewById(R.id.edFromday);
		edFromTime = (Button) findViewById(R.id.edFromTime);

		SaleReportDataSource a = new SaleReportDataSource(this, this);
		company = new CompanyDataSource(this, this);
		OpenningCash = (Button) findViewById(R.id.btnOpenValue);
		GrossSale = (Button) findViewById(R.id.btnGrossValue);
		ItemSale = (Button) findViewById(R.id.btnItemSaleValue);
		TotalCash = (Button) findViewById(R.id.btnTotalCashValue);
		NumberOfSale = (Button) findViewById(R.id.btnNumSaleValue);
		ItemDiscount = (Button) findViewById(R.id.btnItemDisValue);
		BillDiscount = (Button) findViewById(R.id.btnBillDisValue);
		CashDrawer = (Button) findViewById(R.id.btnCashvl);
		CardSale = (Button) findViewById(R.id.btnCardsvl);
		Unpaid = (Button) findViewById(R.id.btnUnpaidvl);
        imBack =(ImageView) findViewById(R.id.imgBack);
		edToday=(Button)findViewById(R.id.edToday);
		edToTime=(Button)findViewById(R.id.edToTime);
        btnSearch=(Button)findViewById(R.id.btnSearch);
        Salereturn=(Button)findViewById(R.id.btnSaleRefundValue);
        
        String open = a.loadOpenCash();
		String grosssale = a.loadGrossSale();
		String itemsale = a.loadItemSale();
		String totalcash = a.loadTotalCash();
		String numberofsale = a.loadNumberOfSale();
		String ItemDiscount = a.loadItemDisccount();
		String BillDiscount = a.loadBillDisccount();
		String CardSale = a.loadCardSale();
		String Unpaid = a.loadUnPaid();
		String returnla=a.loadreturn();
		
		
		OpenningCash.setText(open);
		GrossSale.setText(grosssale);
		ItemSale.setText(itemsale);
		TotalCash.setText(totalcash);
		NumberOfSale.setText(numberofsale);
		this.ItemDiscount.setText(ItemDiscount);
		this.BillDiscount.setText(BillDiscount);
		this.CardSale.setText(CardSale);
		this.Unpaid.setText("$"+Unpaid);
		this.Salereturn.setText(returnla);
		Double dend=0.0;
		open = open.replace("$", "");
		totalcash = totalcash.replace("$", "");
		Double dopen = Double.parseDouble(open);
		Double dtotalcash = Double.parseDouble(totalcash);
		returnla=returnla.replace("-", "");
		returnla=returnla.replace("$", "");
		Double dreturnla=Double.parseDouble(returnla);
		dend = (dopen + dtotalcash)-dreturnla;

		strend = "" + dend;
		//this.CashDrawer.setText("$"+strend);
		
		
		this.CashDrawer.setText("$"+totalcash);
		TotalCash.setText("$"+strend);
		edFromday.setOnClickListener(this);
		edFromTime.setOnClickListener(this);
		btnEnd.setOnClickListener(this);
		btnPrint.setOnClickListener(this);
		btnPrintA4.setOnClickListener(this);
		imBack.setOnClickListener(this);
		edToday.setOnClickListener(this);
		edToTime.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
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
				Toast.makeText(
						this,
						"Please input date and time,the current report will be printed !",
						Toast.LENGTH_LONG).show();

			} else if (!edFromday.getText().toString().equals("")
					&& !edToday.getText().toString().equals("")
					&& !edToday.getText().toString().equals("")
					&& !edToTime.getText().toString().equals("")) {
				SaleReportDataSource a = new SaleReportDataSource(this, this);
				String form = fromdate + " " + formdtime;
				String to = todate + " " + totime;

				String open = "0.00";
				String grosssale = a.loadGrossSaled(form, to);
				String itemsale = a.loadItemSaled(form, to);
				String totalcash = a.loadTotalCashd(form, to);
				String numberofsale = a.loadNumberOfSaled(form, to);
				String ItemDiscount = a.loadItemDisccountd(form, to);
				String BillDiscount = a.loadBillDisccountd(form, to);
				String CardSale = a.loadCardSaled(form, to);
				String Unpaid = a.loadUnPaidd(form, to);
				String returnla = a.loadreturnd(form, to);

				OpenningCash.setText("0.00");
				GrossSale.setText(grosssale);
				ItemSale.setText(itemsale);
				TotalCash.setText(totalcash);
				NumberOfSale.setText(numberofsale);
				this.ItemDiscount.setText(ItemDiscount);
				this.BillDiscount.setText(BillDiscount);
				this.CardSale.setText(CardSale);
				this.Unpaid.setText("$"+Unpaid);
				this.Salereturn.setText(returnla);
				open = open.replace("$", "");
				totalcash = totalcash.replace("$", "");
				Double dend = 0.0;
				try {
					Double dopen = 0.00;
					Double dtotalcash = Double.parseDouble(totalcash);
					returnla = returnla.replace("-", "");
					returnla = returnla.replace("$", "");
					Double dreturnla = Double.parseDouble(returnla);
					dend = dtotalcash - dreturnla;
				} catch (Exception e) {

				}
				
				strend = "" + dend;
				this.CashDrawer.setText("$"+totalcash);
				TotalCash.setText("$"+dend);
				
				
			}
		}
		if (v == imBack) {
			DialogSaleReport.this.finish();
			//Intent i = new Intent(this, MainActivity.class);
			//startActivity(i);
		}
		if (v == btnEnd) {
			SaleReportDataSource a = new SaleReportDataSource(this, this);
			PrinterFunctions a2 = new PrinterFunctions();
			a2.PrintPosSaleEndShift(this, this, "USB:", "", "2");
			a2.PrintItemSaleEndShift(this, this, "USB:", "", "2");
			String strtemp=a.Endshift(strend);
			
			PosApp.listOrderData.clear();
			MainActivity m = new MainActivity();
			m.notifidataOrderList();
			DialogSaleReport.this.finish();
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
		}
		if (v == btnPrint) {
			PrinterFunctions a2 = new PrinterFunctions();
			a2.PrintPosSaleEndShift(this, this, "USB:", "", "1");
			a2.PrintItemSaleEndShift(this, this, "USB:", "", "1");
		}
		if (v == btnPrintA4) {
			if (edFromday.getText().toString().equals("")
					|| edToday.getText().toString().equals("")
					|| edToday.getText().toString().equals("")
					|| edToTime.getText().toString().equals("")) {
				Toast.makeText(this, "Please input date and time !!!",
				Toast.LENGTH_LONG).show();
				initializeFonts();
				final String APPLICATION_PACKAGE_NAME = this.getBaseContext()
						.getPackageName();
				File path = new File(Environment.getExternalStorageDirectory(),
						APPLICATION_PACKAGE_NAME);
				if (!path.exists()) {
					path.mkdir();
				}
				File file = new File(path, "SaleReport.pdf");
				// create a new document
				Document document = new Document(PageSize.A4.rotate());
				SaleReportDataSource a1=new SaleReportDataSource(this, this);
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String currentDateandTime = sdf.format(new Date());
				String form=a1.loadShiftDate();
				String to=currentDateandTime; 
				SaleReportDataSource a = new SaleReportDataSource(this, this);
				try {

					PdfWriter docWriter = PdfWriter.getInstance(document,
							new FileOutputStream(file));
					document.open();

					PdfContentByte cb = docWriter.getDirectContent();
					// initialize fonts for text printing
					initializeFonts();

					createTitle(cb, 350, 550, company.loadCName());

					createHeadings(cb, 30, 535,
							"ADD: " + company.loadCAddress());
					createHeadings(cb, 30, 520, "TEL: " + company.loadCPhone());
					createHeadings(cb, 30, 505,
							"WEB: " + company.loadCWebsite());
					createHeadings(cb, 30, 490, "GENERAL SALE REPORT");
					createHeadings(cb, 30, 475, "FROM " + form + " TO " + to);
					createHeadings(cb, 500, 520,
							ComDDUtilities.getDateTimePrint());
					createHeadings(cb, 500, 505, a.loadUserName());

					// list all the products sold to the customer
					float[] columnWidths = { 1.5f, 5f, 2f };
					// create PDF table with the given widths

					table1 = new PdfPTable(columnWidths);
					// set table width a percentage of the page width
					table1.setTotalWidth(700f);

					PdfPCell cell = new PdfPCell(new Phrase("ITEM"));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table1.addCell(cell);
					cell = new PdfPCell(new Phrase("NAME"));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table1.addCell(cell);
					cell = new PdfPCell(new Phrase("AMOUNT SGD"));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table1.addCell(cell);
					table1.setHeaderRows(1);
					
					table1.addCell(" ");
					table1.addCell(" ");
					table1.addCell(" ");
					
					table1.addCell("1");
					table1.addCell("GROSS SALE");
					table1.addCell(a.loadGrossSale().replace("$", ""));
					
					String open = a.loadOpenCash();
					String grosssale = a.loadGrossSale();
					String itemsale = a.loadItemSale();
					String totalcash = a.loadTotalCash();
					String numberofsale = a.loadNumberOfSale();
					String ItemDiscount = a.loadItemDisccount();
					String BillDiscount = a.loadBillDisccount();
					String CardSale = a.loadCardSale().replace("$", "");
					String Unpaid = a.loadUnPaid();
					String returnla=a.loadreturn();
					
					
					
					Double dend=0.0;
					open = open.replace("$", "");
					totalcash = totalcash.replace("$", "");
					Double dopen = Double.parseDouble(open);
					Double dtotalcash = Double.parseDouble(totalcash);
					returnla=returnla.replace("-", "");
					returnla=returnla.replace("$", "");
					Double dreturnla=Double.parseDouble(returnla);
					dend = (dopen + dtotalcash)-dreturnla;

					strend = "" + dend;
					
					Double dcardsale=Double.parseDouble(CardSale);
					Double dend2=0.0;
					dend2=dtotalcash+dcardsale;
					
					
					table1.addCell("2");
					table1.addCell("TOTAL ITEM SALE");
					table1.addCell(a.loadItemSale().replace("$", ""));
					
					table1.addCell("3");
					table1.addCell("TOTAL ITEM DISCOUNT");
					table1.addCell(a.loadItemDisccount().replace("$", ""));
					
					table1.addCell("4");
					table1.addCell("TOTAL BILL DISCOUNT");
					table1.addCell(a.loadBillDisccount().replace("$", ""));
					
					table1.addCell("5");
					table1.addCell("RETURN GOODS");
					table1.addCell(a.loadreturn().replace("$", ""));
					
					table1.addCell("6");
					table1.addCell("GST AMOUNT");
					table1.addCell("0.00");
					
					table1.addCell("7");
					table1.addCell("TOTAL CASH RECEIVED");
					table1.addCell(totalcash);
					
					table1.addCell("8");
					table1.addCell("CASH FLOAT");
					table1.addCell(a.loadOpenCash().replace("$", ""));
					
					table1.addCell("9");
					table1.addCell("TOTAL CASH DRAWER");
					table1.addCell(""+dend);
					
					table1.addCell("10");
					table1.addCell("TOTAL CARD RECEIVED");
					table1.addCell(CardSale.replace("$", ""));
					
					table1.addCell("");
					table1.addCell("MASTER");
					table1.addCell(a.loadCardSaledMaster1(form, to).replace(
							"$", ""));

					table1.addCell("");
					table1.addCell("NETS");
					table1.addCell(a.loadCardSaledNETS(form, to).replace("$",
							""));

					table1.addCell("");
					table1.addCell("VISA");
					table1.addCell(a.loadCardSaledVISA(form, to).replace("$",
							""));

					table1.addCell("");
					table1.addCell("AMEX");
					table1.addCell(a.loadCardSaledDINERS(form, to).replace("$",
							""));

					table1.addCell("");
					table1.addCell("JCB");
					table1.addCell(a.loadCardSaledJCB(form, to)
							.replace("$", ""));

					table1.addCell("");
					table1.addCell("DINERS");
					table1.addCell(a.loadCardSaledAMEX(form, to).replace("$",
							""));

					table1.addCell("11");
					table1.addCell("DEBT");
					table1.addCell(a.loadUnPaidd(form, to));
					
					table1.addCell(" ");
					table1.addCell(" ");
					table1.addCell(" ");
					
					table1.addCell("");
					table1.addCell("TOTAL RECEIVEABLE CASH & CARD");
					table1.addCell(""+dend2);

					table1.writeSelectedRows(0, -1, document.leftMargin(), 450,
							docWriter.getDirectContent());

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

			} else if (!edFromday.getText().toString().equals("")
					&& !edToday.getText().toString().equals("")
					&& !edToday.getText().toString().equals("")
					&& !edToTime.getText().toString().equals("")) {
				initializeFonts();
				final String APPLICATION_PACKAGE_NAME = this.getBaseContext()
						.getPackageName();
				File path = new File(Environment.getExternalStorageDirectory(),
						APPLICATION_PACKAGE_NAME);
				if (!path.exists()) {
					path.mkdir();
				}
				File file = new File(path, "SaleReport.pdf");
				// create a new document
				Document document = new Document(PageSize.A4.rotate());
				String form = fromdate + " " + formdtime;
				String to = todate + " " + totime;
				SaleReportDataSource a = new SaleReportDataSource(this, this);
				try {

				PdfWriter docWriter = PdfWriter.getInstance(document,
						new FileOutputStream(file));
				document.open();

				PdfContentByte cb = docWriter.getDirectContent();
				// initialize fonts for text printing
				initializeFonts();

					createHeadings(cb, 30, 520, "");
					createHeadings(cb, 30, 505, "");
					createHeadings(cb, 30, 490, "GENERAL SALE REPORT");
					createHeadings(cb, 30, 475, "FROM " + form + " TO " + to);
					createHeadings(cb, 500, 520,
							ComDDUtilities.getDateTimePrint());
					createHeadings(cb, 500, 505, a.loadUserName());

				// list all the products sold to the customer
				float[] columnWidths = { 1.5f, 5f, 2f };
				// create PDF table with the given widths

				table1 = new PdfPTable(columnWidths);
				// set table width a percentage of the page width
				table1.setTotalWidth(700f);

				PdfPCell cell = new PdfPCell(new Phrase("ITEM"));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new PdfPCell(new Phrase("NAME"));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new PdfPCell(new Phrase("AMOUNT SGD"));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				table1.setHeaderRows(1);
				
				table1.addCell(" ");
				table1.addCell(" ");
				table1.addCell(" ");
				
				table1.addCell("1");
				table1.addCell("GROSS SALE");
				table1.addCell(a.loadGrossSaled(form,to).replace("$", ""));

					String open = "0.00";
				String grosssale = a.loadGrossSaled(form,to);
				String itemsale = a.loadItemSaled(form,to);
				String totalcash = a.loadTotalCashd(form, to);
				String numberofsale = a.loadNumberOfSaled(form, to);
				String ItemDiscount = a.loadItemDisccountd(form, to);
				String BillDiscount = a.loadBillDisccountd(form, to);
				String CardSale = a.loadCardSaled(form, to);
				String Unpaid = a.loadUnPaidd(form, to);
				String returnla=a.loadreturnd(form, to);
				open = open.replace("$", "");
				totalcash = totalcash.replace("$", "");
				CardSale = CardSale.replace("$", "");
				
				Double dend=0.0;
				Double dend2=0.0;
				try
				{
					Double dopen = Double.parseDouble(open);
					Double dtotalcash = Double.parseDouble(totalcash);
					returnla=returnla.replace("-", "");
					returnla=returnla.replace("$", "");
					Double dreturnla=Double.parseDouble(returnla);
					dend = (dopen + dtotalcash)-dreturnla;	
				
				
				
				Double dtotalcard = Double.parseDouble(CardSale);

						dend2 = dend + dtotalcard;
					} catch (Exception e) {

					}


				
				
				strend = "" + dend;
				
				
				
				table1.addCell("2");
				table1.addCell("TOTAL ITEM SALE");
				table1.addCell(a.loadItemSaled(form,to).replace("$", ""));
				
				table1.addCell("3");
				table1.addCell("TOTAL ITEM DISCOUNT");
				table1.addCell(a.loadItemDisccountd(form, to).replace("$", ""));
				
				table1.addCell("4");
				table1.addCell("TOTAL BILL DISCOUNT");
				table1.addCell(a.loadBillDisccountd(form, to).replace("$", ""));
				
				table1.addCell("5");
				table1.addCell("RETURN GOODS");
				table1.addCell(a.loadreturnd(form, to).replace("$", ""));
				
				table1.addCell("6");
				table1.addCell("GST AMOUNT");
				table1.addCell("0.00");
				
				table1.addCell("7");
				table1.addCell("TOTAL CASH RECEIVED");
				table1.addCell(strend.replace("$", ""));
				
				table1.addCell("8");
					table1.addCell("CASH FLOAT");
					table1.addCell("0.00");

					table1.addCell("9");
					table1.addCell("TOTAL CASH DRAWER");
					table1.addCell("0.00");

					table1.addCell("10");
					table1.addCell("TOTAL CARD RECEIVED");
					table1.addCell(a.loadCardSaled(form, to).replace("$", ""));

					table1.addCell("");
					table1.addCell("MASTER");
					table1.addCell(a.loadCardSaledMaster1(form, to).replace(
							"$", ""));

					table1.addCell("");
					table1.addCell("NETS");
					table1.addCell(a.loadCardSaledNETS(form, to).replace("$",
							""));

					table1.addCell("");
					table1.addCell("VISA");
					table1.addCell(a.loadCardSaledVISA(form, to).replace("$",
							""));

					table1.addCell("");
					table1.addCell("AMEX");
					table1.addCell(a.loadCardSaledAMEX(form, to).replace("$",
							""));

					table1.addCell("");
					table1.addCell("JCB");
					table1.addCell(a.loadCardSaledAMEX(form, to).replace("$",
							""));

					table1.addCell("");
					table1.addCell("DINERS");
					table1.addCell(a.loadCardSaledAMEX(form, to).replace("$",
							""));

					table1.addCell("11");
					table1.addCell("DEBT");
					table1.addCell(a.loadUnPaidd(form, to));

					table1.addCell(" ");
					table1.addCell(" ");
					table1.addCell(" ");

					table1.addCell("");
					table1.addCell("TOTAL RECEIVEABLE CASH & CARD");
					table1.addCell("" + dend2);

				table1.writeSelectedRows(0, -1, document.leftMargin(), 450,
						docWriter.getDirectContent());

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
			
		}
		if (v == edFromday) {
			checkpick=true;
			final Calendar c = Calendar.getInstance();
			mdate = c.get(Calendar.DATE);
			mmonth = c.get(Calendar.MONTH);
			myear = c.get(Calendar.YEAR);
			DatePickerDialog d = new DatePickerDialog(this, this, myear,
					mmonth, mdate);
			d.show();
		}
		if (v == edFromTime) {
			checkpick=true;
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
			checkpick=false;
			final Calendar c1 = Calendar.getInstance();
			mdate = c1.get(Calendar.DATE);
			mmonth = c1.get(Calendar.MONTH);
			myear = c1.get(Calendar.YEAR);
			DatePickerDialog d1 = new DatePickerDialog(this, this, myear,
					mmonth, mdate);
			d1.show();
		}if (v == edToTime) {
			checkpick=false;
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

	}
	
    
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		if (checkpick)
		{
			formdtime="";
			if(hourOfDay<10)
			{
				
				formdtime+="0"+hourOfDay + ":";
			}
			else
			{
				
				formdtime+=hourOfDay + ":";
			}
			if(minute<10)
			{
				
				formdtime+="0"+ minute+":00";
			}
			else
			{
				
				formdtime+=minute+":00";
			}
			edFromTime.setText(formdtime);
		}
		else
		{
			totime="";
			if(hourOfDay<10)
			{
				
				totime+="0"+hourOfDay + ":";
			}
			else
			{
				
				totime+=hourOfDay + ":";
			}
			if(minute<10)
			{
				
				totime+="0"+ minute+":00";
			}
			else
			{
				
				totime+=minute+":00";
			}
			edFromTime.setText(formdtime);
			
			
			
			
		edToTime.setText(totime);	
		
		}

	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		String month = String.valueOf(monthOfYear + 1);
		if (checkpick) {
			edFromday.setText(dayOfMonth + "/" + month + "/" + year);
			fromdate = year + "-" + month + "-" + dayOfMonth;
		} else {
			edToday.setText(dayOfMonth + "/" + month + "/" + year);
			todate = year + "-" + month + "-" + dayOfMonth;
		}
	}
	private void createHeadings(PdfContentByte cb, float x, float y, String text) {

		cb.beginText();
		cb.setFontAndSize(bfBold, 10);
		cb.setTextMatrix(x, y);
		cb.showText(text.trim());
		cb.endText();

	}

	private void createTitle(PdfContentByte cb, float x, float y, String text) {

		cb.beginText();
		cb.setFontAndSize(bfBold, 15);
		cb.setTextMatrix(x, y);
		cb.showText(text.trim());
		cb.endText();

	}

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

}
