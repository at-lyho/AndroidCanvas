package com.hovanly.dut.scaleiamgesample;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 02/08/2017
 */
public class BitmapUtils {
    private BitmapUtils() {
        // No-op
    }

    public static Bitmap getBimapFormResouce(Context context, int res) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), res);
        return bitmap;
    }

    public static int dpToPx(int dpValue) {
        return (int) (dpValue * Resources.getSystem().getDisplayMetrics().density);
    }
}
