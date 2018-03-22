package com.example.listview;

import java.util.Comparator;

/**
 * Created by Arya on 4/8/16.
 */
public class ComparatorLocation implements Comparator<BikeData> {
    @Override
    public int compare(BikeData myData1, BikeData myData2) {
        return (myData1.LOCATION.compareTo(myData2.LOCATION));
    }
}
