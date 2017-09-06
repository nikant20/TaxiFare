package me.nikantchaudhary.taxifare;

import java.io.Serializable;

// Bean or Model or POJO
public class Rate implements Serializable {

    long BaseRate,FareRate,TimeRate;

    public Rate(){

    }

    public Rate(long baseRate, long fareRate, long timeRate) {
        BaseRate = baseRate;
        FareRate = fareRate;
        TimeRate = timeRate;
    }

    public long getBaseRate() {
        return BaseRate;
    }

    public void setBaseRate(long baseRate) {
        BaseRate = baseRate;
    }

    public long getFareRate() {
        return FareRate;
    }

    public void setFareRate(long fareRate) {
        FareRate = fareRate;
    }

    public long getTimeRate() {
        return TimeRate;
    }

    public void setTimeRate(long timeRate) {
        TimeRate = timeRate;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "BaseRate=" + BaseRate +
                ", FareRate=" + FareRate +
                ", TimeRate=" + TimeRate +
                '}';
    }
}
