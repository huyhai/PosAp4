package com.pos.model;

public class ListOrderModel {
	private String itemCode;
	private String itemName;
	private String unitPrice;
	private String qualyti;
	private String discount;
	private String amount;
	private String price2;
	private String specialPrice;

	// private String DetailItemName;
	// private String DetailQuantity;
	// private String DetailUnitPrice;
	// private String DetailDiscount;
	// private String DetailAmount;
	// private String DetailItemCode;
	// public static final String DETAILHOLD_TABLE_NAME = "HoldSales";
	public static final String ItemName = "ItemName";
	public static final String Quantity = "Quantity";
	public static final String UnitPrice = "UnitPrice";
	public static final String Discount = "Discount";
	public static final String Amount = "Amount";
	public static final String ItemCode = "ItemCode";

	public static final String Price2 = "Price2";
	public static final String SpecialPrice = "SpecialPrice";
	public final static String[] DETAIL_HOLD_FULL_PROJECTION = {
			ListOrderModel.ItemName, ListOrderModel.Quantity,
			ListOrderModel.UnitPrice, ListOrderModel.Discount,
			ListOrderModel.Amount, ListOrderModel.ItemCode,
			ListOrderModel.Price2, ListOrderModel.SpecialPrice };

	public ListOrderModel() {

	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getQualyti() {
		return qualyti;
	}

	public void setQualyti(String qualyti) {
		this.qualyti = qualyti;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPrice2() {
		return price2;
	}

	public void setPrice2(String price2) {
		this.price2 = price2;
	}

	public String getSpecialPrice() {
		return specialPrice;
	}

	public void setSpecialPrice(String specialPrice) {
		this.specialPrice = specialPrice;
	}
	// public String getDetailItemName() {
	// return DetailItemName;
	// }
	//
	// public void setDetailItemName(String detailItemName) {
	// DetailItemName = detailItemName;
	// }
	//
	// public String getDetailQuantity() {
	// return DetailQuantity;
	// }
	//
	// public void setDetailQuantity(String detailQuantity) {
	// DetailQuantity = detailQuantity;
	// }
	//
	// public String getDetailUnitPrice() {
	// return DetailUnitPrice;
	// }
	//
	// public void setDetailUnitPrice(String detailUnitPrice) {
	// DetailUnitPrice = detailUnitPrice;
	// }
	//
	// public String getDetailDiscount() {
	// return DetailDiscount;
	// }
	//
	// public void setDetailDiscount(String detailDiscount) {
	// DetailDiscount = detailDiscount;
	// }
	//
	// public String getDetailAmount() {
	// return DetailAmount;
	// }
	//
	// public void setDetailAmount(String detailAmount) {
	// DetailAmount = detailAmount;
	// }
	//
	// public String getDetailItemCode() {
	// return DetailItemCode;
	// }
	//
	// public void setDetailItemCode(String detailItemCode) {
	// DetailItemCode = detailItemCode;
	// }
	// public String getAmountOriginal() {
	// return amountOriginal;
	// }
	//
	// public void setAmountOriginal(String amountOriginal) {
	// this.amountOriginal = amountOriginal;
	// }


}
