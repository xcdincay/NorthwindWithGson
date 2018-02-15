package com.ittr.data.OrderDetail;

import com.ittr.data.Product.Product;

public class Order_Detail {

	private int OrderID;
	private int ProductID;
	private double UnitPrice;
	private short Quantity;
	private float Discount;
	private long InternalItemID;
	private String ExternalItemID;
	private String ExternalOrderID;
	private long InternalOrderID;
	private Product Product;
	public int getOrderID() {
		return OrderID;
	}
	public void setOrderID(int orderID) {
		OrderID = orderID;
	}
	public int getProductID() {
		return ProductID;
	}
	public void setProductID(int productID) {
		ProductID = productID;
	}
	public double getUnitPrice() {
		return UnitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		UnitPrice = unitPrice;
	}
	public short getQuantity() {
		return Quantity;
	}
	public void setQuantity(short quantity) {
		Quantity = quantity;
	}
	public float getDiscount() {
		return Discount;
	}
	public void setDiscount(float discount) {
		Discount = discount;
	}

	public String getExternalItemID() {
		return ExternalItemID;
	}
	public void setExternalItemID(String externalItemID) {
		ExternalItemID = externalItemID;
	}
	public String getExternalOrderID() {
		return ExternalOrderID;
	}
	public void setExternalOrderID(String externalOrderID) {
		ExternalOrderID = externalOrderID;
	}
	public long getInternalOrderID() {
		return InternalOrderID;
	}
	public void setInternalOrderID(long internalOrderID) {
		InternalOrderID = internalOrderID;
	}
	public Product getProduct() {
		return Product;
	}
	public void setProduct(Product product) {
		Product = product;
	}
	public long getInternalItemID() {
		return InternalItemID;
	}
	public void setInternalItemID(long internalItemID) {
		InternalItemID = internalItemID;
	}
}
