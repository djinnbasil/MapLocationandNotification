package com.tetra.biometrics;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class InterestPlace implements Parcelable {




       public InterestPlace(){
            address="";
            icon="";
            rating="";
            user_rating="";
            open="Open";
            name="";
        }
        String address;
        String icon;
        String rating;
        String user_rating;
        String open;
        String name;

    protected InterestPlace(Parcel in) {
        address = in.readString();
        icon = in.readString();
        rating = in.readString();
        user_rating = in.readString();
        open = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(icon);
        dest.writeString(rating);
        dest.writeString(user_rating);
        dest.writeString(open);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InterestPlace> CREATOR = new Creator<InterestPlace>() {
        @Override
        public InterestPlace createFromParcel(Parcel in) {
            return new InterestPlace(in);
        }

        @Override
        public InterestPlace[] newArray(int size) {
            return new InterestPlace[size];
        }
    };
}


