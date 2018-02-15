package com.ittr.data.OrderDetail;

import java.io.IOException;

public class PurchaseOrderDetail extends Order_Detail {
	
	public PurchaseOrderDetail setPurchaseOrderDetail(String key,String value) throws IOException, Exception{
		
		if (key.equals("ExternalOrderID")) {
			this.setExternalOrderID(value);
		} else if (key.equals("ProductID")) {
			this.setProductID(Integer.parseInt(value));
		} else if (key.equals("UnitPrice")) {
			this.setUnitPrice(new Double(value));
		} else if (key.equals("Quantity")) {
			this.setQuantity(Short.parseShort(value));
		} else if (key.equals("Discount")) {
			this.setDiscount(Float.parseFloat(value));
		} else if (key.equals("ExternalItemID")){
			this.setExternalItemID(value);
		}
		return this;
	}

}
