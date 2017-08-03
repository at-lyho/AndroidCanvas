package com.hovanly.dut.canvas.models;

import android.graphics.Canvas;
import android.graphics.Paint;

import lombok.Data;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 02/08/2017
 */
@Data
abstract class Shape {
    private Paint paint;
    private float realCoordinateX;
    private float realCoordinateY;
    abstract public void onDraw(Canvas canvas);

    public void updateCoordinate(float x, float y) {
        realCoordinateX = x;
        realCoordinateY = y;
    }
    abstract public boolean isTouchInside(float x, float y);
}
