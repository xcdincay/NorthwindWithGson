package com.ittr.data.Order;

import java.util.ArrayList;
import java.util.List;

import com.ittr.data.OrderDetail.PurchaseOrderDetail;

public class PurchaseOrder extends Order {
	private long InternalOrderID;
	private long InternalItemID;
	List<PurchaseOrderDetail> purchaseOrderDetailList = new ArrayList<PurchaseOrderDetail>();
	private PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();

	public PurchaseOrderDetail getPurchaseOrderDetail() {
		return purchaseOrderDetail;
	}

	public void setPurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail) {
		this.purchaseOrderDetail = purchaseOrderDetail;
		purchaseOrderDetailList.add(purchaseOrderDetail);
	}

	public long getInternalOrderID() {
		return InternalOrderID;
	}

	public void setInternalOrderID(long internalOrderID) {
		InternalOrderID = internalOrderID;
	}

	public long getInternalItemID() {
		return InternalItemID;
	}

	public void setInternalItemID(long internalItemID) {
		InternalItemID = internalItemID;
	}
}