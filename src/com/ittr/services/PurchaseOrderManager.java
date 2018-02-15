package com.ittr.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ittr.api.util.Util;
import com.ittr.data.Order.PurchaseOrder;
import com.ittr.data.OrderDetail.PurchaseOrderDetail;
import com.ittr.data.Product.Product;

public class PurchaseOrderManager {

	public List<PurchaseOrder> setPurchaseOrder() throws Exception, IOException {
		List<Product> productList = new ArrayList<Product>();
		FileManager fileManager = new FileManager();
		boolean indicator = true;
		ProductFilterOData productOdata = new ProductFilterOData();
		productList = productOdata.productFilterOData();
		List<Map<String, String>> excelData = new ArrayList<Map<String, String>>();
		excelData = fileManager.getExcelSheet();
		List<PurchaseOrder> purchaseOrderList = new ArrayList<PurchaseOrder>();
		for (int i = 0; i < excelData.size(); i++) {
			for (int j = 0; j < purchaseOrderList.size(); j++) {
				String externalOrderID = purchaseOrderList.get(j).getExternalOrderID();
				if (externalOrderID.equals(excelData.get(i).get("ExternalOrderID"))) {
					indicator = false;
					if (indicator == false) {
						PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
						purchaseOrderDetail.setInternalOrderID(purchaseOrderList.get(j).getInternalOrderID());
						purchaseOrderDetail.setInternalItemID(purchaseOrderList.get(j).getInternalItemID());
						for (Entry<String, String> entry : excelData.get(i).entrySet()) {
							String key = entry.getKey();
							String value = entry.getValue();
							if (key.equals("ProductID")) {
								purchaseOrderDetail.setProductID(Integer.parseInt(value));
								for (int c = 0; c < productList.size(); c++) {
									int productID = productList.get(c).getProductID();
									if (productID == purchaseOrderDetail.getProductID()) {

										purchaseOrderDetail.setProduct(productList.get(c));
										break;
									}
								}
								if (purchaseOrderDetail.getProduct() == null)
									throw new Exception("Wrong Product ID.Please try again.");
							} else {
								purchaseOrderDetail = purchaseOrderDetail.setPurchaseOrderDetail(key, value);
							}
						}
						purchaseOrderList.get(j).setPurchaseOrderDetail(purchaseOrderDetail);
						break;
					}
				}
				indicator = true;
			}
			if (indicator == true) {
				PurchaseOrder purchaseOrder = new PurchaseOrder();
				PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
				purchaseOrder.setExternalOrderID(excelData.get(i).get("ExternalOrderID"));
				purchaseOrder.setInternalOrderID(Util.createInternalOrderID());
				purchaseOrder.setInternalItemID(Util.createInternalItemID());
				purchaseOrderDetail.setInternalOrderID(purchaseOrder.getInternalOrderID());
				purchaseOrderDetail.setInternalItemID(purchaseOrder.getInternalItemID());
				for (Entry<String, String> entry : excelData.get(i).entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					if (key.equals("ProductID")) {
						purchaseOrderDetail.setProductID(Integer.parseInt(value));
						for (int c = 0; c < productList.size(); c++) {
							int productID = productList.get(c).getProductID();
							if (productID == purchaseOrderDetail.getProductID()) {
								purchaseOrderDetail.setProduct(productList.get(c));
								break;
							}
						}
						if (purchaseOrderDetail.getProduct() == null)
							throw new Exception("Wrong Product ID.Please try again.");
					} else {
						purchaseOrderDetail = purchaseOrderDetail.setPurchaseOrderDetail(key, value);
					}
				}
				purchaseOrder.setPurchaseOrderDetail(purchaseOrderDetail);
				purchaseOrderList.add(purchaseOrder);
			}
		}
		return purchaseOrderList;
	}
}
