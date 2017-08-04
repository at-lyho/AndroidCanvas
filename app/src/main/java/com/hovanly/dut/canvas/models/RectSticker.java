package com.hovanly.dut.canvas.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;

import com.hovanly.dut.canvas.utils.GraphicUtils;

import lombok.Data;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 03/08/2017
 */
@Data
public class RectSticker extends Shape {
    private Pointer realPointer1;
    private Pointer realPointer2;
    private Pointer realPointer3;
    private Pointer realPointer4;
    private Path path;

    public RectSticker(Pointer pointer1, Pointer pointer2, Pointer pointer3, Pointer pointer4) {
        realPointer1 = pointer1;
        realPointer2 = pointer2;
        realPointer3 = pointer3;
        realPointer4 = pointer4;
        path = new Path();
        intPaint();
    }

    private void intPaint() {
        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        setPaint(paint);
    }

    @Override
    public void onDraw(Canvas canvas) {
        //No-op
    }

    public boolean isTouchInside(float x, float y, @NonNull Matrix matrix) {
        Pointer pointer = new Pointer(x, y);
        Pointer pointer1 = new Pointer(realPointer1.getX(), realPointer1.getY(), matrix);
        Pointer pointer2 = new Pointer(realPointer2.getX(), realPointer2.getY(), matrix);
        Pointer pointer3 = new Pointer(realPointer3.getX(), realPointer3.getX(), matrix);
        Pointer pointer4 = new Pointer(realPointer4.getX(), realPointer4.getY(), matrix);
        return GraphicUtils.pointInTriangle(pointer, pointer1, pointer2, pointer3)
                || GraphicUtils.pointInTriangle(pointer, pointer1, pointer3, pointer4);
    }

    public void onDraw(Canvas canvas, @NonNull Matrix matrix) {
        Pointer pointer1 = new Pointer(realPointer1.getX(), realPointer1.getY(), matrix);
        Pointer pointer2 = new Pointer(realPointer2.getX(), realPointer2.getY(), matrix);
        Pointer pointer3 = new Pointer(realPointer3.getX(), realPointer3.getY(), matrix);
        Pointer pointer4 = new Pointer(realPointer4.getX(), realPointer4.getY(), matrix);
        path.reset();
        path.moveTo(pointer1.getX(), pointer1.getY());
        path.lineTo(pointer2.getX(), pointer2.getY());
        path.lineTo(pointer3.getX(), pointer3.getY());
        path.lineTo(pointer4.getX(), pointer4.getY());
        path.lineTo(pointer1.getX(), pointer1.getY());
        canvas.drawPath(path, getPaint());
    }
}
