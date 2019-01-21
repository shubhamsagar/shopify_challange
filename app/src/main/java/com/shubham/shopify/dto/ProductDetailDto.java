package com.shubham.shopify.dto;

/**
 * Created by SHUBHAM on 1/15/2019.
 */

public class ProductDetailDto {



    public String getCollectionTitle() {
        return collectionTitle;
    }

    public void setCollectionTitle(String collectionTitle) {
        this.collectionTitle = collectionTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(int inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    private String collectionTitle;
    private String title;
    private int inventoryQuantity;
    private String imageUrl;

    public ProductDetailDto(String collectionTitle, String title, int inventoryQuantity, String imageUrl) {
        this.collectionTitle = collectionTitle;
        this.title = title;
        this.inventoryQuantity = inventoryQuantity;
        this.imageUrl = imageUrl;
    }

}
