package com.pos.ui;

import java.util.ArrayList;

import com.pos.CustomFragmentActivity;
import com.pos.MainActivity;
import com.pos.PosApp;
import com.pos.R;
import com.pos.adapter.UsersAdapter;
import com.pos.common.Utilities;
import com.pos.db.MainCateDataSource;
import com.pos.db.UsersDataSource;
import com.pos.model.ListOrderModel;
import com.pos.model.UsersModel;
import com.pos.db.SaleReportDataSource;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings.Global;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginDialog extends Activity implements OnClickListener {
	private Dialog dialogEditItems;
	private Boolean isShowDialog = false;
	private Activity ac;
	private RelativeLayout rlBig;
	private GridView gvUser;
	private ImageView imgCompanyLogo;
	private Button btnCompanyName;
	private RelativeLayout rlRes;
	private EditText edInput;
	private Button btnOk;
	private Button btnDel;
	public static ImageView imgAvartar;
	private EditText edUserName;
	private static ArrayList<UsersModel> list = new ArrayList<UsersModel>();
	UsersAdapter adapter;
	public static String linkImage;
	private Button btnCompany;
	private Button btnBack;

	public void showDialogSelectImg(Activity _ac) {
		ac = _ac;
		// custom dialog
		if (!isShowDialog) {
			isShowDialog = true;
			// imgSet = _imgSet;
			dialogEditItems = new Dialog(ac);
			dialogEditItems.getWindow();
			dialogEditItems.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialogEditItems.setContentView(R.layout.login_dialog);
			dialogEditItems.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			// dialog.setTitle("Title...");

			this.rlBig = (RelativeLayout) dialogEditItems
					.findViewById(R.id.rlBig);
			this.gvUser = (GridView) dialogEditItems.findViewById(R.id.gvUser);
			this.imgCompanyLogo = (ImageView) dialogEditItems
					.findViewById(R.id.imgCompanyLogo);
			this.btnCompanyName = (Button) dialogEditItems
					.findViewById(R.id.btnCompanyName);
			this.btnOk = (Button) dialogEditItems.findViewById(R.id.btnOk);
			this.btnDel = (Button) dialogEditItems.findViewById(R.id.btnDel);
			this.btnCompany = (Button) dialogEditItems
					.findViewById(R.id.btnCompanyName);
			btnBack = (Button) dialogEditItems.findViewById(R.id.btnBack);
			rlRes = (RelativeLayout) dialogEditItems.findViewById(R.id.rlRes);
			edInput = (EditText) dialogEditItems.findViewById(R.id.edInput);

			edUserName = (EditText) dialogEditItems
					.findViewById(R.id.edUserName);
			imgAvartar = (ImageView) dialogEditItems
					.findViewById(R.id.imgAvartar);

			((CustomFragmentActivity) ac).setWidthHeight(rlBig, 1.3, 1.4);
			// ((CustomFragmentActivity) ac).setWidthHeight(imgCompanyLogo, 8,
			// 5);
			((CustomFragmentActivity) ac).setWidthHeight(imgAvartar, 8, 5);
			adapter = new UsersAdapter(ac, list);
			gvUser.setAdapter(adapter);
			loadVattu();

			// gvUser.setOnItemSelectedListener(this);
			imgCompanyLogo.setOnClickListener(this);
			btnOk.setOnClickListener(this);
			btnDel.setOnClickListener(this);
			imgAvartar.setOnClickListener(this);
			btnCompany.setOnClickListener(this);
			btnBack.setOnClickListener(this);
			dialogEditItems.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					isShowDialog = false;
				}
			});
			imgAvartar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					((CustomFragmentActivity) ac).getPicture();

				}
			});
			dialogEditItems.setCancelable(false);
			dialogEditItems.setCanceledOnTouchOutside(false);
			dialogEditItems.show();
		}

	}

	public static boolean isLogAgain=false;
	public void showDialogSelectImg2(Activity _ac) {
		SaleReportDataSource a = new SaleReportDataSource(_ac, _ac);

		ac = _ac;
		// custom dialog
		if (!isShowDialog) {
			isShowDialog = true;
			// imgSet = _imgSet;
			dialogEditItems = new Dialog(ac);
			dialogEditItems.getWindow();
			dialogEditItems.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialogEditItems.setContentView(R.layout.login_dialog);
			dialogEditItems.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			// dialog.setTitle("Title...");

			this.rlBig = (RelativeLayout) dialogEditItems
					.findViewById(R.id.rlBig);
			this.gvUser = (GridView) dialogEditItems.findViewById(R.id.gvUser);
			this.imgCompanyLogo = (ImageView) dialogEditItems
					.findViewById(R.id.imgCompanyLogo);
			this.btnCompanyName = (Button) dialogEditItems
					.findViewById(R.id.btnCompanyName);
			this.btnOk = (Button) dialogEditItems.findViewById(R.id.btnOk);
			this.btnDel = (Button) dialogEditItems.findViewById(R.id.btnDel);
			this.btnCompany = (Button) dialogEditItems
					.findViewById(R.id.btnCompanyName);
			btnBack = (Button) dialogEditItems.findViewById(R.id.btnBack);
			rlRes = (RelativeLayout) dialogEditItems.findViewById(R.id.rlRes);
			edInput = (EditText) dialogEditItems.findViewById(R.id.edInput);

			edUserName = (EditText) dialogEditItems
					.findViewById(R.id.edUserName);
			imgAvartar = (ImageView) dialogEditItems
					.findViewById(R.id.imgAvartar);

			((CustomFragmentActivity) ac).setWidthHeight(rlBig, 1.3, 1.4);
			// ((CustomFragmentActivity) ac).setWidthHeight(imgCompanyLogo, 8,
			// 5);
			((CustomFragmentActivity) ac).setWidthHeight(imgAvartar, 8, 5);
			adapter = new UsersAdapter(ac, list);
			gvUser.setAdapter(adapter);
			loadVattu();

			// gvUser.setOnItemSelectedListener(this);
			imgCompanyLogo.setOnClickListener(this);
			btnOk.setOnClickListener(this);
			btnDel.setOnClickListener(this);
			imgAvartar.setOnClickListener(this);
			btnCompany.setOnClickListener(this);
			btnBack.setOnClickListener(this);
			dialogEditItems.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					isShowDialog = false;
				}
			});
			imgAvartar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					((CustomFragmentActivity) ac).getPicture();

				}
			});
			edInput.setText(a.loadAmount());
			edInput.setVisibility(View.GONE);
			dialogEditItems.setCancelable(false);
			dialogEditItems.setCanceledOnTouchOutside(false);
			dialogEditItems.show();
		}

	}

	UsersDataSource dataSource;

	public void loadVattu() {

		if (list != null)
			list.clear();

		dataSource = new UsersDataSource(ac, ac);
		list = dataSource.loadItems();
		adapter.setItemList(list);
		adapter.notifyDataSetChanged();
	}

	private void show() {
		btnCompanyName.setVisibility(View.VISIBLE);
		btnBack.setVisibility(View.VISIBLE);
		rlRes.setVisibility(View.VISIBLE);
		imgCompanyLogo.setVisibility(View.GONE);
		edInput.setVisibility(View.GONE);
		btnDel.setVisibility(View.VISIBLE);
	}

	private void hide() {
		btnCompanyName.setVisibility(View.GONE);
		btnBack.setVisibility(View.GONE);
		rlRes.setVisibility(View.GONE);
		imgCompanyLogo.setVisibility(View.VISIBLE);
		if(isLogAgain){
			edInput.setVisibility(View.GONE);	
		}else{
			edInput.setVisibility(View.VISIBLE);
		}
		btnDel.setVisibility(View.GONE);
		isRes = false;
	}

	private boolean isRes = false;

	@Override
	public void onClick(View v) {
		if (v == btnCompany) {
			hide();
			DialogCompany a = new DialogCompany();
			a.showDialogSaleReport(ac, ac);
		}
		if (v == btnBack) {
			hide();
		}
		if (imgCompanyLogo == v) {
			isRes = true;
			show();
		}
		if (btnDel == v) {
			UsersDataSource a = new UsersDataSource(ac, ac);
			String s = a.DelelteUser(idUser);

			loadVattu();
		}
		if (btnOk == v) {
			if (isRes) {
				if (TextUtils.isEmpty(edUserName.getText().toString())) {
					edUserName.setError("This field is required");
				} else {
					UsersModel md = new UsersModel();
					md.setUserName(edUserName.getText().toString());
					md.setImage(linkImage);
					md.setStatus("1");
					dataSource.insert(md);
					edUserName.setText("");
					loadVattu();

					// Utilities.getGlobalVariable(com.pos.MainActivity).username=edUserName.getText().toString();
					hide();

				}
			} else {
				if (TextUtils.isEmpty(edInput.getText().toString())
						|| idUser == "") {
					Toast.makeText(ac, "Choosen User and input cash",
							Toast.LENGTH_SHORT).show();
					// edInput.setError("This field is required");
				} else {
					dataSource.insertCounters(edInput.getText().toString(),
							idUser);
					dialogEditItems.dismiss();
				}
			}

		}

	}

	public static String idUser = "";

	public String getIDuser(int pos) {
		idUser = list.get(pos).getIDUser();
		return idUser;

	}

}
