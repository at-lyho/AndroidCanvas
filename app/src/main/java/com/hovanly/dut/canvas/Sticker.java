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
public class Sticker extends Shape {
    private Bitmap bitmap;
    private Bitmap bitmapRotate;
    private Bitmap bitmapDelete;
    private float distanceX;
    private float distanceY;
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
        Matrix matrix = new Matrix();
        matrix.postScale(2, 2, 150, 150);
        matrix.postRotate(10f, 150, 150);
        matrix.postTranslate(10, 10);
        postConcatMatrix(matrix);
        canvas.concat(getMatrix());
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
        Pointer pointer1 = new Pointer(100, 100, getMatrix());
        Pointer pointer2 = new Pointer(100 + bitmap.getWidth(), 100, getMatrix());
        Pointer pointer3 = new Pointer(100 + bitmap.getWidth(), 100 + bitmap.getHeight(), getMatrix());
        Pointer pointer4 = new Pointer(100, 100 + bitmap.getHeight(), getMatrix());
        return GraphicUtils.pointInTriangle(pointer, pointer1, pointer2, pointer3)
                || GraphicUtils.pointInTriangle(pointer, pointer1, pointer3, pointer4);
    }
}
