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
    private float lastX;
    private float lastY;

    public DrawPath(Canvas canvas, Paint paint)
    {
        this.canvas = canvas;
        this.paint = paint;
    }

    public boolean draw(MotionEvent event)
    {

        if(path == null)
            path = new Path();

        if(event == null)
            return false;

        if(canvas == null)
            return false;

        int action = event.getAction();
        float touchX = event.getX();
        float touchY = event.getY();

        boolean returnval = false;

        switch (action){
            case MotionEvent.ACTION_DOWN:
                // record xy
                lastX = touchX;
                lastY = touchY;
                returnval = true;
                break;
            case MotionEvent.ACTION_MOVE:
                path.reset();

                path.moveTo(lastX, lastY);
                path.lineTo(touchX, touchY);
                canvas.drawPath(path, paint);

                lastX = touchX;
                lastY = touchY;
                returnval = true;
                break;
            case MotionEvent.ACTION_UP:
                returnval = true;
                break;
            default:
                returnval = false;
                break;
        }
        if(returnval)
            return true;

        return false;

    }

}
