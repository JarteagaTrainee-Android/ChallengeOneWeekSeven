package com.applaudostudio.weeksevenchallengeone.model.gsonmodels;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photos implements Parcelable {
    @SerializedName("id")
    private int mId;
    @SerializedName("sol")
    private int mSol;
    @SerializedName("camera")
    private Camera mCamera;
    @SerializedName("img_src")
    private String mImg_src;
    @SerializedName("earth_date")
    private String mEarth_date;
    @SerializedName("rover")
    private Rover mRover;


    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public int getSol() {
        return mSol;
    }

    public void setSol(int mSol) {
        this.mSol = mSol;
    }

    public Camera getCamera() {
        return mCamera;
    }

    public void setCamera(Camera mCamera) {
        this.mCamera = mCamera;
    }

    public String getImg_src() {
        return mImg_src;
    }

    public void setImg_src(String mImg_src) {
        this.mImg_src = mImg_src;
    }

    public String getEarth_date() {
        return mEarth_date;
    }

    public void setEarth_date(String mEarth_date) {
        this.mEarth_date = mEarth_date;
    }

    public Rover getRover() {
        return mRover;
    }

    public void setRover(Rover mRover) {
        this.mRover = mRover;
    }

    public Photos(int id, int sol, Camera camera, String img_src, String earth_date, Rover rover) {
        this.mId = id;
        this.mSol = sol;
        this.mCamera = camera;
        this.mImg_src = img_src;
        this.mEarth_date = earth_date;
        this.mRover = rover;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeInt(this.mSol);
        dest.writeParcelable(this.mCamera, flags);
        dest.writeString(this.mImg_src);
        dest.writeString(this.mEarth_date);
        dest.writeParcelable(this.mRover, flags);
    }

    protected Photos(Parcel in) {
        this.mId = in.readInt();
        this.mSol = in.readInt();
        this.mCamera = in.readParcelable(Camera.class.getClassLoader());
        this.mImg_src = in.readString();
        this.mEarth_date = in.readString();
        this.mRover = in.readParcelable(Rover.class.getClassLoader());
    }

    public static final Parcelable.Creator<Photos> CREATOR = new Parcelable.Creator<Photos>() {
        @Override
        public Photos createFromParcel(Parcel source) {
            return new Photos(source);
        }

        @Override
        public Photos[] newArray(int size) {
            return new Photos[size];
        }
    };
}
