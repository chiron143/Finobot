package com.purplepath.purplepath.recommendation.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 * @author Praveen Kumar on 02/02/17.
 */

public class RecommendationInfo {

    @DrawableRes
    public int iconRes;

    @DrawableRes
    public int indicatorRes;

    @StringRes
    public int nameRes;


    public static RecommendationInfo createRecommendationInfo(@DrawableRes int iconRes, @StringRes int nameRes, @DrawableRes int indicatorRes) {
        RecommendationInfo recommendationInfo = new RecommendationInfo();
        recommendationInfo.iconRes = iconRes;
        recommendationInfo.nameRes = nameRes;
        recommendationInfo.indicatorRes = indicatorRes;
        return recommendationInfo;
    }

}
