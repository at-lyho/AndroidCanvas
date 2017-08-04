package com.hovanly.dut.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import com.hovanly.dut.canvas.models.Pointer;
import com.hovanly.dut.canvas.models.Sticker;
import com.hovanly.dut.canvas.utils.GraphicUtils;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 31/07/2017
 */
public class CustomView extends View {
    private static final String TAG = CustomView.class.getSimpleName();
    Mode mMode = Mode.NONE;
    private float mTouchX;
    private float mTouchY;
    private Sticker mSticker;
    private double lastAngle = 0;
    private ScaleGestureDetector mScaleGestureDetector;

    public CustomView(Context context) {
        super(context);
        init(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mSticker = new Sticker(context, 200, 200);
        mScaleGestureDetector = new ScaleGestureDetector(context, new OnScaleListener());
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
                Pointer pointer = mSticker.getCoordinateRotate();
                lastAngle = GraphicUtils.getAngle(mTouchX, mTouchY, pointer.getX(), pointer.getY());
                if (mSticker.isTouchInside(mTouchX, mTouchY)) {
                    Toast.makeText(getContext(), "inSide", Toast.LENGTH_SHORT).show();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mMode == Mode.MOVE) {
                    /*mSticker.onMove(event.getX() - mTouchX, event.getY() - mTouchY);
                    mTouchX = event.getX();
                    mTouchY = event.getY();
                    invalidate();*/
                    Pointer pointer1 = mSticker.getCoordinateRotate();
                    double newAngle = GraphicUtils.getAngle(event.getX(), event.getY(), pointer1.getX(), pointer1.getY());
                    mSticker.onRotate((float) (newAngle - lastAngle));
                    lastAngle = newAngle;
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

    /**
     * Action Object
     */
    private enum Mode {
        NONE, MOVE, ZOOM
    }

    /**
     * Listener scale for zoom object.
     */
    public class OnScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        public static final float MIN_SCALE = 1.0f;
        public static final float MAX_SCALE = 3.0f;
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
         /*   if (mMode == Mode.ZOOM) {
                mSticker.onZoom(detector);
                invalidate();
            }*/
            return true;
        }
    }
}
