package com.ittr.api.util;

public final class Util {
	public static final String ServiceUri = "http://services.odata.org/V2/Northwind/Northwind.svc/";
	public static final String orderExpand = "Orders?$expand=Order_Details/Product";
	public static final String filterForInvoice = "Invoices?$filter=";
	public static final String filterWithOrderID = "OrderID%20eq%20";
	public static final String OR = "%20or%20";
}
