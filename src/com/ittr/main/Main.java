package com.ittr.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ittr.data.Invoice.Invoice;
import com.ittr.data.Order.PurchaseOrder;
import com.ittr.services.InvoiceFilterOData;
import com.ittr.services.PurchaseOrderManager;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, Exception {
		
		InvoiceFilterOData invoiceFilter = new InvoiceFilterOData();
		List<Invoice> invoiceList = new ArrayList<Invoice>();
		invoiceList = invoiceFilter.invoiceFilterByOrderID();
		
		List<PurchaseOrder> purchaseOrderList = new ArrayList<PurchaseOrder>();
		PurchaseOrderManager purchaseOrderManager = new PurchaseOrderManager();
		purchaseOrderList = purchaseOrderManager.setPurchaseOrder();
	}
}
