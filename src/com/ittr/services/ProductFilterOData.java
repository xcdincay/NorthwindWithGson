package com.ittr.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.ittr.api.util.Util;
import com.ittr.data.Product.Product;
import com.ittr.data.Product.ProductDataSet;
import com.ittr.services.connector.Connection;

public class ProductFilterOData {

	public List<Product> productFilterOData() throws Exception {
		List<Product> productList = new ArrayList<Product>();
		DefaultHttpClient httpClient = null;
		// Northwind servisinden en fazla 50 kay�t d�n�yor
		// Bu y�zden 50'�er 50'�er gidip geliniyor
		Connection connection = new Connection();
		httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = connection.getResponse(Util.ServiceUri + Util.filterForProduct, httpClient);
		String responseFromJson = connection.readData(httpResponse);
		Gson gson = new Gson();
		ProductDataSet product = gson.fromJson(responseFromJson, ProductDataSet.class);
		productList.addAll(product.d.results);
		return productList;

	}
}
