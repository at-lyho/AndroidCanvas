package com.hovanly.dut.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.util.Log;

import lombok.Data;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 02/08/2017
 */
@Data
public class Sticker extends BaseSticker {
    private static final String TAG = Sticker.class.getSimpleName();
    private Bitmap bitmap;
    private Bitmap bitmapRotate;
    private Bitmap bitmapDelete;
    private float distanceX;
    private float distanceY;
    private Paint paint;
    private Path path = new Path();
    private Matrix subMatrix = new Matrix();

    public Sticker(@NonNull Context context) {
        paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        setBitmap(GraphicUtils.getBitmapFormResource(context, R.drawable.broccoli, GraphicUtils.dpToPx(100), GraphicUtils.dpToPx(100)));
        setBitmapDelete(GraphicUtils.getBitmapFormResource(context, R.drawable.ic_remove, GraphicUtils.dpToPx(24), GraphicUtils.dpToPx(24)));
        setBitmapRotate(GraphicUtils.getBitmapFormResource(context, R.drawable.ic_rotate, GraphicUtils.dpToPx(24), GraphicUtils.dpToPx(24)));
        setCoordinateX(100);
        setCoordinateY(100);
        setScale(2f);
        setDegrees(10f);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        canvas.save();
        drawSticker(canvas);
        canvas.restore();
        drawBoundSticker(canvas);
        drawDelete(canvas);
        drawRotate(canvas);
    }

    private void drawSticker(Canvas canvas) {
        onUpdateMatrixSticker();
        canvas.concat(getMatrix());
        canvas.drawBitmap(bitmap, getCoordinateX(), getCoordinateY(), null);
    }

    private void drawDelete(Canvas canvas) {
        Pointer pointer = new Pointer(getCoordinateX(), getCoordinateY(), getSubMatrix());
        canvas.drawBitmap(bitmapDelete, pointer.getX() - bitmapDelete.getWidth() / 2, pointer.getY() - bitmapDelete.getHeight() / 2, null);
    }

    private void drawRotate(Canvas canvas) {
        Pointer pointer = new Pointer(getCoordinateX() + bitmap.getWidth(), getCoordinateY() + bitmap.getHeight(), getSubMatrix());
        canvas.drawBitmap(bitmapRotate, pointer.getX() - bitmapDelete.getWidth() / 2, pointer.getY() - bitmapDelete.getHeight() / 2, null);
    }

    private void drawBoundSticker(Canvas canvas) {
        float coordinateX = getCoordinateX();
        float coordinateY = getCoordinateY();
        Matrix matrix = getSubMatrix();
        Pointer pointer1 = new Pointer(coordinateX, coordinateY, matrix);
        Pointer pointer2 = new Pointer(coordinateX + bitmap.getWidth(), coordinateY, matrix);
        Pointer pointer3 = new Pointer(coordinateX + bitmap.getWidth(), coordinateY + bitmap.getHeight(), matrix);
        Pointer pointer4 = new Pointer(coordinateX, coordinateY + bitmap.getHeight(), matrix);
        path.reset();
        path.moveTo(pointer1.getX(), pointer1.getY());
        path.lineTo(pointer2.getX(), pointer2.getY());
        path.lineTo(pointer3.getX(), pointer3.getY());
        path.lineTo(pointer4.getX(), pointer4.getY());
        path.lineTo(pointer1.getX(), pointer1.getY());
        canvas.drawPath(path, paint);
    }

    private void onUpdateMatrixSticker() {
        subMatrix.reset();
        subMatrix.postScale(getScale(), getScale());
        subMatrix.postRotate(getDegrees());
     //   subMatrix.postTranslate(distanceX, distanceY);
        postConcatMatrix(subMatrix);
    }

    public void updateMove(float x, float y) {
        distanceX = x;
        distanceY = y;
       /* setCoordinateX(getCoordinateX() + x);
        setCoordinateY(getCoordinateY() + y);*/
    }

    public boolean isTouchInsideTicker(float x, float y) {
        float coordinateX = getCoordinateX();
        float coordinateY = getCoordinateY();
        Matrix matrix = getSubMatrix();
        Pointer pointer = new Pointer(x, y);
        Pointer pointer1 = new Pointer(coordinateX, coordinateY, matrix);
        Pointer pointer2 = new Pointer(coordinateX + bitmap.getWidth(), coordinateY, matrix);
        Pointer pointer3 = new Pointer(coordinateX + bitmap.getWidth(), coordinateY + bitmap.getHeight(), matrix);
        Pointer pointer4 = new Pointer(coordinateX, coordinateY + bitmap.getHeight(), matrix);
        return GraphicUtils.pointInTriangle(pointer, pointer1, pointer2, pointer3)
                || GraphicUtils.pointInTriangle(pointer, pointer1, pointer3, pointer4);
    }
}
