package com.hovanly.dut.canvas.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.NonNull;

import lombok.Data;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 03/08/2017
 */
@Data
public class EditerSticker extends Shape {
    private float offsetX;
    private float offsetY;
    private Bitmap bitmap;
    @Override
    public void onDraw(Canvas canvas) {
        //No-op
    }

    @Override
    public boolean isTouchInside(float x, float y) {
        return false;
    }

    public void onDraw(Canvas canvas, @NonNull Matrix matrix) {
        Pointer pointer = new Pointer(getRealCoordinateX(), getRealCoordinateY(), matrix);
        canvas.drawBitmap(bitmap, pointer.getX() - offsetX, pointer.getY() - offsetY, null);
    }
}
