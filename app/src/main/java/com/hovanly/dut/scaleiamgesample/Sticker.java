package com.hovanly.dut.scaleiamgesample;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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
    private int coordinateX;
    private int coordinateY;
    private Paint paint;

    public Sticker() {
        paint = new Paint();
        paint.setStrokeWidth(2);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void onDraw(Canvas canvas) {
        canvas.save();
        canvas.rotate(45f, BitmapUtils.dpToPx(150), BitmapUtils.dpToPx(150));
        canvas.scale(2, 2, BitmapUtils.dpToPx(150), BitmapUtils.dpToPx(150));
        canvas.drawBitmap(bitmap, BitmapUtils.dpToPx(100), BitmapUtils.dpToPx(100), null);
        canvas.restore();
        canvas.rotate(45f, BitmapUtils.dpToPx(150), BitmapUtils.dpToPx(150));
        drawPath(canvas);
    }

    private void drawPath(Canvas canvas) {
        Path path = new Path();
        path.moveTo(BitmapUtils.dpToPx(100), BitmapUtils.dpToPx(100));
        path.lineTo(bitmap.getWidth() + BitmapUtils.dpToPx(100), BitmapUtils.dpToPx(100));
        path.lineTo(bitmap.getWidth() + BitmapUtils.dpToPx(100), bitmap.getHeight() + BitmapUtils.dpToPx(100));
        path.lineTo(BitmapUtils.dpToPx(100), bitmap.getHeight() + BitmapUtils.dpToPx(100));
        path.lineTo(BitmapUtils.dpToPx(100), BitmapUtils.dpToPx(100));
        canvas.drawPath(path, paint);
    }
}
