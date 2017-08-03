package com.hovanly.dut.canvas.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hovanly.dut.canvas.models.Pointer;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 02/08/2017
 */
public class GraphicUtils {
    private GraphicUtils() {
        // No-op
    }

    public static Bitmap getBitmapFormResource(Context context, int res) {
        return BitmapFactory.decodeResource(context.getResources(), res);
    }

    public static Bitmap getBitmapFormResource(Context context, int res, int width, int height) {
        Bitmap bitmap = getBitmapFormResource(context, res);
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    public static int dpToPx(int dpValue) {
        return (int) (dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    private static float sign(Pointer pointer1, Pointer pointer2, Pointer pointer3) {
        return (pointer1.getX() - pointer3.getX()) *
                (pointer2.getY() - pointer3.getY())
                - (pointer2.getX() - pointer3.getX())
                * (pointer1.getY() - pointer3.getY());
    }

    public static boolean pointInTriangle(Pointer pointer, Pointer pointer1, Pointer pointer2, Pointer pointer3) {
        boolean b1, b2, b3;
        b1 = sign(pointer, pointer1, pointer2) < 0.0f;
        b2 = sign(pointer, pointer2, pointer3) < 0.0f;
        b3 = sign(pointer, pointer3, pointer1) < 0.0f;
        return ((b1 == b2) && (b2 == b3));
    }
}
