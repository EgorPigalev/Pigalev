package com.example.pigalev;

import android.os.Parcel;
import android.os.Parcelable;

public class MaskFeeling implements Parcelable {

    private int id;
    private String title;
    private int position;
    private String image;

    public MaskFeeling(int id, String title, int position, String image) {
        this.id = id;
        this.title = title;
        this.position = position;
        this.image = image;
    }

    protected MaskFeeling(Parcel in) {
        id = in.readInt();
        title = in.readString();
        position = in.readInt();
        image = in.readString();
    }

    public static final Creator<MaskFeeling> CREATOR = new Creator<MaskFeeling>() {
        @Override
        public MaskFeeling createFromParcel(Parcel in) {
            return new MaskFeeling(in);
        }

        @Override
        public MaskFeeling[] newArray(int size) {
            return new MaskFeeling[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(position);
        dest.writeString(image);
    }
}
