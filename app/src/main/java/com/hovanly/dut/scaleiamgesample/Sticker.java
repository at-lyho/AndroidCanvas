package com.hovanly.dut.scaleiamgesample;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import lombok.Data;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 02/08/2017
 */
@Data
public class Sticker {
    private static final String TAG = "aaa";
    private Bitmap bitmap;
    private Bitmap bitmapRotate;
    private Bitmap bitmapDelete;
    private float coordinateX;
    private float coordinateY;
    private float distanceX;
    private float distanceY;
    private Matrix matrix = new Matrix();
    private Paint paint;
    private float drag = 0f;
    public Sticker() {
        paint = new Paint();
        paint.setStrokeWidth(2);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void onDraw(Canvas canvas) {
        canvas.save();
        Log.d(TAG, "onDraw: ");
        // canvas.rotate(drag, 150, 150);
        // canvas.rotate(30f, 150, 150);
        Matrix matrix = new Matrix();
        matrix.postScale(2, 2, 150, 150);
        matrix.postRotate(10f, 150, 150);
        matrix.postTranslate(10, 10);
        this.matrix.postConcat(matrix);
        canvas.concat(this.matrix);
        drawPath(canvas);
        canvas.drawBitmap(bitmap, 100, 100, null);
        canvas.restore();
    }

    private void drawPath(Canvas canvas) {
        Path path = new Path();
        path.moveTo(100, 100);
        path.lineTo(bitmap.getWidth() + 100, 100);
        path.lineTo(bitmap.getWidth() + 100, bitmap.getHeight() + 100);
        path.lineTo(100, bitmap.getHeight() + 100);
        path.lineTo(100, 100);
        canvas.drawPath(path, paint);
    }

    public void updateMove(float x, float y) {
        distanceX = x;
        distanceY = y;
    }

    public void updateDrag(float drag) {
        this.drag += drag;
    }

    public boolean isTouchOnTicker(float x, float y) {
        Pointer pointer = new Pointer(x, y);
        Pointer pointer1 = new Pointer(100, 100, matrix);
        Pointer pointer2 = new Pointer(100 + bitmap.getWidth(), 100, matrix);
        Pointer pointer3 = new Pointer(100 + bitmap.getWidth(), 100 + bitmap.getHeight(), matrix);
        Pointer pointer4 = new Pointer(100, 100 + bitmap.getHeight(), matrix);
        return pointInTriangle(pointer, pointer1, pointer2, pointer3)
                || pointInTriangle(pointer, pointer1, pointer3, pointer4);
    }

    private float sign(Pointer pointer1, Pointer pointer2, Pointer pointer3) {
        return (pointer1.getX() - pointer3.getX()) *
                (pointer2.getY() - pointer3.getY())
                - (pointer2.getX() - pointer3.getX())
                * (pointer1.getY() - pointer3.getY());
    }

    private boolean pointInTriangle(Pointer pointer, Pointer pointer1, Pointer pointer2, Pointer pointer3) {
        boolean b1, b2, b3;
        b1 = sign(pointer, pointer1, pointer2) < 0.0f;
        b2 = sign(pointer, pointer2, pointer3) < 0.0f;
        b3 = sign(pointer, pointer3, pointer1) < 0.0f;
        return ((b1 == b2) && (b2 == b3));
    }
}
