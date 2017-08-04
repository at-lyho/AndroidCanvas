package com.hovanly.dut.canvas.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.view.ScaleGestureDetector;

import com.hovanly.dut.canvas.R;
import com.hovanly.dut.canvas.utils.GraphicUtils;

import lombok.Data;

import static com.hovanly.dut.canvas.CustomView.OnScaleListener.MAX_SCALE;
import static com.hovanly.dut.canvas.CustomView.OnScaleListener.MIN_SCALE;

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
    private Matrix matrixSticker = new Matrix();
    private float distanceX;
    private float distanceY;
    private float scale = 1.0f;
    private float degrees;

    public Sticker(@NonNull Context context) {
        setBitmapSticker(GraphicUtils.getBitmapFormResource(context, R.drawable.broccoli, GraphicUtils.dpToPx(100), GraphicUtils.dpToPx(100)));
        setRealCoordinateX(100);
        setRealCoordinateY(100);
        // Create Action Delete
        deletedAction = new EditerSticker();
        deletedAction.setBitmap(GraphicUtils.getBitmapFormResource(context, R.drawable.ic_remove, GraphicUtils.dpToPx(24), GraphicUtils.dpToPx(24)));
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
        onUpdateMatrixSticker();
        drawSticker(canvas);
        drawBoundSticker(canvas);
        drawDelete(canvas);
        drawRotate(canvas);
    }

    private void drawSticker(Canvas canvas) {
        canvas.save();
        canvas.concat(getMatrixSticker());
        canvas.drawBitmap(bitmapSticker, getRealCoordinateX(), getRealCoordinateY(), null);
        canvas.restore();
    }

    private void drawDelete(Canvas canvas) {
        deletedAction.onDraw(canvas, getMatrixSticker());
    }

    private void drawRotate(Canvas canvas) {
        rotateAction.onDraw(canvas, getMatrixSticker());
    }

    private void drawBoundSticker(Canvas canvas) {
        rectSticker.onDraw(canvas, getMatrixSticker());
    }

    /**
     * update matrixSticker if sticker rotate, move, and scale
     */
    private void onUpdateMatrixSticker() {
        matrixSticker.postRotate(getDegrees());
        matrixSticker.postTranslate(distanceX, distanceY);
    }

    public void updateMove(float x, float y) {
        distanceX = x;
        distanceY = y;
    }

    @Override
    public boolean isTouchInside(float x, float y) {
        return rectSticker.isTouchInside(x, y, getMatrixSticker());
    }

    public void updateScale(ScaleGestureDetector detector) {
        Pointer pointer = new Pointer(getRealCoordinateX() + bitmapSticker.getWidth() / 2, getRealCoordinateY() + bitmapSticker.getHeight() / 2, matrixSticker);
        matrixSticker.postScale(detector.getScaleFactor(), detector.getScaleFactor(), pointer.getX(), pointer.getY());
        GraphicUtils.onLimitScaleMatrix(matrixSticker, MIN_SCALE, MAX_SCALE, pointer.getX(), pointer.getY());
    }
}
