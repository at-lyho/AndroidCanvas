package com.hovanly.dut.canvas;

import android.graphics.Canvas;

import lombok.Data;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 02/08/2017
 */
@Data
abstract class BaseSticker {
    private float coordinateX;
    private float coordinateY;
    private float scale;
    private float degrees;

    abstract public void onDraw(Canvas canvas);
}
