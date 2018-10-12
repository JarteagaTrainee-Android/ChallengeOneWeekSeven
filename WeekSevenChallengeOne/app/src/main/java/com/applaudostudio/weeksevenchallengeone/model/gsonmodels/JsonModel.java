package com.applaudostudio.weeksevenchallengeone.model.gsonmodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonModel {
    @SerializedName("photos")
    private List<Photos> mPhotosList;

    public List<Photos> getPhotosList() {
        return mPhotosList;
    }

    public void setPhotosList(List<Photos> photosList) {
        this.mPhotosList = photosList;
    }


    public JsonModel(List<Photos> photosList) {
        this.mPhotosList = photosList;
    }


}
