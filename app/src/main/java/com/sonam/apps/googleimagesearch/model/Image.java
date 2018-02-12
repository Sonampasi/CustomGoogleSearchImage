package com.sonam.apps.googleimagesearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonam on 2/11/2018.
 */

public class Image {

    @SerializedName("link")
    @Expose
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public static ArrayList<Image> fromJsonArray(List<Image> jsonArray) {
        ArrayList<Image> images = new ArrayList<>();
        for (int x = 0; x < jsonArray.size(); x++) {
            try {
                Image image = jsonArray.get(x);
                if (image != null) {
                    images.add(image);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return images;
    }


}
