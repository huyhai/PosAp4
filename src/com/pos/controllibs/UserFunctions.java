/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.pos.controllibs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pos.common.ConstantValue;
import com.pos.common.Utilities;
import com.pos.controllibs.UserFunctions.AdsModel;
import com.pos.controllibs.UserFunctions.PromotionsModel;
import com.pos.service.JSONCallBack;
import com.pos.service.JSONMethod;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class UserFunctions {
	private static UserFunctions userFunctions;
	// private JSONParser jsonParser;
	private static String message;
	public String messagePush;
	private Boolean isShowMessage = false;

	// private static String loginURL = "http://digipay.vn/digipayWS_New/";
	// private static String registerURL = "http://digipay.vn/digipayWS_New/";

	public static UserFunctions getInstance() {
		if (userFunctions == null)
			userFunctions = new UserFunctions();
		return userFunctions;
	}

	public void sendMessage(Context cont, String Action, Boolean isSuccess) {
		Intent intent = new Intent(Action);
		intent.putExtra(ConstantValue.IS_SUCCESS, isSuccess);
		cont.sendBroadcast(intent);
	}

	// constructor
	// public UserFunctions() {
	// jsonParser = new JSONParser();
	// }
	public static String dataPath = "";
	public static String DB_PATH = "";
	public static void initDataPath(String path) {
		// init value for data app path
		dataPath = path;
		DB_PATH =  path + "/databases/";
	}
	public String getMessage() {
		if (message == null) {
			message = "Not Message!";
		}
		return message;
	}

	public static String LOGIN = "Login";

	public void callLogin(Activity cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, userLogin,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack userLogin = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						JSONArray listPIN;
						listPIN = resultJson.getJSONArray("token");
						for (int i = 0; i < listPIN.length(); i++) {
							// String jsonobjectPin = new JSONObject();
							String jsonobjectPin = listPIN.getString(i);
							PIN1.add(jsonobjectPin);
						}
						sendMessage(activity, LOGIN, true);
					} else {
						sendMessage(activity, LOGIN, false);
					}

				} else {
					sendMessage(activity, LOGIN, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, LOGIN, false);
			}

		}
	};
	public static String DOWNSOFTPIN = "downsoftpin";
	public ArrayList<String> listSiriel = new ArrayList<String>();
	private ArrayList<String> exDate = new ArrayList<String>();
	public ArrayList<String> PIN = new ArrayList<String>();
	public ArrayList<String> PIN1 = new ArrayList<String>();

	public ArrayList<String> getlistSiriel() {
		if (null == listSiriel) {
			listSiriel = new ArrayList<String>();
		}
		return listSiriel;
	}

	public ArrayList<String> getexDatelist() {
		if (null == exDate) {
			exDate = new ArrayList<String>();
		}
		return exDate;
	}

	public ArrayList<String> getPINlist() {
		if (null == PIN) {
			PIN = new ArrayList<String>();
		}
		return PIN;
	}

	public void callDownsoftpin(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, downsoftpin,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack downsoftpin = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						JSONArray listserial;
						JSONArray listexDate;
						JSONArray listPIN;
						listserial = resultJson.getJSONArray("serial");
						listexDate = resultJson.getJSONArray("exDate");
						listPIN = resultJson.getJSONArray("PIN");
						for (int i = 0; i < listserial.length(); i++) {
							String jsonobjectserial = listserial.getString(i);
							String jsonobjectDate = listexDate.getString(i);
							String jsonobjectPin = listPIN.getString(i);
							listSiriel.add(jsonobjectserial);
							exDate.add(jsonobjectDate);
							PIN.add(jsonobjectPin);
						}
						sendMessage(activity, DOWNSOFTPIN, true);
					} else {
						sendMessage(activity, DOWNSOFTPIN, false);
					}

				} else {
					sendMessage(activity, DOWNSOFTPIN, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, DOWNSOFTPIN, false);
			}

		}
	};
	public static String TOPUPGAME = "tpgame";
	public static String amount;
	public String tgAcount;

	public void callTopupGame(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, tpgame,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack tpgame = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						amount = Utilities.getDataString(resultJson, "amount");
						tgAcount = Utilities.getDataString(resultJson,
								"targetAccount");
						sendMessage(activity, TOPUPGAME, true);
					} else {
						sendMessage(activity, TOPUPGAME, false);
					}

				} else {
					sendMessage(activity, TOPUPGAME, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, TOPUPGAME, false);
			}

		}
	};
	public static String TOPUPMOBILE = "tpmobile";
	public String amountMobile;
	public String tgAcountMobile;
	public boolean isAdap = false;

	public void callTopupMobile(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, tpmobile,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack tpmobile = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						amountMobile = Utilities.getDataString(resultJson,
								"amount");
						tgAcountMobile = Utilities.getDataString(resultJson,
								"phonenumber");
						sendMessage(activity, TOPUPMOBILE, true);
					} else {
						sendMessage(activity, TOPUPMOBILE, false);
					}

				} else {
					sendMessage(activity, TOPUPMOBILE, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, TOPUPMOBILE, false);
			}

		}
	};
	public static String LOGINUSER = "loginuser";
	public UserModel userModel;

	public UserModel getUserModel() {
		if (null == userModel) {
			userModel = new UserModel();
		}
		return userModel;
	}

	// public static String amount;
	// public String tgAcount;
	public void callLoginUser(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, loginuser,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack loginuser = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						userModel = new UserModel();
						userModel
								.setData(resultJson.getJSONObject("user_info"));
						sendMessage(activity, LOGINUSER, true);
					} else {
						sendMessage(activity, LOGINUSER, false);
					}

				} else {
					sendMessage(activity, LOGINUSER, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, LOGINUSER, false);
			}

		}
	};
	public static String GETNOTIFICATION = "getNotifi";

	// public static String amount;
	// public String tgAcount;
	public void callGetNotifir(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, getNotifi,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack getNotifi = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						sendMessage(activity, GETNOTIFICATION, true);
					} else {
						sendMessage(activity, GETNOTIFICATION, false);
					}

				} else {
					sendMessage(activity, GETNOTIFICATION, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, GETNOTIFICATION, false);
			}

		}
	};

	public static String DERECTIONS = "derections";
	private DerectionsModel derectionsModel;

	public DerectionsModel getDerectionsModel() {
		if (null == derectionsModel) {
			derectionsModel = new DerectionsModel();
		}
		return derectionsModel;
	}

	public static String CONTACT = "contact";
	private ContactModel contactModel;

	public ContactModel getContactModel() {
		if (null == contactModel) {
			contactModel = new ContactModel();
		}
		return contactModel;
	}

	public void callContactModel(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, contact,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack contact = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						contactModel = new ContactModel();
						contactModel.setData(resultJson
								.getJSONObject("list_data"));
						sendMessage(activity, CONTACT, true);
					} else {
						sendMessage(activity, CONTACT, false);
					}

				} else {
					sendMessage(activity, CONTACT, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, CONTACT, false);
			}

		}
	};
	public static String SERVICE = "listrewssards";
	public ServiceModel serviceModel;
	private ArrayList<ServiceModel> listService;


	public ArrayList<ServiceModel> getlistService() {
		if (null == listService) {
			listService = new ArrayList<ServiceModel>();
		}
		return listService;
	}

	private static Activity act;
	public void getLlistService(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		act=cont;
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, listrewards,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack listrewards = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						listService = new ArrayList<ServiceModel>();
						JSONArray listJson;
						listJson = resultJson.getJSONArray("list_data");
						for (int i = 0; i < listJson.length(); i++) {
							JSONObject jSonObFriend = new JSONObject();
							jSonObFriend = listJson.getJSONObject(i);
							serviceModel = new ServiceModel();
							serviceModel.setData(jSonObFriend);
							listService.add(serviceModel);
						}

						sendMessage(activity, SERVICE, true);
					} else {
						sendMessage(activity, SERVICE, false);
					}

				} else {
					sendMessage(activity, SERVICE, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, SERVICE, false);
			}

		}
	};
	public static String LISTADS = "listAds";
	public AdsModel adsModel;

	public AdsModel getAdsModel() {
		if (null == adsModel) {
			adsModel = new AdsModel();
		}
		return adsModel;
	}

	public void getListAds(Activity cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, listAds,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack listAds = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						adsModel = new AdsModel();
						adsModel.setData(resultJson.getJSONObject("list_data"));

						sendMessage(activity, LISTADS, true);
					} else {
						sendMessage(activity, LISTADS, false);
					}

				} else {
					sendMessage(activity, LISTADS, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, LISTADS, false);
			}

		}
	};

	public static String EVENT = "event";
	public EventModel eventModel;

	private ArrayList<EventModel> listEventModel;

	// public AdsModel getAdsModel() {
	// if (null == adsModel) {
	// adsModel = new AdsModel();
	// }
	// return adsModel;
	// }

	public ArrayList<EventModel> getLishEventModel() {
		if (null == listEventModel) {
			listEventModel = new ArrayList<EventModel>();
		}
		return listEventModel;
	}

	public void getEvent(Activity cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, event,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack event = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						// listhistoryModel = new ArrayList<HistoryModel>();
						JSONArray listJson;
						listJson = resultJson.getJSONArray("list_data");
						for (int i = 0; i < listJson.length(); i++) {
							JSONObject jSonObFriend = new JSONObject();
							jSonObFriend = listJson.getJSONObject(i);
							eventModel = new EventModel();
							eventModel.setData(jSonObFriend);
							listEventModel.add(eventModel);
						}

						sendMessage(activity, EVENT, true);
					} else {
						sendMessage(activity, EVENT, false);
					}

				} else {
					sendMessage(activity, EVENT, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, EVENT, false);
			}

		}
	};

	public static String PRODUCT = "product";
	public ProductModel productModel;

	private ArrayList<ProductModel> listProducttModel;

	// public AdsModel getAdsModel() {
	// if (null == adsModel) {
	// adsModel = new AdsModel();
	// }
	// return adsModel;
	// }

	public ArrayList<ProductModel> getLishProductModel() {
		if (null == listProducttModel) {
			listProducttModel = new ArrayList<ProductModel>();
		}
		return listProducttModel;
	}

	public void getProduct(Activity cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, product,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack product = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						// listhistoryModel = new ArrayList<HistoryModel>();
						JSONArray listJson;
						listJson = resultJson.getJSONArray("list_data");
						for (int i = 0; i < listJson.length(); i++) {
							JSONObject jSonObFriend = new JSONObject();
							jSonObFriend = listJson.getJSONObject(i);
							productModel = new ProductModel();
							productModel.setData(jSonObFriend);
							listProducttModel.add(productModel);
						}

						sendMessage(activity, PRODUCT, true);
					} else {
						sendMessage(activity, PRODUCT, false);
					}

				} else {
					sendMessage(activity, PRODUCT, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, PRODUCT, false);
			}

		}
	};

	public static String HISTORY = "listImagess";
	public HistoryModel historyModel;
	private ArrayList<HistoryModel> listhistoryModel;

	// public AdsModel getAdsModel() {
	// if (null == adsModel) {
	// adsModel = new AdsModel();
	// }
	// return adsModel;
	// }

	public ArrayList<HistoryModel> getLishHistoryModelModel() {
		if (null == listhistoryModel) {
			listhistoryModel = new ArrayList<HistoryModel>();
		}
		return listhistoryModel;
	}

	public void getListHistory(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, listImagess,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack listImagess = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						listhistoryModel = new ArrayList<HistoryModel>();
						JSONArray listJson;
						listJson = resultJson.getJSONArray("list_data");
						for (int i = 0; i < listJson.length(); i++) {
							JSONObject jSonObFriend = new JSONObject();
							jSonObFriend = listJson.getJSONObject(i);
							historyModel = new HistoryModel();
							historyModel.setData(jSonObFriend);
							listhistoryModel.add(historyModel);
						}

						sendMessage(activity, HISTORY, true);
					} else {
						sendMessage(activity, HISTORY, false);
					}

				} else {
					sendMessage(activity, HISTORY, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, HISTORY, false);
			}

		}
	};
	public static String LISTIMAGE = "listImages";
	public ImagesModel imagesModel;
	private ArrayList<ImagesModel> listImagesModel;

	// public AdsModel getAdsModel() {
	// if (null == adsModel) {
	// adsModel = new AdsModel();
	// }
	// return adsModel;
	// }

	public ArrayList<ImagesModel> getListImagesModel() {
		if (null == listImagesModel) {
			listImagesModel = new ArrayList<ImagesModel>();
		}
		return listImagesModel;
	}

	public void getListImages(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, listImages,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack listImages = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						listImagesModel = new ArrayList<ImagesModel>();
						JSONArray listJson;
						listJson = resultJson.getJSONArray("list_data");
						for (int i = 0; i < listJson.length(); i++) {
							JSONObject jSonObFriend = new JSONObject();
							jSonObFriend = listJson.getJSONObject(i);
							imagesModel = new ImagesModel();
							imagesModel.setData(jSonObFriend);
							listImagesModel.add(imagesModel);
						}

						sendMessage(activity, LISTIMAGE, true);
					} else {
						sendMessage(activity, LISTIMAGE, false);
					}

				} else {
					sendMessage(activity, LISTIMAGE, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, LISTIMAGE, false);
			}

		}
	};
	public static String SVLIST = "resservation";
	public SVModel listSV;
	private ArrayList<SVModel> listMember;

