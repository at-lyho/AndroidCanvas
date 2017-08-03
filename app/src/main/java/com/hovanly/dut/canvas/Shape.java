package com.hovanly.dut.canvas;

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
    private Matrix matrix = new Matrix();

    public void postConcatMatrix(@NonNull Matrix matrix) {
        this.matrix.postConcat(matrix);
    }
}