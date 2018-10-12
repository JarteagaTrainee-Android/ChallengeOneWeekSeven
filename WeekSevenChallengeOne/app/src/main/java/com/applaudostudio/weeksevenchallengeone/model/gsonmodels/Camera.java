package com.applaudostudio.weeksevenchallengeone.model.gsonmodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Camera implements Parcelable {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("rover_id")
    private String mRover_id;
    @SerializedName("full_name")
    private String mFull_Name;

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getRover_id() {
        return mRover_id;
    }

    public void setRover_id(String mRover_id) {
        this.mRover_id = mRover_id;
    }

    public String getFull_Name() {
        return mFull_Name;
    }

    public void setFull_Name(String mFull_Name) {
        this.mFull_Name = mFull_Name;
    }

    public Camera(int id, String name, String rover_id, String full_Name) {

        this.mId = id;
        this.mName = name;
        this.mRover_id = rover_id;
        this.mFull_Name = full_Name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mName);
        dest.writeString(this.mRover_id);
        dest.writeString(this.mFull_Name);
    }

    protected Camera(Parcel in) {
        this.mId = in.readInt();
        this.mName = in.readString();
        this.mRover_id = in.readString();
        this.mFull_Name = in.readString();
    }

    public static final Parcelable.Creator<Camera> CREATOR = new Parcelable.Creator<Camera>() {
        @Override
        public Camera createFromParcel(Parcel source) {
            return new Camera(source);
        }

        @Override
        public Camera[] newArray(int size) {
            return new Camera[size];
        }
    };
}
