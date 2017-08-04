package com.hovanly.dut.canvas.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.NonNull;

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

    /**
     *
     * @param matrix
     * @param minScale
     * @param maxScale
     * @param focusX
     * @param focusY
     */
    public static void onLimitScaleMatrix(@NonNull Matrix matrix, float minScale, float maxScale, float focusX, float focusY) {
        float[] values = new float[9];
        matrix.getValues(values);
        float scaleX = values[Matrix.MSCALE_X];
        float scaleY = values[Matrix.MSCALE_Y];
        if (scaleX >= maxScale) {
            matrix.postScale(maxScale / scaleX, maxScale / scaleY, focusX, focusY);
        } else if (scaleX <= minScale) {
            matrix.postScale(minScale / scaleX, minScale / scaleY, focusX, focusY);
        }
    }

    /**
     * @param xTouch
     * @param yTouch
     * @param originX coordinate x of origin
     * @param originY coordinate x of origin
     *                originX, originY must in canvas and not is canvas origin.
     * @return
     */
    public static double getAngle(float xTouch, float yTouch, float originX, float originY) {
        float x = xTouch - originX;
        float y = originY - yTouch;
        switch (getQuadrant(x, y, originX, originY)) {
            case 1:
                return 360 - Math.atan(x / y) / Math.PI * 180;
            case 2:
                return 270 - Math.atan(x / y) / Math.PI * 180;
            case 3:
                return 90 + Math.atan(x / y) / Math.PI * 180;
            case 4:
                return Math.atan(x / y) / Math.PI * 180;
            default:
                return 0;
        }
    }


    /**
     * @return The selected quadrant.
     *           *
     *       2   *    1
     *           *
     * *******************
     *           *
     *      3    *   4
     *           *
     */
    private static int getQuadrant(float x, float y, float originX, float originY) {
        if (x >= originX) {
            return y >= originX ? 1 : 4;
        } else {
            return y >= originY ? 2 : 3;
        }
    }
}
