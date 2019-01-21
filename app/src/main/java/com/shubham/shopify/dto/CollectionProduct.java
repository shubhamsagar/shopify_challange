package com.shubham.shopify.dto;

/**
 * Created by SHUBHAM on 1/16/2019.
 */

public class CollectionProduct {
    public CollectionProduct(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    private String product_id;
}
