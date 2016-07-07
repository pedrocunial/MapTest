package br.com.pedrocunial.maptest.utils;

import android.content.Context;

/**
 * Created by summerjob on 07/07/16.
 */
public class Converter {

    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
