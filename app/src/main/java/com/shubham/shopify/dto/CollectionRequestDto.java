package com.shubham.shopify.dto;

/**
 * Created by SHUBHAM on 1/15/2019.
 */

public class CollectionRequestDto {

    private String id;
    private String title;
    private String imageUrl;

    public CollectionRequestDto(String id, String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
