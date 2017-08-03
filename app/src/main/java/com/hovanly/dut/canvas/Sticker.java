package com.hovanly.dut.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;

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
    private Matrix matrix = new Matrix();

    public Sticker(@NonNull Context context) {
        paint = new Paint();
        paint.setStrokeWidth(2);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        setBitmap(GraphicUtils.getBitmapFormResource(context, R.drawable.broccoli));
        setBitmapDelete(GraphicUtils.getBitmapFormResource(context, R.drawable.ic_remove, GraphicUtils.dpToPx(24), GraphicUtils.dpToPx(24)));
        setBitmapRotate(GraphicUtils.getBitmapFormResource(context, R.drawable.ic_rotate, GraphicUtils.dpToPx(24), GraphicUtils.dpToPx(24)));
        setCoordinateX(200);
        setCoordinateY(200);
        setScale(2f);
        setDegrees(30f);
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawSticker(canvas);
        drawBoundSticker(canvas);
        drawDelete(canvas);
    }

    private void drawSticker(Canvas canvas) {
        canvas.save();
        updateMatrixSticker();
        canvas.concat(getMatrix());
        canvas.drawBitmap(bitmap, getCoordinateX(), getCoordinateY(), null);
        canvas.restore();

    }

    private void drawDelete(Canvas canvas) {
        canvas.drawBitmap(bitmapDelete, getCoordinateX() - bitmapDelete.getWidth() / 2, getCoordinateY() - bitmapDelete.getHeight() / 2, null);
    }

    private void drawBoundSticker(Canvas canvas) {
        Path path = new Path();
        float x = getCoordinateX();
        float y = getCoordinateY();
        path.moveTo(x, y);
        path.lineTo(bitmap.getWidth() + x, y);
        path.lineTo(bitmap.getWidth() + x, bitmap.getHeight() + y);
        path.lineTo(x, bitmap.getHeight() + y);
        path.lineTo(x, y);
        canvas.drawPath(path, paint);
    }

    private void updateMatrixSticker() {
        float scale = getScale();
        float degress = getDegrees();
        matrix.reset();
        matrix.postScale(scale, scale, 150, 150);
        matrix.postRotate(degress, 150, 150);
        postConcatMatrix(matrix);
    }

    public void updateMove(float x, float y) {
        distanceX = x;
        distanceY = y;
    }

    public boolean isTouchInsideTicker(float x, float y) {
        float coordinateX = getCoordinateX();
        float coordinateY = getCoordinateY();
        Matrix matrix = getMatrix();
        Pointer pointer = new Pointer(x, y);
        Pointer pointer1 = new Pointer(coordinateX, coordinateY, matrix);
        Pointer pointer2 = new Pointer(coordinateX + bitmap.getWidth(), coordinateY, matrix);
        Pointer pointer3 = new Pointer(coordinateX + bitmap.getWidth(), coordinateY + bitmap.getHeight(), matrix);
        Pointer pointer4 = new Pointer(coordinateX, coordinateY + bitmap.getHeight(), matrix);
        return GraphicUtils.pointInTriangle(pointer, pointer1, pointer2, pointer3)
                || GraphicUtils.pointInTriangle(pointer, pointer1, pointer3, pointer4);
    }
}
