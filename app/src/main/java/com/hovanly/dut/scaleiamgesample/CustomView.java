package com.hovanly.dut.scaleiamgesample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 31/07/2017
 */
public class CustomView extends View {
    private Paint paint;
    private GestureDetector mGestureDetector;
    private float mTouchX;
    private float mTouchY;
    private Sticker mSticker;


    public CustomView(Context context) {
        super(context);
        init(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        mGestureDetector = new GestureDetector(context, new GestureDetectorCustom());
        mSticker = new Sticker();
        mSticker.setBitmap(BitmapUtils.getBimapFormResouce(context, R.drawable.broccoli));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mSticker.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchX = event.getX();
                mTouchY = event.getY();
                if (mSticker.isTouchOnTicker(mTouchX, mTouchY)){
                    Toast.makeText(getContext(), "inside", Toast.LENGTH_SHORT).show();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mSticker.updateMove(event.getX() - mTouchX, event.getY() - mTouchY);
                mSticker.updateDrag(20);
               // invalidate();
              //  invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mTouchX = event.getX();
                mTouchY = event.getY();
                break;
        }
        return true;
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
    private class GestureDetectorCustom implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
         //   Toast.makeText(getContext(), "onScroll", Toast.LENGTH_SHORT).show();
           /* mTouchX = -distanceX;
            mTouchY = -distanceY;*/
            //  invalidate();
         //   invalidate();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }
}
