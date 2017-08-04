package com.hovanly.dut.canvas.models;

import android.view.ScaleGestureDetector;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 04/08/2017
 */
public interface OnStickerActionListener {
    void onMove(float distanceX, float distanceY);

    void onRotate(float degrees);

    void onZoom(ScaleGestureDetector scaleGestureDetector);
}
