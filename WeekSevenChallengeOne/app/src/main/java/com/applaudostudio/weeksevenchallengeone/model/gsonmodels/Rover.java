package com.applaudostudio.weeksevenchallengeone.model.gsonmodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Rover implements Parcelable {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("landing_date")
    private String mLanding_date;
    @SerializedName("launch_date")
    private String mLaunch_date;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getLanding_date() {
        return mLanding_date;
    }

    public void setLanding_date(String landing_date) {
        this.mLanding_date = landing_date;
    }

    public String getLaunch_date() {
        return mLaunch_date;
    }

    public void setLaunch_date(String launch_date) {
        this.mLaunch_date = launch_date;
    }



    public Rover(int id, String name, String landing_date, String launch_date) {
        this.mId = id;
        this.mName = name;
        this.mLanding_date = landing_date;
        this.mLaunch_date = launch_date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mName);
        dest.writeString(this.mLanding_date);
        dest.writeString(this.mLaunch_date);
    }

    protected Rover(Parcel in) {
        this.mId = in.readInt();
        this.mName = in.readString();
        this.mLanding_date = in.readString();
        this.mLaunch_date = in.readString();
    }

    public static final Parcelable.Creator<Rover> CREATOR = new Parcelable.Creator<Rover>() {
        @Override
        public Rover createFromParcel(Parcel source) {
            return new Rover(source);
        }

        @Override
        public Rover[] newArray(int size) {
            return new Rover[size];
        }
    };
}