//	public SVModel getListService() {
//		if (null == listSV) {
//			listSV = new SVModel();
//		}
//		return listSV;
//	}

	public ArrayList<SVModel> getgetListService() {
		if (null == listMember) {
			listMember = new ArrayList<SVModel>();
		}
		return listMember;
	}

	public void getListReaservation(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams,
				resservation, _isShowProgressBar);
		// method.execute();
	}

	JSONCallBack resservation = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						listMember = new ArrayList<SVModel>();
						JSONArray listJson;
						listJson = resultJson.getJSONArray("list_data");
						for (int i = 0; i < listJson.length(); i++) {
							JSONObject jSonObFriend = new JSONObject();
							jSonObFriend = listJson.getJSONObject(i);
							listSV = new SVModel();
							listSV.setData(jSonObFriend);
							listMember.add(listSV);
						}

						sendMessage(activity, SVLIST, true);
					} else {
						sendMessage(activity, SVLIST, false);
					}

				} else {
					sendMessage(activity, SVLIST, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, SVLIST, false);
			}

		}
	};

	public static String BOOKINTERNET = "bookInternet";
	public BookedModel bookedModel;

	public BookedModel getBookedModel() {
		if (null == bookedModel) {
			bookedModel = new BookedModel();
		}
		return bookedModel;
	}

	public void callBookInternet(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		JSONMethod method = new JSONMethod(cont, JSONObjectParams,
				bookInternet, _isShowProgressBar);
		// method.execute();
	}

	JSONCallBack bookInternet = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						sendMessage(activity, BOOKINTERNET, true);
					} else {
						sendMessage(activity, BOOKINTERNET, false);
					}

				} else {
					sendMessage(activity, BOOKINTERNET, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, BOOKINTERNET, false);
			}

		}
	};

	// public static String ADDDEVICE = "adddive";

	public void addDV(Activity cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, adddive,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack adddive = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					// if ((Boolean) resultJson.get("is_success")) {
					// sendMessage(activity, ADDDEVICE, true);
					// } else {
					// sendMessage(activity, ADDDEVICE, false);
					// }

				} else {
					// sendMessage(activity, ADDDEVICE, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				// sendMessage(activity, ADDDEVICE, false);
			}

		}
	};
	public static String PRODUCDEFAULT = "cossntssact";
	private ProducDefaultModel roducDefaultModel;

	public ProducDefaultModel producDefaultModel() {
		if (null == roducDefaultModel) {
			roducDefaultModel = new ProducDefaultModel();
		}
		return roducDefaultModel;
	}

	public void callroducDefaultModel(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, rodsucDefaultModel,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack rodsucDefaultModel = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						roducDefaultModel = new ProducDefaultModel();
						roducDefaultModel.setData(resultJson
								.getJSONObject("list_data"));
						sendMessage(activity, PRODUCDEFAULT, true);
					} else {
						sendMessage(activity, PRODUCDEFAULT, false);
					}

				} else {
					sendMessage(activity, PRODUCDEFAULT, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, PRODUCDEFAULT, false);
			}

		}
	};
	public static String UPDATEPOINT = "upPoint";

	public void callUpdatePoint(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, upPoint,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack upPoint = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					// isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						sendMessage(activity, UPDATEPOINT, true);
					} else {
						sendMessage(activity, UPDATEPOINT, false);
					}

				} else {
					sendMessage(activity, UPDATEPOINT, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, UPDATEPOINT, false);
			}

		}
	};
	public static String BOOKREWARDS = "bookRewards";

	public void callBookRewards(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, bookRewards,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack bookRewards = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						sendMessage(activity, BOOKREWARDS, true);
					} else {
						sendMessage(activity, BOOKREWARDS, false);
					}

				} else {
					sendMessage(activity, BOOKREWARDS, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, BOOKREWARDS, false);
			}

		}
	};
	public static String PUSH = "pppppp";

	public void updatePush(Activity cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, pppppp,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack pppppp = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				message = Utilities.getDataString(resultJson, "message");
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						sendMessage(activity, PUSH, true);
					} else {
						sendMessage(activity, PUSH, false);
					}

				} else {

					sendMessage(activity, PUSH, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, PUSH, false);
			}

		}
	};
	public static String SIGNUPUSER = "signupuser";

	public void callSignup(Activity cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, signupuser,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack signupuser = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						sendMessage(activity, SIGNUPUSER, true);
					} else {
						sendMessage(activity, SIGNUPUSER, false);
					}

				} else {
					sendMessage(activity, SIGNUPUSER, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, SIGNUPUSER, false);
			}

		}
	};
	public static String UPDATEUSER = "upuser";
	private UpdateUserModel updateModel;

	public UpdateUserModel getUpdateModel() {
		if (null == updateModel) {
			updateModel = new UpdateUserModel();
		}
		return updateModel;
	}

	public void up(Activity cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, upuser,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack upuser = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						sendMessage(activity, UPDATEUSER, true);
					} else {
						sendMessage(activity, UPDATEUSER, false);
					}

				} else {
					sendMessage(activity, UPDATEUSER, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, UPDATEUSER, false);
			}

		}
	};
	public static String CHECk = "check";

	public void check(Activity cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, check,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack check = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						sendMessage(activity, CHECk, true);
					} else {
						sendMessage(activity, CHECk, false);
					}

				} else {
					sendMessage(activity, CHECk, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, CHECk, false);
			}

		}
	};
	public static String ADV = "cossntact";
	private ADVModel ADVModel;
	private ArrayList<ADVModel> ADVModelList;

	public ADVModel ADVModel() {
		if (null == ADVModel) {
			ADVModel = new ADVModel();
		}
		return ADVModel;
	}
	public ArrayList<ADVModel> getListADV() {
		if (null == ADVModelList) {
			ADVModelList = new ArrayList<ADVModel>();
		}
		return ADVModelList;
	}

	public void callADVModel(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, cosntact,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack cosntact = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
//					if ((Boolean) resultJson.get("is_success")) {
//						ADVModel = new ADVModel();
//						ADVModel.setData(resultJson
//								.getJSONObject("list_data"));
//						sendMessage(activity, ADV, true);
//					} else {
//						sendMessage(activity, ADV, false);
//					}
					if ((Boolean) resultJson.get("is_success")) {
						ADVModelList = new ArrayList<ADVModel>();
						JSONArray listJson;
						listJson = resultJson.getJSONArray("list_data");
						for (int i = 0; i < listJson.length(); i++) {
							JSONObject jSonObFriend = new JSONObject();
							jSonObFriend = listJson.getJSONObject(i);
							ADVModel = new ADVModel();
							ADVModel.setData(jSonObFriend);
							ADVModelList.add(ADVModel);
						}

						sendMessage(activity, ADV, true);
					} else {
						sendMessage(activity, ADV, false);
					}

				} else {
					sendMessage(activity, ADV, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, ADV, false);
			}

		}
	};
	public static String MAILLINGLIST = "percent1";

	public void updateMailling(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, percent1,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack percent1 = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						// getUserModel().setVisit(Utilities.getDataString(resultJson,
						// "stamps"));
						sendMessage(activity, MAILLINGLIST, true);
					} else {
						// getUserModel().setVisit(Utilities.getDataString(resultJson,
						// "stamps"));
						sendMessage(activity, MAILLINGLIST, false);
					}

				} else {

					sendMessage(activity, MAILLINGLIST, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, MAILLINGLIST, false);
			}

		}
	};
	public static String GETALLCOUCHERREAL = "allvoucherreal";
	public static String allVoucherreal;
	public String vouAvail;

	public void allVoucherReal(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams,
				allvoucherreal, _isShowProgressBar);
		// method.execute();
	}

	JSONCallBack allvoucherreal = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						// getUserModel().setVisit(
						// Utilities.getDataString(resultJson, "stamps"));
						allVoucherreal = Utilities.getDataString(resultJson,
								"user_info");
						vouAvail = Utilities.getDataString(resultJson,
								"vou_avail");
						sendMessage(activity, GETALLCOUCHERREAL, true);
					} else {
						// getUserModel().setVisit(Utilities.getDataString(resultJson,
						// "stamps"));
						sendMessage(activity, GETALLCOUCHERREAL, false);
					}

				} else {

					sendMessage(activity, GETALLCOUCHERREAL, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, GETALLCOUCHERREAL, false);
			}

		}
	};
	public static String GETALLCOUCHER = "allvoucher";
	public static String allVoucher;

	public void allVoucher(Activity cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, allvoucher,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack allvoucher = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						// getUserModel().setVisit(
						// Utilities.getDataString(resultJson, "stamps"));
						allVoucher = Utilities.getDataString(resultJson,
								"user_info");
						sendMessage(activity, GETALLCOUCHER, true);
					} else {
						// getUserModel().setVisit(Utilities.getDataString(resultJson,
						// "stamps"));
						sendMessage(activity, GETALLCOUCHER, false);
					}

				} else {

					sendMessage(activity, GETALLCOUCHER, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, GETALLCOUCHER, false);
			}

		}
	};
	public static String UPDATEVOU = "percentvou";

	public void updateVou(Activity cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, percentvou,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack percentvou = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						getUserModel().setVisit(
								Utilities.getDataString(resultJson, "stamps"));
						sendMessage(activity, UPDATEVOU, true);
					} else {
						// getUserModel().setVisit(Utilities.getDataString(resultJson,
						// "stamps"));
						sendMessage(activity, UPDATEVOU, false);
					}

				} else {

					sendMessage(activity, UPDATEVOU, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, UPDATEVOU, false);
			}

		}
	};
	public static String UPDATESTAMPS = "percent";

	public void updateStamps(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, percent,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack percent = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						getUserModel().setVisit(
								Utilities.getDataString(resultJson, "stamps"));
						sendMessage(activity, UPDATESTAMPS, true);
					} else {
						// getUserModel().setVisit(Utilities.getDataString(resultJson,
						// "stamps"));
						sendMessage(activity, UPDATESTAMPS, false);
					}

				} else {

					sendMessage(activity, UPDATESTAMPS, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, UPDATESTAMPS, false);
			}

		}
	};
	public static String ADDDEVICE = "adddive";

	public void add(Context cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, adddive1,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack adddive1 = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				// String message = Utilities.getDataString(resultJson,
				// "message");
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						sendMessage(activity, ADDDEVICE, true);
					} else {
						sendMessage(activity, ADDDEVICE, false);
					}

				} else {
					sendMessage(activity, ADDDEVICE, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, ADDDEVICE, false);
			}
			// Log.e("JSON", json);
		}
	};

	public static String PROMOTIONS = "promotion";
	// private ArrayList<PromotionsModel> listPromotions = new
	// ArrayList<PromotionsModel>();
	private PromotionsModel promotionModel;

	public PromotionsModel getProModel() {
		if (null == promotionModel) {
			promotionModel = new PromotionsModel();
		}
		return promotionModel;
	}

	// public ArrayList<PromotionsModel> getlistRegular() {
	// if (null == listPromotions) {
	// listPromotions = new ArrayList<PromotionsModel>();
	// }
	// return listPromotions;
	// }

	public void callGetlistRegular(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams,
				getlistregular, _isShowProgressBar);
		// method.execute();
	}

	JSONCallBack getlistregular = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						promotionModel = new PromotionsModel();
						promotionModel.setData(resultJson
								.getJSONObject("list_data"));

						sendMessage(activity, PROMOTIONS, true);
					} else {
						sendMessage(activity, PROMOTIONS, false);
					}

				} else {
					sendMessage(activity, PROMOTIONS, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, PROMOTIONS, false);
			}

		}
	};
	public ArrayList<String> listPhone = new ArrayList<String>();

	// public ArrayList<String> listName =new ArrayList<String>();
	public class PromotionsModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String NameReservation;

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setNameReservation(Utilities
					.getDataString(jSonInfo, "Content"));

		}

		public String getNameReservation() {
			return NameReservation;
		}

		public void setNameReservation(String fullName) {
			this.NameReservation = fullName;
		}

	}

	public class UpdateUserModel implements Serializable {
		private static final long serialVersionUID = 1L;

		private String nameUp;
		private String fullUp;
		private String phoneUp;
		private String passUp;
		private String emailUp;
		private String IdUser;

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setUsernameUp(Utilities.getDataString(jSonInfo, "UserName"));
			this.setfullUp(Utilities.getDataString(jSonInfo, "FullName"));
			this.setEmailUp(Utilities.getDataString(jSonInfo, "Email"));
			this.setPhoneUp(Utilities.getDataString(jSonInfo, "Phone"));
			this.setPassUp(Utilities.getDataString(jSonInfo, "Password"));
			this.setIdUser(Utilities.getDataString(jSonInfo, "Iduser"));

		}

		public String getUsernameUp() {
			return nameUp;
		}

		public void setUsernameUp(String _usernameup) {
			nameUp = _usernameup;
		}

		public String getFullUp() {
			return fullUp;
		}

		public void setfullUp(String _fullup) {
			fullUp = _fullup;
		}

		public String getPhoneUp() {
			return phoneUp;
		}

		public void setPhoneUp(String _phoneup) {
			phoneUp = _phoneup;
		}

		public String getPassUp() {
			return passUp;
		}

		public void setPassUp(String _passup) {
			passUp = _passup;
		}

		public String getEmailUp() {
			return emailUp;
		}

		public void setEmailUp(String _emailup) {
			emailUp = _emailup;
		}

		public String getIdUser() {
			return IdUser;
		}

		public void setIdUser(String idUser) {
			IdUser = idUser;
		}
	}
	public class BookedModel implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String token;
		private String DateOrder;
		
		public void setData(JSONObject jSonInfo) throws JSONException{
			this.setToken(Utilities.getDataString(jSonInfo, "token"));
			this.setDateOrder(Utilities.getDataString(jSonInfo, "TimeOrder"));
		}
		
		public String getToken() {
			return token;
		}

		public void setToken(String _token) {
			token = _token;
		}

		public String getDateOrder() {
			return DateOrder;
		}

		public void setDateOrder(String dateOrder) {
			DateOrder = dateOrder;
		}
		
	}

	public class UserModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String name;
		private String username;
		private String Visit;
		private String IdUser;
		private String email;
		private String phone;
		private String push;
		private String mailling;

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setUsername(Utilities.getDataString(jSonInfo, "UserName"));
			this.setName(Utilities.getDataString(jSonInfo, "FullName"));
			this.setVisit(Utilities.getDataString(jSonInfo, "Visit"));
			this.setIdUser(Utilities.getDataString(jSonInfo, "UserID"));
			this.setEmail(Utilities.getDataString(jSonInfo, "Email"));
			this.setPhone(Utilities.getDataString(jSonInfo, "Phone"));
			this.setMailling(Utilities.getDataString(jSonInfo, "Mailling"));
			this.setPush(Utilities.getDataString(jSonInfo, "Push"));

		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String _username) {
			username = _username;
		}

		public String getName() {
			return name;
		}

		public void setName(String firstname) {
			this.name = firstname;
		}

		public String getVisit() {
			return Visit;
		}

		public void setVisit(String sVisit) {
			this.Visit = sVisit;
		}

		public String getIdUser() {
			return IdUser;
		}

		public void setIdUser(String idUser) {
			IdUser = idUser;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getMailling() {
			return mailling;
		}

		public void setMailling(String mailling) {
			this.mailling = mailling;
		}

		public String getPush() {
			return push;
		}

		public void setPush(String push) {
			this.push = push;
		}

		// public String getScore() {
		// return score;
		// }
		//
		// public void setScore(String score) {
		// if (score.equals("null")) {
		// this.score = "0";
		// } else {
		// this.score = score;
		// }
		//
		// }

	}

	public class LogGameModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String loaigiaodich;
		private String nhamang;
		private String sdtPin;
		private String menhgia;
		private String trangthai;
		private String buyerreal;

		private String date;
		private String timezone;
		private String timezone_type;

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setLoaigiaodich(Utilities
					.getDataString(jSonInfo, "trans_type"));
			this.setNhamang(Utilities.getDataString(jSonInfo, "provider"));
			this.setSdtPin(Utilities.getDataString(jSonInfo, "buyer"));
			this.setDate(jSonInfo.getJSONObject("time_create"));
			this.setMenhgia(Utilities.getDataString(jSonInfo, "trans_price"));
			this.setTrangthai(Utilities.getDataString(jSonInfo, "status"));
			this.setBuyerreal(Utilities.getDataString(jSonInfo, "buyerreal"));

		}

		public void setDate(JSONObject jSonInfo) throws JSONException {
			this.setDate(Utilities.getDataString(jSonInfo, "date"));
			this.setTimezone(Utilities.getDataString(jSonInfo, "timezone"));
			this.setTimezone_type(Utilities.getDataString(jSonInfo,
					"timezone_type"));
		}

		public String getLoaigiaodich() {
			return loaigiaodich;
		}

		public void setLoaigiaodich(String loaigiaodich) {
			this.loaigiaodich = loaigiaodich;
		}

		public String getNhamang() {
			return nhamang;
		}

		public void setNhamang(String nhamang) {
			this.nhamang = nhamang;
		}

		public String getSdtPin() {
			return sdtPin;
		}

		public void setSdtPin(String sdtPin) {
			this.sdtPin = sdtPin;
		}

		public String getMenhgia() {
			return menhgia;
		}

		public void setMenhgia(String menhgia) {
			this.menhgia = menhgia;
		}

		public String getTrangthai() {
			return trangthai;
		}

		public void setTrangthai(String trangthai) {
			this.trangthai = trangthai;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getTimezone() {
			return timezone;
		}

		public void setTimezone(String timezone) {
			this.timezone = timezone;
		}

		public String getTimezone_type() {
			return timezone_type;
		}

		public void setTimezone_type(String timezone_type) {
			this.timezone_type = timezone_type;
		}

		public String getBuyerreal() {
			return buyerreal;
		}

		public void setBuyerreal(String buyerreal) {
			this.buyerreal = buyerreal;
		}

	}

	public class SVModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String name;
		private String image;
		private String des;
		private String IDSv;

		public void ListReservationModel() {

		}

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setName(Utilities.getDataString(jSonInfo, "name"));
			this.setImage(Utilities.getDataString(jSonInfo, "Image"));
			this.setDes(Utilities.getDataString(jSonInfo, "Description"));
			this.setIDSv(Utilities.getDataString(jSonInfo, "ID"));

		}


		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getDes() {
			return des;
		}

		public void setDes(String des) {
			this.des = des;
		}

		public String getIDSv() {
			return IDSv;
		}

		public void setIDSv(String iDSv) {
			IDSv = iDSv;
		}


	}

	public class DerectionsModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String longt;
		private String lat;
		private String address;

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setLongt(Utilities.getDataString(jSonInfo, "Longitude"));
			this.setLat(Utilities.getDataString(jSonInfo, "Latitude"));
			this.setAddress(Utilities.getDataString(jSonInfo, "CompanyAddress"));

		}

		public String getLongt() {
			return longt;
		}

		public void setLongt(String longt) {
			this.longt = longt;
		}

		public String getLat() {
			return lat;
		}

		public void setLat(String lat) {
			this.lat = lat;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

	}

	public class ContactModel implements Serializable {
		private static final long serialVersionUID = 1L;
		private String name;
		private String address;
		private String mobile;
		private String email;
		private String image;
		private String Latitude;
		private String Longitude;

		private String web;

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setName(Utilities.getDataString(jSonInfo, "CompanyName"));
			this.setAddress(Utilities.getDataString(jSonInfo, "CompanyAddress"));
			this.setMobile(Utilities.getDataString(jSonInfo, "CompanyPhone"));
			this.setEmail(Utilities.getDataString(jSonInfo, "CompanyEmail"));
			this.setImage(Utilities.getDataString(jSonInfo, "images"));
			this.setWeb(Utilities.getDataString(jSonInfo, "Website"));
			this.setLatitude(Utilities.getDataString(jSonInfo, "Latitude"));
			this.setLongitude(Utilities.getDataString(jSonInfo, "Longitude"));

		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getWeb() {
			return web;
		}

		public void setWeb(String web) {
			this.web = web;
		}

		public String getLatitude() {
			return Latitude;
		}

		public void setLatitude(String latitude) {
			Latitude = latitude;
		}

		public String getLongitude() {
			return Longitude;
		}

		public void setLongitude(String longitude) {
			Longitude = longitude;
		}

	}
	public class ADVModel implements Serializable {
		private static final long serialVersionUID = 1L;
		private String image;
		private String nameAd;
		private String contentAD;


		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setImage(Utilities.getDataString(jSonInfo, "Image"));
			this.setNameAd(Utilities.getDataString(jSonInfo, "Name"));
			this.setContentAD(Utilities.getDataString(jSonInfo, "Description"));

		}


		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}


		public String getNameAd() {
			return nameAd;
		}


		public void setNameAd(String nameAd) {
			this.nameAd = nameAd;
		}


		public String getContentAD() {
			return contentAD;
		}


		public void setContentAD(String contentAD) {
			this.contentAD = contentAD;
		}

	}
	public class ProducDefaultModel implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String name;
		private String content;
		private String images;
		private String oldPrice;
		private String newPrice;

		public void setData(JSONObject jSonInfo) throws JSONException {

			this.setName(Utilities.getDataString(jSonInfo, "Name"));
			this.setContent(Utilities.getDataString(jSonInfo, "Content"));
			this.setImages(Utilities.getDataString(jSonInfo, "Images"));
			this.setOldPrice(Utilities.getDataString(jSonInfo, "OldPrice"));
			this.setNewPrice(Utilities.getDataString(jSonInfo, "NewPrice"));

		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setImages(String images) {
			this.images = images;
		}

		public String getImage() {
			return images;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getOldPrice() {
			return oldPrice;
		}

		public void setOldPrice(String oldPrice) {
			this.oldPrice = oldPrice;
		}

		public String getNewPrice() {
			return newPrice;
		}

		public void setNewPrice(String newPrice) {
			this.newPrice = newPrice;
		}

	}
	public class ServiceModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String images;

		public void ListReservationModel() {

		}

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setImages(Utilities.getDataString(jSonInfo, "Image"));

		}

		public String getImages() {
			return images;
		}

		public void setImages(String images) {
			this.images = images;
		}


	}

	public class AdsModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String content;

		public void ListReservationModel() {

		}

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setContent(Utilities.getDataString(jSonInfo, "Contents"));

		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

	}

	public class EventModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String des;
		private String title;
		private String images;

		public void setData(JSONObject jSonInfo) throws JSONException {

			this.setDes(Utilities.getDataString(jSonInfo, "Name"));
			this.setTit(Utilities.getDataString(jSonInfo, "ContentAds"));
			this.setImages(Utilities.getDataString(jSonInfo, "Image"));
		}

		public String getDes() {
			return des;
		}

		public void setDes(String des) {
			this.des = des;
		}

		public void setImages(String images) {
			this.images = images;
		}

		public String getImage() {
			return images;
		}

		public String getTit() {
			return title;
		}

		public void setTit(String title) {
			this.title = title;
		}
	}

	public class ProductModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String name;
		private String content;
		private String images;
		private String oldPrice;
		private String newPrice;

		public void setData(JSONObject jSonInfo) throws JSONException {

			this.setName(Utilities.getDataString(jSonInfo, "Name"));
			this.setContent(Utilities.getDataString(jSonInfo, "Content"));
			this.setImages(Utilities.getDataString(jSonInfo, "Images"));
			this.setOldPrice(Utilities.getDataString(jSonInfo, "OldPrice"));
			this.setNewPrice(Utilities.getDataString(jSonInfo, "NewPrice"));

		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setImages(String images) {
			this.images = images;
		}

		public String getImage() {
			return images;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getOldPrice() {
			return oldPrice;
		}

		public void setOldPrice(String oldPrice) {
			this.oldPrice = oldPrice;
		}

		public String getNewPrice() {
			return newPrice;
		}

		public void setNewPrice(String newPrice) {
			this.newPrice = newPrice;
		}
	}

	public class HistoryModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String SetDate;
		private String TimeFrom;
		private String TimeTo;
		private String Requests;
		private String status;

		public void ListReservationModel() {

		}

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setRequests(Utilities.getDataString(jSonInfo, "Requests"));
			this.setTimeTo(Utilities.getDataString(jSonInfo, "TimeTo"));
			this.setTimeFrom(Utilities.getDataString(jSonInfo, "TimeFrom"));
			this.setStatus(Utilities.getDataString(jSonInfo, "Status"));
			setDate1(jSonInfo.getJSONObject("SetDate"));

		}

		public void setDate1(JSONObject jSonInfo) throws JSONException {
			this.setSetDate(Utilities.getDataString(jSonInfo, "date"));
		}

		public String getRequests() {
			return Requests;
		}

		public void setRequests(String requests) {
			Requests = requests;
		}

		public String getTimeTo() {
			return TimeTo;
		}

		public void setTimeTo(String timeTo) {
			TimeTo = timeTo;
		}

		public String getTimeFrom() {
			return TimeFrom;
		}

		public void setTimeFrom(String timeFrom) {
			TimeFrom = timeFrom;
		}

		public String getSetDate() {
			return SetDate;
		}

		public void setSetDate(String setDate) {
			SetDate = setDate;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

	}

	public class ImagesModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String id;
		private String time;
		private String images;

		public void ListReservationModel() {

		}

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setId(Utilities.getDataString(jSonInfo, "ID"));
			this.setTime(Utilities.getDataString(jSonInfo, "Time_Upload"));
			this.setImages(Utilities.getDataString(jSonInfo, "ImageName"));

		}

		public String getImages() {
			return images;
		}

		public void setImages(String images) {
			this.images = images;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

	}

	/**
	 * function make Login Request
	 * 
	 * @param email
	 * @param password
	 * */
	/*
	 * public JSONObject loginUser(String email, String password) { // Building
	 * Parameters List<NameValuePair> params = new ArrayList<NameValuePair>();
	 * params.add(new BasicNameValuePair("tag", login_tag)); params.add(new
	 * BasicNameValuePair("email", email)); params.add(new
	 * BasicNameValuePair("password", password)); JSONObject json =
	 * jsonParser.getJSONFromUrl(loginURL, params); // return json //
	 * Log.e("JSON", json.toString()); return json; }
	 */

	/**
	 * function make Login Request
	 * 
	 * @param name
	 * @param email
	 * @param password
	 * */
	/*
	 * public JSONObject registerUser(String name, String email, String
	 * password) { // Building Parameters List<NameValuePair> params = new
	 * ArrayList<NameValuePair>(); params.add(new BasicNameValuePair("tag",
	 * register_tag)); params.add(new BasicNameValuePair("name", name));
	 * params.add(new BasicNameValuePair("email", email)); params.add(new
	 * BasicNameValuePair("password", password));
	 * 
	 * // getting JSON Object JSONObject json =
	 * jsonParser.getJSONFromUrl(registerURL, params); // return json return
	 * json; }
	 * 
	 * public JSONObject callEp() { // Building Parameters List<NameValuePair>
	 * params = new ArrayList<NameValuePair>(); params.add(new
	 * BasicNameValuePair("tag", "epay")); // JSONObject json =
	 * jsonParser.getJSONFromUrl(loginURL, params); JSONObject json =
	 * jsonParser.getJSONFromUrl(loginURL, params); // return json //
	 * Log.e("JSON", json.toString()); return json; }
	 *//**
	 * Function get Login status
	 * */
	/*
	 * public boolean isUserLoggedIn(Context context) { DatabaseHandler db = new
	 * DatabaseHandler(context); int count = db.getRowCount(); if (count > 0) {
	 * // user logged in return true; } return false; }
	 *//**
	 * Function to logout user Reset Database
	 * */
	/*
	 * public boolean logoutUser(Context context) { DatabaseHandler db = new
	 * DatabaseHandler(context); db.resetTables(); return true; }
	 */
	public static String LISTUSERRESERVATION = "userresservation";

	public ListUserReservationModel userReservationModel;
	private ArrayList<ListUserReservationModel> listUserReservationModel;

	// public ListReservationModel getUserModel() {
	// if (null == userModel) {
	// userModel = new ListReservationModel();
	// }
	// return userModel;
	// }
	public ArrayList<ListUserReservationModel> getListUserReservation() {
		if (null == listUserReservationModel) {
			listUserReservationModel = new ArrayList<ListUserReservationModel>();
		}
		return listUserReservationModel;
	}

	public void getListUserReservation(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams,
				userresservation, _isShowProgressBar);
		// method.execute();
	}

	JSONCallBack userresservation = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						listUserReservationModel = new ArrayList<ListUserReservationModel>();
						JSONArray listJson;
						listJson = resultJson.getJSONArray("list_data");
						for (int i = 0; i < listJson.length(); i++) {
							JSONObject jSonObFriend = new JSONObject();
							jSonObFriend = listJson.getJSONObject(i);
							userReservationModel = new ListUserReservationModel();
							userReservationModel.setData(jSonObFriend);
							listUserReservationModel.add(userReservationModel);
						}

						sendMessage(activity, LISTUSERRESERVATION, true);
					} else {
						sendMessage(activity, LISTUSERRESERVATION, false);
					}

				} else {
					sendMessage(activity, LISTUSERRESERVATION, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, LISTUSERRESERVATION, false);
			}

		}
	};

	public class ListUserReservationModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String daytime;

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setDate(jSonInfo.getJSONObject("SetDate"));

		}

		public void setDate(JSONObject jSonInfo) throws JSONException {
			this.setdaytime(Utilities.getDataString(jSonInfo, "date"));
		}

		public String getdaytime() {
			return daytime;
		}

		public void setdaytime(String resId) {
			this.daytime = resId;
		}

	}

	public static String FORGOTPASS = "forgotPass";

	public void callForgotPass(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, forgotPass,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack forgotPass = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						sendMessage(activity, FORGOTPASS, true);
					} else {
						sendMessage(activity, FORGOTPASS, false);
					}

				} else {
					sendMessage(activity, FORGOTPASS, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, FORGOTPASS, false);
			}

		}
	};

}
