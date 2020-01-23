package com.github.mikephil.charting.interfaces2.dataprovider;

import com.github.mikephil.charting.components2.YAxis;
import com.github.mikephil.charting.data2.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
