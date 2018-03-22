package com.syberos.learnandroid;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by toby on 18-1-10.
 */

public class SendPersonUseParcelable implements Parcelable {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int age;

    public static final Creator<SendPersonUseParcelable> CREATOR = new Creator<SendPersonUseParcelable>() {
        @Override
        public SendPersonUseParcelable createFromParcel(Parcel in) {
            SendPersonUseParcelable sendPersonUseParcelable = new SendPersonUseParcelable();
            sendPersonUseParcelable.name = in.readString();
            sendPersonUseParcelable.age = in.readInt();
            return sendPersonUseParcelable;
        }

        @Override
        public SendPersonUseParcelable[] newArray(int size) {
            return new SendPersonUseParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(age);
    }
}
