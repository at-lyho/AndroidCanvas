package com.hovanly.dut.canvas;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;

import lombok.Data;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 02/08/2017
 */
@Data
public class Sticker {
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
        return GraphicUtils.pointInTriangle(pointer, pointer1, pointer2, pointer3)
                || GraphicUtils.pointInTriangle(pointer, pointer1, pointer3, pointer4);
    }
}
