package com.pos.common;




import com.pos.ui.Home;
import com.pos.ui.Items;

import android.support.v4.app.Fragment;

public class FragmentFactory {

	public static Fragment getFragmentByKey(final int key) {
		/**
		 * All class in app will int below with key_value and return Fragment by
		 * name class fragment
		 */
		switch (key) {
		case ConstantValue.HOME_FRAGMENT:
			return new Home();
		case ConstantValue.ITEMS:
			return new Items();
//		case ConstantValue.EVENT:
//			return new Event();
//		case ConstantValue.GALLERY:
//			return new Gallery();
//		case ConstantValue.CONTACTUS:
//			return new Contact();
//		case ConstantValue.DIRECTION:
//			return new Derections();
//		case ConstantValue.LOGIN_FRAGMENT:
//			return new Login();
//		case ConstantValue.SIGN_UP:
//			return new Signup();
//		case ConstantValue.PROMOTIONS:
//			return new Promotions();
//		case ConstantValue.PUSH:
//			return new Push();
//		case ConstantValue.MAILING:
//			return new MailingLists();
//		case ConstantValue.TELLFRIENDS:
//			return new TellFriends();
//		case ConstantValue.RESERVATION:
//			return new Reservation();
////		case ConstantValue.VISITSTAMP:
////			return new VisitStamp();
//		case ConstantValue.SETTING:
//			return new Setting();
//		case ConstantValue.HISTORY:
//			return new History();
//		case ConstantValue.PRODUCT:
//			return new Product();
//		case ConstantValue.ME:
//			return new Me();
		default:
			return null;
		}
	}

}
