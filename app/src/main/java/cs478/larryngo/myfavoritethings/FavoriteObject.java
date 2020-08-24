package cs478.larryngo.myfavoritethings;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

//Parcelable classes allows the transfer of object classes smoothly and prevent errors
public class FavoriteObject implements Parcelable {
    private String title;
    private String date;
    private byte[] image;
    private String desc;
    private int id;

    public FavoriteObject (String title, byte[] image, String desc, String date)
    {
        this.title = title;
        this.date = date;
        this.image = image;
        this.desc = desc;
    }

    public FavoriteObject (String title, byte[] image, String desc, String date, int id)
    {
        this.title = title;
        this.date = date;
        this.image = image;
        this.desc = desc;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public byte[] getImage() {
        return image;
    }

    public String getDesc() {
        return desc;
    }

    protected FavoriteObject(Parcel in) {
        title = in.readString();
        date = in.readString();
        desc = in.readString();
        image = in.createByteArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(desc);
        dest.writeByteArray(image);
    }


    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FavoriteObject> CREATOR = new Parcelable.Creator<FavoriteObject>() {
        @Override
        public FavoriteObject createFromParcel(Parcel in) {
            return new FavoriteObject(in);
        }

        @Override
        public FavoriteObject[] newArray(int size) {
            return new FavoriteObject[size];
        }
    };
}