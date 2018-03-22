package com.example.listview;

import java.util.Comparator;

/**
 * Created by Arya on 4/8/16.
 */
public class ComparatorModel implements Comparator<BikeData> {
    @Override
    public int compare(BikeData myData1, BikeData myData2) {
        return (myData1.MODEL.compareTo(myData2.MODEL));
    }
}
