package com.hovanly.dut.canvas.models;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.NonNull;

import lombok.Data;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 02/08/2017
 */
@Data
abstract class Shape {
    private float coordinateX;
    private float coordinateY;
    abstract public void onDraw(Canvas canvas);

    public void updateCoordinate(float x, float y) {
        coordinateX = x;
        coordinateY = y;
    }

    public void updateCoordinate(float x, float y, @NonNull Matrix matrix) {
        Pointer pointer = new Pointer(x, y, matrix);
        coordinateX = pointer.getX();
        coordinateY = pointer.getY();
    }

    public void updateCoordinate(@NonNull Matrix matrix) {
        Pointer pointer = new Pointer(coordinateX, coordinateY, matrix);
        coordinateX = pointer.getX();
        coordinateY = pointer.getY();
    }
}
