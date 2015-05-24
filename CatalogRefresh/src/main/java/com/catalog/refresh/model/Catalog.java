package com.catalog.refresh.model;

public class Catalog {
	private String productId;
	private String affiliate_url;
	private String image_url;
	private String keyword;
	private String description;
	private String currency;
	private String merchant;
	private String merchantId;
	private String brand;
	private String upc;
	private String isbn;
	private String sales;
	private String catalogId;
	private String category;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getAffiliate_url() {
		return affiliate_url;
	}
	public void setAffiliate_url(String affiliate_url) {
		this.affiliate_url = affiliate_url;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getUpc() {
		return upc;
	}
	public void setUpc(String upc) {
		this.upc = upc;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	@Override
	public String toString() {
		return "Catalog [productId=" + productId + ", affiliate_url="
				+ affiliate_url + ", image_url=" + image_url + ", keyword="
				+ keyword + ", description=" + description + ", currency="
				+ currency + ", merchant=" + merchant + ", merchantId="
				+ merchantId + ", brand=" + brand + ", upc=" + upc + ", isbn="
				+ isbn + ", sales=" + sales + ", catalogId=" + catalogId + "]";
	}
	
	
}
