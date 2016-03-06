package com.pos;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.pos.controllibs.UserFunctions;
import com.pos.model.ListOrderModel;

public class PosApp extends Application implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Boolean bIsLogXML = true;
	 public boolean englist = true;
	public Boolean isLogin = false;
	public Boolean isReport = false;
	public Boolean clearCase = false;
	public boolean isHavePush = false;
	public int device_width = 0;
	public int device_height = 0;
	public int posGroup;
	public int posEdit;
	public static int GST=0;
	public int language_code=1;
	public boolean isPOC=false;
	public boolean isRefund=false;
	public static String note="";
	public static boolean isHoldPaid=false;
	public String username="";
	public boolean isTableHold = false;
	public static int soluongIn=1;
	public static String dayEnd="";
//	public boolean isPrintNotSave = false;

	private static Context context;

	public static Context getAppContext() {
		return PosApp.context;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		try {
			PosApp.context = getApplicationContext();
			String dataPath = getApplicationContext().getFilesDir().getPath().replace("/files", "");
			UserFunctions.initDataPath(dataPath);
		} catch (Exception e) {
			// TODO: handle exception
		}
		super.onCreate();
	}

	public static void initImageLoader(Context context, ImageLoader imageLoader) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging()
				.build();
		// Initialize ImageLoader with configuration.
		imageLoader.init(config);
	}
	
	public static ArrayList<ListOrderModel> listOrderData=new ArrayList<ListOrderModel>();
	
}
