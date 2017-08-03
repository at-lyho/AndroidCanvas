package com.hovanly.dut.canvas.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.NonNull;

import com.hovanly.dut.canvas.R;
import com.hovanly.dut.canvas.utils.GraphicUtils;

import lombok.Data;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 02/08/2017
 */
@Data
public class Sticker extends Shape {
    private static final String TAG = Sticker.class.getSimpleName();
    private EditerSticker deletedAction;
    private EditerSticker rotateAction;
    private RectSticker rectSticker;
    private Bitmap bitmapSticker;
    private float distanceX;
    private float distanceY;
    private float scale;
    private float degrees;
    private Matrix matrix = new Matrix();

    public Sticker(@NonNull Context context) {
        setBitmapSticker(GraphicUtils.getBitmapFormResource(context, R.drawable.broccoli, GraphicUtils.dpToPx(100), GraphicUtils.dpToPx(100)));
        setRealCoordinateX(100);
        setRealCoordinateY(100);
        setScale(1f);
        setDegrees(2f);
        // Create Action Delete
        deletedAction = new EditerSticker();
        deletedAction.setBitmap(GraphicUtils.getBitmapFormResource(context, R.drawable.ic_rotate, GraphicUtils.dpToPx(24), GraphicUtils.dpToPx(24)));
        deletedAction.updateCoordinate(getRealCoordinateX(), getRealCoordinateY());
        deletedAction.setOffsetX(deletedAction.getBitmap().getWidth() / 2);
        deletedAction.setOffsetY(deletedAction.getBitmap().getHeight() / 2);
        // Create Action Rotate
        rotateAction = new EditerSticker();
        rotateAction.setBitmap(GraphicUtils.getBitmapFormResource(context, R.drawable.ic_rotate, GraphicUtils.dpToPx(24), GraphicUtils.dpToPx(24)));
        rotateAction.updateCoordinate(getRealCoordinateX() + getBitmapSticker().getWidth(), getRealCoordinateY() + getBitmapSticker().getHeight());
        rotateAction.setOffsetX(rotateAction.getBitmap().getWidth() / 2);
        rotateAction.setOffsetY(rotateAction.getBitmap().getHeight() / 2);
        // Create RectSticker
        float coordinateX = getRealCoordinateX();
        float coordinateY = getRealCoordinateY();
        Pointer pointer1 = new Pointer(coordinateX, coordinateY);
        Pointer pointer2 = new Pointer(coordinateX + bitmapSticker.getWidth(), coordinateY);
        Pointer pointer3 = new Pointer(coordinateX + bitmapSticker.getWidth(), coordinateY + bitmapSticker.getHeight());
        Pointer pointer4 = new Pointer(coordinateX, coordinateY + bitmapSticker.getHeight());
        rectSticker = new RectSticker(pointer1, pointer2, pointer3, pointer4);
    }

    @Override
    public void onDraw(Canvas canvas) {
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
        canvas.drawBitmap(bitmapSticker, getRealCoordinateX(), getRealCoordinateY(), null);
    }

    private void drawDelete(Canvas canvas) {
        deletedAction.onDraw(canvas, getMatrix());
    }

    private void drawRotate(Canvas canvas) {
        rotateAction.onDraw(canvas, getMatrix());
    }

    private void drawBoundSticker(Canvas canvas) {
        rectSticker.onDraw(canvas, getMatrix());
    }

    private void onUpdateMatrixSticker() {
      /*  matrix.postScale(getScale(), getScale());
        matrix.postRotate(getDegrees());*/
        matrix.postTranslate(distanceX, distanceY);
    }

    public void updateMove(float x, float y) {
        distanceX = x;
        distanceY = y;
    }

    @Override
    public boolean isTouchInside(float x, float y) {
        float coordinateX = getRealCoordinateX();
        float coordinateY = getRealCoordinateY();
        Matrix matrix = getMatrix();
        Pointer pointer = new Pointer(x, y);
        Pointer pointer1 = new Pointer(coordinateX, coordinateY, matrix);
        Pointer pointer2 = new Pointer(coordinateX + bitmapSticker.getWidth(), coordinateY, matrix);
        Pointer pointer3 = new Pointer(coordinateX + bitmapSticker.getWidth(), coordinateY + bitmapSticker.getHeight(), matrix);
        Pointer pointer4 = new Pointer(coordinateX, coordinateY + bitmapSticker.getHeight(), matrix);
        return GraphicUtils.pointInTriangle(pointer, pointer1, pointer2, pointer3)
                || GraphicUtils.pointInTriangle(pointer, pointer1, pointer3, pointer4);
    }
}
