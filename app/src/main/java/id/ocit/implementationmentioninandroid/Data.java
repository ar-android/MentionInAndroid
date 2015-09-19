package id.ocit.implementationmentioninandroid;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import com.linkedin.android.spyglass.mentions.Mentionable;
import org.json.JSONArray;

/**
 * Created by ocit on 9/19/15.
 */

public class Data implements Mentionable {

    private final String mName;

    public Data(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    // --------------------------------------------------
    // Mentionable Implementation
    // --------------------------------------------------

    @NonNull
    @Override
    public String getTextForDisplayMode(MentionDisplayMode mode) {
        switch (mode) {
            case FULL:
                return mName;
            case PARTIAL:
            case NONE:
            default:
                return "";
        }
    }

    @Override
    public MentionDeleteStyle getDeleteStyle() {
        // Note: Cities do not support partial deletion
        // i.e. "San Francisco" -> DEL -> ""
        return MentionDeleteStyle.PARTIAL_NAME_DELETE;
    }

    @Override
    public int getSuggestibleId() {
        return mName.hashCode();
    }

    @Override
    public String getSuggestiblePrimaryText() {
        return mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
    }

    public Data(Parcel in) {
        mName = in.readString();
    }

    public static final Parcelable.Creator<Data> CREATOR
            = new Parcelable.Creator<Data>() {
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    // --------------------------------------------------
    // DataLoader Class (loads cities from JSON file)
    // --------------------------------------------------

    public static class DataLoader extends MentionsLoader<Data> {
        private static final String TAG = DataLoader.class.getSimpleName();

        public DataLoader(String[] arr) {
            super(arr);
        }

        @Override
        public Data[] loadData(JSONArray arr) {
            Data[] data = new Data[arr.length()];
            try {
                for (int i = 0; i < arr.length(); i++) {
                    data[i] = new Data(arr.getString(i));
                }
            } catch (Exception e) {
                Log.e(TAG, "Unhandled exception while parsing city JSONArray", e);
            }
            return data;
        }
    }

}
