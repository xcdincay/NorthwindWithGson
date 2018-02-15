package com.ittr.api.util;

import java.util.concurrent.atomic.AtomicLong;

public final class Util {
	public static final String ServiceUri = "http://services.odata.org/V2/Northwind/Northwind.svc/";
	public static final String orderExpand = "Orders?$expand=Order_Details/Product";
	public static final String filterForInvoice = "Invoices?$filter=";
	public static final String filterWithOrderID = "OrderID%20eq%20";
	public static final String OR = "%20or%20";
	public static final String filterForProduct = "Products";
	public static final String filterWithProductID = "ProductID%20eq%20";
	private static AtomicLong idCounterForOrderID = new AtomicLong(10000);
	private static AtomicLong idCounterForItemID = new AtomicLong(500000);

	public static synchronized long createInternalOrderID() {
		long lastvalue = idCounterForOrderID.getAndIncrement();
		return lastvalue;
	}
	public static synchronized long createInternalItemID() {
		long lastvalue = idCounterForItemID.getAndIncrement();
		return lastvalue;
	}
	
}
