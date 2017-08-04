package com.hovanly.dut.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import com.hovanly.dut.canvas.models.Sticker;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 31/07/2017
 */
public class CustomView extends View {
    private static final String TAG = CustomView.class.getSimpleName();
    Mode mMode = Mode.NONE;
    private float mScale = 1.0f;
    private float mTouchX;
    private float mTouchY;
    private Sticker mSticker;
    private ScaleGestureDetector mScaleGestureDetector;

    private void init(Context context) {
        mSticker = new Sticker(context);
        OnScaleListener onScaleListener = new OnScaleListener();
        mScaleGestureDetector = new ScaleGestureDetector(context, onScaleListener);
    }

    public CustomView(Context context) {
        super(context);
        init(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        int numTouch = event.getPointerCount();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mMode = Mode.MOVE;
                mTouchX = event.getX();
                mTouchY = event.getY();
                if (mSticker.isTouchInside(mTouchX, mTouchY)){
                    Toast.makeText(getContext(), "inside", Toast.LENGTH_SHORT).show();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mMode == Mode.MOVE) {
                    mSticker.updateMove(event.getX() - mTouchX, event.getY() - mTouchY);
                    mTouchX = event.getX();
                    mTouchY = event.getY();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (numTouch >= 2) {
                    mMode = Mode.ZOOM;
                } else {
                    mMode = Mode.NONE;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mMode = Mode.NONE;
                break;
            case MotionEvent.ACTION_UP:
                mMode = Mode.NONE;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mSticker.onDraw(canvas);
    }

    private enum Mode {
        NONE, MOVE, ZOOM
    }
  /*  private double getAngle(double xTouch, double yTouch) {
        double x = xTouch - (dialerWidth / 2d);
        double y = dialerHeight - yTouch - (dialerHeight / 2d);

        switch (getQuadrant(x, y)) {
            case 1:
                return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 2:
                return 180 - Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 3:
                return 180 + (-1 * Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
            case 4:
                return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            default:
                return 0;
        }
    }

    */

    /**
     * @return The selected quadrant.
     *//*
    private static int getQuadrant(double x, double y) {
        if (x >= 0) {
            return y >= 0 ? 1 : 4;
        } else {
            return y >= 0 ? 2 : 3;
        }
    }*/
    public class OnScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        public static final float MIN_SCALE = 1.0f;
        public static final float MAX_SCALE = 3.0F;
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (mMode == Mode.ZOOM) {
                mSticker.updateScale(detector);
                invalidate();
            }
            return true;
        }
    }
}
