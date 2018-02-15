	package com.ittr.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.ittr.data.Invoice.Invoice;
import com.ittr.data.Invoice.InvoiceDataSet;
import com.ittr.data.Order.SalesOrderDataSet;
import com.ittr.services.connector.Connection;
import com.ittr.api.util.Util;

public class InvoiceFilterOData {
	public List<Invoice> invoiceFilterByOrderID(){
		List<Invoice> invoiceList = new ArrayList<Invoice>();
		int serviceResponseSize = 50;
		DefaultHttpClient httpClient = null;
		String queryForInvoice = "";
		try {
			//Northwind servisinden en fazla 50 kay�t d�n�yor
			//Bu y�zden 50'�er 50'�er gidip geliniyor
			Connection connection = new Connection();
			httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = connection.getResponse(Util.ServiceUri + Util.orderExpand, httpClient);
			String responseFromJson = connection.readData(httpResponse);
			Gson gson = new Gson();
			SalesOrderDataSet salesOrder = gson.fromJson(responseFromJson, SalesOrderDataSet.class);
		
			//Gelen gson u objesini d�n�yoruz
			for (int i = 0; i < serviceResponseSize; i++) {
				//Gelen objelerdeki OrderID'leri tek tek al�yoruz ve filtre i�in verece�imiz 
				//Url'i olu�turyoruz
				long orderID = salesOrder.d.results.get(i).getOrderID();
				queryForInvoice = queryForInvoice + Util.filterWithOrderID + orderID;
				// Her 50 kay�d�n sonuncusu hari� "OR" koymas� i�in bir if a��yoruz
				if (i % serviceResponseSize != serviceResponseSize - 1) {
					queryForInvoice = queryForInvoice + Util.OR;
					// E�er 50. kay�t geldiyse olu�turdu�umuz URL 'le servise gidip
					// Invoice u filtreliyoruz
				} else if (i % serviceResponseSize == serviceResponseSize - 1) {
					HttpResponse responseForInvoice = connection
							.getResponse(Util.ServiceUri + Util.filterForInvoice + queryForInvoice, httpClient);
					try {
						String outPutForResponse = connection.readData(responseForInvoice);
						InvoiceDataSet invoiceDataSet = gson.fromJson(outPutForResponse, InvoiceDataSet.class);
						invoiceList.addAll(invoiceDataSet.d.results);
					} catch (Exception e) {
						e.printStackTrace();
					}
					//Servisten sonucu ald�ktan sonra linki s�f�rlamak i�in yaratt���m�z
					// queryForInvoice stringini s�f�rl�yoruz
					queryForInvoice = "";
					//Kay�t bitene kadar 50 ekleyip tekrar d�n�yoruz bu sayede her seferinde 
					//50'�er kay�t �ekiyoruz
					if (serviceResponseSize != salesOrder.d.results.size()) {
						serviceResponseSize = serviceResponseSize + 50;
					}

				}
			}
			//Ilk �nce SalesOrder�n i�inkide OrderID'yi al�p
			//InvoiceListimizin i�in d�n�yoruz 
			for (int z = 0; z < salesOrder.d.results.size(); z++) {
				long orderID = salesOrder.d.results.get(z).getOrderID();
				for (int y = 0; y < invoiceList.size(); y++) {
					int invoiceOrderID = invoiceList.get(y).getOrderID();
					//E�er SalesOrder�n i�inkide OrderID InvoiceListin i�inde varsa
					//O Invoice u SalesOrder�n i�ine ekliyoruz
					//Ondan sonra bu Invoice un i�in SalesOrder� ekliyoruz ve yap�m�z Bidirectional oluyor
					if (orderID == invoiceOrderID) {
						salesOrder.d.results.get(z).setInvoice(invoiceList.get(y));
						invoiceList.get(y).setOrder(salesOrder.d.results.get(z));
						break;
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return invoiceList;
		
	}
}
