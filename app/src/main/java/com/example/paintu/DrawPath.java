package com.example.paintu;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

public class DrawPath {

    private Paint paint = null;
    private Canvas canvas = null;
    private Path path = null;
    private MotionEvent motionEvent = null;

    public DrawPath(Canvas canvas, Paint paint)
    {
        this.canvas = canvas;
        this.paint = paint;
    }

    public boolean draw(MotionEvent event)
    {
        int action = event.getAction();

        path = new Path();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                canvas.drawPath(path, paint);
                break;
            default:
                break;
        }
        return false;

    }

}
