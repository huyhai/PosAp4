package com.pos.libs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import com.pos.model.ListOrderModel;

public class CalculationUtils {

	public static double calculateSubAmout(List<ListOrderModel> vattuList) {
		double chiphiVatTu = 0;
		for (int i = 0; i < vattuList.size(); i++) {
			ListOrderModel vtct = vattuList.get(i);
			chiphiVatTu = chiphiVatTu
					+ (Double.parseDouble(vtct.getQualyti())
							* Double.parseDouble(vtct.getUnitPrice()) - Double
								.parseDouble(vtct.getDiscount()));
		}
		DecimalFormat df = new DecimalFormat("0.00");
		double sub=Double.valueOf(df.format(chiphiVatTu));
		return sub;
	}

	public static double calculateGST(double sum,int percentGST) {
		double chiphiNhanCong = 0;
		chiphiNhanCong = sum * 0;
		chiphiNhanCong = round(chiphiNhanCong, 2);
		return chiphiNhanCong;
	}

	public static double calculatePercent(double sum, double percent) {
		double chiphiNhanCong = 0;
		chiphiNhanCong = sum / 100 * percent;
		chiphiNhanCong = round(chiphiNhanCong, 2);
		return chiphiNhanCong;
	}

	public static double calculateChange(double sum, double mustpay) {
		double chiphiNhanCong = 0;
		chiphiNhanCong = sum - mustpay;
//		chiphiNhanCong = Math.round(chiphiNhanCong / 100) * 100;
		chiphiNhanCong = round(chiphiNhanCong, 2);
		return chiphiNhanCong;
	}

	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
