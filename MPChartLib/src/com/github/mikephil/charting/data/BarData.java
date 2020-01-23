
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Data object that represents all data for the BarChart.
 * 
 * @author Philipp Jahoda
 */
public class BarData extends BarLineScatterCandleBubbleData<IBarDataSet> {

    /** the space that is left between groups of bars */
    private float mGroupSpace = 3f;
    private float mBarWidth = 0.5f;

    // /**
    // * The maximum space (in pixels on the screen) a single bar can consume.
    // */
    // private float mMaximumBarWidth = 100f;

    public BarData() {
        super();
    }

    public BarData(List<String> xVals) {
        super(xVals);
    }

    public BarData(String[] xVals) {
        super(xVals);
    }

    public BarData(List<String> xVals, List<IBarDataSet> dataSets) {
        super(xVals, dataSets);
    }

    public BarData(String[] xVals, List<IBarDataSet> dataSets) {
        super(xVals, dataSets);
    }

    public BarData(List<String> xVals, IBarDataSet dataSet) {
        super(xVals, toList(dataSet));
    }

    public BarData(String[] xVals, IBarDataSet dataSet) {
        super(xVals, toList(dataSet));
    }

    private static List<IBarDataSet> toList(IBarDataSet dataSet) {
        List<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        sets.add(dataSet);
        return sets;
    }

    public void setBarWidth(float mBarWidth) {
        this.mBarWidth = mBarWidth;
    }

    public float getBarWidth() {
        return mBarWidth;
    }


    /**
     * Returns the space that is left out between groups of bars. Always returns
     * 0 if the BarData object only contains one DataSet (because for one
     * DataSet, there is no group-space needed).
     * 
     * @return
     */
    public float getGroupSpace() {

        if (mDataSets.size() <= 1)
            return 0f;
        else
            return mGroupSpace;
    }

    /**
     * Sets the space between groups of bars of different datasets in percent of
     * the total width of one bar. 100 = space is exactly one bar width,
     * default: 80
     * 
     * @param percent
     */
    public void setGroupSpace(float percent) {
        mGroupSpace = percent / 100f;
    }

    /**
     * Returns true if this BarData object contains grouped DataSets (more than
     * 1 DataSet).
     * 
     * @return
     */
    public boolean isGrouped() {
        return mDataSets.size() > 1 ? true : false;
    }
    
    //
    // /**
    // * Sets the maximum width (in density pixels) a single bar in the barchart
    // * should consume.
    // *
    // * @param max
    // */
    // public void setBarWidthMaximum(float max) {
    // mMaximumBarWidth = Utils.convertDpToPixel(max);
    // }
    //
    // /**
    // * Returns the maximum width (in density pixels) a single bar in the
    // * barchart should consume.
    // *
    // * @return
    // */
    // public float getBarWidthMaximum() {
    // return mMaximumBarWidth;
    // }

    public void groupBars(float fromX, float groupSpace, float barSpace) {

        int setCount = mDataSets.size();
        if (setCount <= 1) {
            throw new RuntimeException("BarData needs to hold at least 2 BarDataSets to allow grouping.");
        }

        IBarDataSet max = getMaxEntryCountSet();
        int maxEntryCount = max.getEntryCount();

        float groupSpaceWidthHalf = groupSpace / 2f;
        float barSpaceHalf = barSpace / 2f;
        float barWidthHalf = mBarWidth / 2f;

        float interval = getGroupWidth(groupSpace, barSpace);

        for (int i = 0; i < maxEntryCount; i++) {

            float start = fromX;
            fromX += groupSpaceWidthHalf;

            for (IBarDataSet set : mDataSets) {

                fromX += barSpaceHalf;
                fromX += barWidthHalf;

                if (i < set.getEntryCount()) {

                    BarEntry entry = set.getEntryForIndex(i);

                    if (entry != null) {
                        entry.setX(fromX);
                    }
                }

                fromX += barWidthHalf;
                fromX += barSpaceHalf;
            }

            fromX += groupSpaceWidthHalf;
            float end = fromX;
            float innerInterval = end - start;
            float diff = interval - innerInterval;

            // correct rounding errors
            if (diff > 0 || diff < 0) {
                fromX += diff;
            }
        }

        notifyDataChanged();
    }

    /**
     * In case of grouped bars, this method returns the space an individual group of bar needs on the x-axis.
     *
     * @param groupSpace
     * @param barSpace
     * @return
     */
    public float getGroupWidth(float groupSpace, float barSpace) {
        return mDataSets.size() * (mBarWidth + barSpace) + groupSpace;
    }
}
