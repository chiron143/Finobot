package com.github.mikephil.charting.interfaces2.dataprovider;

import com.github.mikephil.charting.data2.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
