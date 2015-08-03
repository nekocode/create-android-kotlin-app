package cn.nekocode.baseframework.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nekocode on 2015/7/17.
 */
public class Result implements Parcelable {
    @SerializedName("result")
    private List<List<String>> result;

    @SerializedName("cat")
    private List<List<String>> cat;

    public List<List<String>> getResult() {
        return result;
    }

    public void setResult(List<List<String>> result) {
        this.result = result;
    }

    public List<List<String>> getCat() {
        return cat;
    }

    public void setCat(List<List<String>> cat) {
        this.cat = cat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.result);
        dest.writeList(this.cat);
    }

    public Result() {
    }

    protected Result(Parcel in) {
        this.result = new ArrayList<List<String>>();
        in.readList(this.result, List.class.getClassLoader());
        this.cat = new ArrayList<List<String>>();
        in.readList(this.cat, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
