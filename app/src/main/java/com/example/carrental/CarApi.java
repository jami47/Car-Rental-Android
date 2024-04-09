package com.example.carrental;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CarApi implements Parcelable {
    String image, title, clas;
    int production = 2000;


    public CarApi() {
    }

    public CarApi(String image, String title, String clas, int production) {
        this.image = image;
        this.title = title;
        this.clas = clas;
        this.production = production;
    }

    protected CarApi(Parcel in) {
        image = in.readString();
        title = in.readString();
        clas = in.readString();
        production = in.readInt();
    }

    public static final Creator<CarApi> CREATOR = new Creator<CarApi>() {
        @Override
        public CarApi createFromParcel(Parcel in) {
            return new CarApi(in);
        }

        @Override
        public CarApi[] newArray(int size) {
            return new CarApi[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(title);
        dest.writeString(clas);
        dest.writeInt(production);
    }
}
