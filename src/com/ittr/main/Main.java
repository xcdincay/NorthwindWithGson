package com.ittr.main;

import com.ittr.services.InvoiceFilterOData;

public class Main {

	public static void main(String[] args) {
		InvoiceFilterOData invoiceFilter = new InvoiceFilterOData();
		invoiceFilter.invoiceFilterByOrderID();
	}	

}
