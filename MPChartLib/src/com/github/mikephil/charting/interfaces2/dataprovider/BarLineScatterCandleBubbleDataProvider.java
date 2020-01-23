package com.github.mikephil.charting.interfaces2.dataprovider;

import com.github.mikephil.charting.components2.YAxis.AxisDependency;
import com.github.mikephil.charting.data2.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.utils2.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    boolean isInverted(AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
