package cn.nekocode.baseframework.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nekocode on 2015/7/17.
 */
public class Weather {
    @SerializedName("weatherinfo")
    private WeatherInfo weatherInfo;

    public static class WeatherInfo implements Parcelable, Cloneable {
        @SerializedName("city")
        private String city;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.city);
        }

        public WeatherInfo() {
        }

        protected WeatherInfo(Parcel in) {
            this.city = in.readString();
        }

        public static final Creator<WeatherInfo> CREATOR = new Creator<WeatherInfo>() {
            public WeatherInfo createFromParcel(Parcel source) {
                return new WeatherInfo(source);
            }

            public WeatherInfo[] newArray(int size) {
                return new WeatherInfo[size];
            }
        };

        public WeatherInfo clone(){
            try {
                return (WeatherInfo) super.clone();
            } catch(CloneNotSupportedException ex){
                return null;
            }
        }
    }

    public WeatherInfo getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
    }
}
