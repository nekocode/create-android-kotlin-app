package cn.nekocode.baseframework.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nekocode on 2015/7/17.
 */
public class ResultBean implements Parcelable {
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

    public ResultBean() {
    }

    protected ResultBean(Parcel in) {
        this.result = new ArrayList<List<String>>();
        in.readList(this.result, List.class.getClassLoader());
        this.cat = new ArrayList<List<String>>();
        in.readList(this.cat, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<ResultBean> CREATOR = new Parcelable.Creator<ResultBean>() {
        public ResultBean createFromParcel(Parcel source) {
            return new ResultBean(source);
        }

        public ResultBean[] newArray(int size) {
            return new ResultBean[size];
        }
    };
}
