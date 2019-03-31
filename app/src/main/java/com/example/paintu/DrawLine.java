package com.example.paintu;

import android.view.MotionEvent;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Paint;

public class DrawLine {

    private Canvas canvas;
    private Path path;
    private Paint paint;
    private float moveX, moveY;
    private float startX, startY;


    public DrawLine(Canvas canvas, Paint paint) {
        this.canvas = canvas;
        this.paint = paint;
    }

    public boolean draw(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = x;
            startY = y;
            return true;
        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            canvas.drawLine(startX, startY, x, y, paint);
            return true;
        }
        else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
            return false;

        return true;
    }
}
