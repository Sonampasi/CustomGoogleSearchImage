package com.sonam.apps.googleimagesearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sonam on 2/11/2018.
 */

public class Response {
    @SerializedName("items")
    @Expose
    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
