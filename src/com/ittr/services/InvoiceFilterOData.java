package com.ittr.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.ittr.data.Invoice.Invoice;
import com.ittr.data.Invoice.InvoiceDataSet;
import com.ittr.data.Order.SalesOrderDataSet;
import com.ittr.api.util.Util;

public class InvoiceFilterOData {
	public void invoiceFilterByOrderID(){
		List<Invoice> invoiceList = new ArrayList<Invoice>();
		int serviceResponseSize = 50;
		DefaultHttpClient httpClient = null;
		String queryForInvoice = "";
		try {
			//Northwind servisinden en fazla 50 kayýt dönüyor
			//Bu yüzden 50'þer 50'þer gidip geliniyor
			Connection connection = new Connection();
			httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = connection.getResponse(Util.ServiceUri + Util.orderExpand, httpClient);
			String responseFromJson = connection.readData(httpResponse);
			Gson gson = new Gson();
			SalesOrderDataSet salesOrder = gson.fromJson(responseFromJson, SalesOrderDataSet.class);
		
			//Gelen gson u objesini dönüyoruz
			for (int i = 0; i < serviceResponseSize; i++) {
				//Gelen objelerdeki OrderID'leri tek tek alýyoruz ve filtre için vereceðimiz 
				//Url'i oluþturyoruz
				int orderID = salesOrder.d.results.get(i).getOrderID();
				queryForInvoice = queryForInvoice + Util.filterWithOrderID + orderID;
				// Her 50 kayýdýn sonuncusu hariç "OR" koymasý için bir if açýyoruz
				if (i % serviceResponseSize != serviceResponseSize - 1) {
					queryForInvoice = queryForInvoice + Util.OR;
					// Eðer 50. kayýt geldiyse oluþturduðumuz URL 'le servise gidip
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
					//Servisten sonucu aldýktan sonra linki sýfýrlamak için yarattýðýmýz
					// queryForInvoice stringini sýfýrlýyoruz
					queryForInvoice = "";
					//Kayýt bitene kadar 50 ekleyip tekrar dönüyoruz bu sayede her seferinde 
					//50'þer kayýt çekiyoruz
					if (serviceResponseSize != salesOrder.d.results.size()) {
						serviceResponseSize = serviceResponseSize + 50;
					}

				}
			}
			//Ilk önce SalesOrderýn içinkide OrderID'yi alýp
			//InvoiceListimizin için dönüyoruz 
			for (int z = 0; z < salesOrder.d.results.size(); z++) {
				int orderID = salesOrder.d.results.get(z).getOrderID();
				for (int y = 0; y < invoiceList.size(); y++) {
					int invoiceOrderID = invoiceList.get(y).getOrderID();
					//Eðer SalesOrderýn içinkide OrderID InvoiceListin içinde varsa
					//O Invoice u SalesOrderýn içine ekliyoruz
					//Ondan sonra bu Invoice un için SalesOrderý ekliyoruz ve yapýmýz Bidirectional oluyor
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
		
	}
}
